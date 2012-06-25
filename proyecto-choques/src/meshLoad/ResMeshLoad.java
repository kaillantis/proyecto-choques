package meshLoad;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultListModel;

import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.Vec3D;

public class ResMeshLoad extends PApplet {
	private static final long serialVersionUID = 6637231894245735116L;
	Hashtable<String, Vec3D> db_node = new Hashtable<String, Vec3D>();
//	Hashtable db_node_tmp = new Hashtable();
	Hashtable<String, int[]> db_element = new Hashtable<String, int[]>();
//	Hashtable db_element_type = new Hashtable();
//	Hashtable db_displacements = new Hashtable();
//	Hashtable db_strains = new Hashtable();
//	Hashtable db_stresses = new Hashtable();
	private int fontsize;
	 Vector<Line> vLines = new Vector<Line>();
	 Vector<Triangle> vTriangle = new Vector<Triangle>();
	private PeasyCam camera;
	
	private class Line{
		public Line(Vec3D point1, Vec3D point2) {
			super();
			this.point1 = point1;
			this.point2 = point2;
		}
		public Vec3D point1;
		public Vec3D point2;
	}
	
	private class Triangle{
		public Triangle(Vec3D point1, Vec3D point2, Vec3D point3) {
			super();
			this.point1 = point1;
			this.point2 = point2;
			this.point3 = point3;
		}
		public Vec3D point1;
		public Vec3D point2;
		public Vec3D point3;
	}
	
	@Override
	public void setup() {
		load(new File(System.getProperty("user.dir")).getParent() + File.separator + "mesh" + File.separator +"test.flavia.msh");
		System.out.print("\nNode size: " + db_node.size());
		System.out.print("\nElem size: " + db_element.size());
		
		for (Entry entry: db_element.entrySet()){
			print(entry.getKey().toString()+"\n");
			print(entry.getValue().toString()+"\n");
		}
		
		size(600, 600, OPENGL);
		readMesh();
		camera = new PeasyCam(this, 0, 0, 0, 50);
		
	}

	private void drawMesh() {
		drawLines();
		drawTriangles();
		
	}
	

	private void drawTriangles() {
		beginShape(TRIANGLES);
		for (Triangle triangle: vTriangle) {
			vertex(triangle.point1);
			vertex(triangle.point2);
			vertex(triangle.point3);
		}
		endShape();
	}

	private void drawLines() {
		beginShape(LINES);
		for (Line line : vLines) {
			vertex(line.point1.toArray());
			vertex(line.point2.toArray());
		}
		endShape();
	}

	private void vertex(Vec3D v) {
		  vertex(v.x,v.y,v.z);
	}
	
	@Override
	public void draw() {
		background(51);
		lights();
//		noStroke();
		drawMesh();
	}

	private void readMesh() {

		for (Entry entry: db_element.entrySet()){
//        int[] arr = db_element.get(key);
		int[] arr =(int[]) entry.getValue();
        if(arr.length==2){
          Vec3D p1 = db_node.get(arr[0]+"");
          Vec3D p2 = db_node.get(arr[1]+"");
          vLines.add(new Line(p1, p2));
         
        }else if(arr.length==3){
            Vec3D p1 = db_node.get(arr[0]+"");
            Vec3D p2 = db_node.get(arr[1]+"");
            Vec3D p3 = db_node.get(arr[1]+"");
            vTriangle.add(new Triangle(p1, p2, p3));
//            print("\nReading mesh");
        }
        	
        }
        
        
	}

	public synchronized void load(String st) {
		try {
			// Rod_2 "Type100" ElemType Linear "MeshType100"
			// Beam_2 "Type110" ElemType Linear "MeshType110"
			// Beam_Spring_2,Contact_Line "Type120" ElemType Linear
			// "MeshType120"
			// Contact_Triangle "Type420" ElemType Triangle "MeshType420"
			// Shell_C0_3 "Type210" ElemType Triangle "MeshType210"
			// Shell_BT_4 "Type300" ElemType Quadrilateral "MeshType300"
			// Solid_Iso_6 "Type500" ElemType Hexahedra "MeshType500"

			double maxdx, maxdy, maxdz, maxdi, maxex, maxey, maxez, maxexy, maxeyz, maxexz, maxsx, maxsy, maxsz, maxsxy, maxsyz, maxsxz, maxei, maxsi, mindx, mindy, mindz, mindi, minex, miney, minez, minexy, mineyz, minexz, minei, minsx, minsy, minsz, minsxy, minsyz, minsxz, minsi;
			double maxx, maxy, maxz;
			double minx, miny, minz;
			maxdx = maxdy = maxdz = maxdi = maxex = maxey = maxez = maxexy = maxeyz = maxexz = maxsx = maxsy = maxsz = maxsxy = maxsyz = maxsxz = maxei = maxsi = Float.MIN_VALUE;
			mindx = mindy = mindz = minex = miney = minez = minexy = mineyz = minexz = minsx = minsy = minsz = minsxy = minsyz = minsxz = Float.MAX_VALUE;
			maxx = maxy = maxz = -Double.MAX_VALUE;
			minx = miny = minz = Double.MAX_VALUE;
			DefaultListModel model = new DefaultListModel();
			String st_msh = st.substring(0, st.length() - 3) + "msh";
			String st_dat = st;
			RandomAccessFile in = new RandomAccessFile(st_msh, "r");
			String dat;
			System.out.print("\nLoading mesh ...");
			while ((dat = in.readLine()) != null) {
				if (dat.startsWith("COORDINATES")) {
					Vec3D p3d;
					while (!(dat = in.readLine()).startsWith("END")) {
						StringTokenizer st_t = new StringTokenizer(dat);
						String index = st_t.nextToken();
						p3d = toVec3D(dat);
						maxx = Math.max(maxx, p3d.x);
						maxy = Math.max(maxy, p3d.y);
						maxz = Math.max(maxz, p3d.z);
						minx = Math.min(minx, p3d.x);
						miny = Math.min(miny, p3d.y);
						minz = Math.min(minz, p3d.z);
						db_node.put(index, p3d);
					}
				} else if (dat.startsWith("ELEMENTS")) {
					fontsize = (int) ((maxx - minx + maxy - miny + maxz - minz) / db_node.size());
					if (fontsize == 0)
						fontsize = 1;
					while (!(dat = in.readLine()).startsWith("END")) {
						StringTokenizer st_t = new StringTokenizer(dat);
						String index = st_t.nextToken();
						int[] arr = toArrayInt(dat);
						db_element.put(index, arr);
					}
				}
			}
			in.close();
		/*	
			in = new RandomAccessFile(st_dat, "r");
			System.out.print("... OK\nLoading data ...");
			dat = in.readLine();
			int n = 1;
			while (dat != null) {
				System.out.print("\nloading block: " + (n++) + " (" + dat + ")");
				if (dat.toUpperCase().startsWith("GAUSSPOINTS")) {
					StringTokenizer st_t = new StringTokenizer(dat, "\"");
					st_t.nextToken();
					String et = st_t.nextToken();
					dat = in.readLine();
					st_t = new StringTokenizer(dat, ":");
					st_t.nextToken();
					int np = Integer.parseInt(st_t.nextToken().trim());
					db_element_type.put(et, "" + np);
					while (!(dat = in.readLine()).toUpperCase().startsWith("END")) {
					}
				} else if (dat.indexOf("DISPLACEMENTS") != -1) {
					StringTokenizer st_t = new StringTokenizer(dat.trim());
					st_t.nextToken();
					st_t.nextToken();
					String step = st_t.nextToken();
					model.addElement(step);
					Hashtable v = new Hashtable();
					while ((dat = in.readLine()) != null && dat.indexOf("LOCAL") == -1 && dat.indexOf("GLOBAL") == -1) {
						st_t = new StringTokenizer(dat);
						String key = st_t.nextToken();
						Vec3D p = toVec3D(dat);
						mindx = Math.min(mindx, p.x);
						mindy = Math.min(mindy, p.y);
						mindz = Math.min(mindz, p.z);
						maxdx = Math.max(maxdx, p.x);
						maxdy = Math.max(maxdy, p.y);
						maxdz = Math.max(maxdz, p.z);
						maxdi = Math.max(maxdi, (float) Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z));
						v.put(key, p);
					}
					db_displacements.put(step, v);
				} else if (dat.indexOf("STRESSES") != -1) {
					StringTokenizer st_t = new StringTokenizer(dat.trim());
					st_t.nextToken();
					st_t.nextToken();
					String step = st_t.nextToken();
					Hashtable v = (Hashtable) db_stresses.get(step);
					if (v == null)
						v = new Hashtable();
					while ((dat = in.readLine()) != null && dat.indexOf("LOCAL") == -1 && dat.indexOf("GLOBAL") == -1 && dat.indexOf("DISPLACEMENTS") == -1) {
						st_t = new StringTokenizer(dat);
						if (st_t.countTokens() == 7) {
							long pos = in.getFilePointer();
							String dat1 = in.readLine();
							StringTokenizer st_t1 = null;
							if (dat1 != null)
								st_t1 = new StringTokenizer(dat1);
							if (dat1 == null || st_t1.countTokens() == 7 || dat1.indexOf("LOCAL") != -1 || dat1.indexOf("GLOBAL") != -1 || dat1.indexOf("DISPLACEMENTS") != -1) {
								in.seek(pos);
							} else {
								dat += " " + dat1 + " " + in.readLine() + " " + in.readLine() + " " + in.readLine() + " " + in.readLine() + " " + in.readLine() + " " + in.readLine();
							}
						}
						String key = st_t.nextToken();
						float[] arr = toArrayFloat(dat);
						if (arr.length == 1) {
							minsx = Math.min(minsx, arr[0]);
							maxsx = Math.max(maxsx, arr[0]);
							maxsi = Math.max(maxsi, Math.abs(arr[0]));
						} else if (arr.length == 6) {
							minsx = Math.min(minsx, arr[0]);
							minsy = Math.min(minsy, arr[1]);
							minsz = Math.min(minsz, arr[2]);
							minsxy = Math.min(minsxy, arr[3]);
							minsyz = Math.min(minsyz, arr[4]);
							minsxz = Math.min(minsxz, arr[5]);
							maxsx = Math.max(maxsx, arr[0]);
							maxsy = Math.max(maxsy, arr[1]);
							maxsz = Math.max(maxsz, arr[2]);
							maxsxy = Math.max(maxsxy, arr[3]);
							maxsyz = Math.max(maxsyz, arr[4]);
							maxsxz = Math.max(maxsxz, arr[5]);
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[0] - arr[1], 2) + Math.pow(arr[1] - arr[2], 2) + Math.pow(arr[2] - arr[0], 2) + 6 * (arr[3] * arr[3] + arr[4] * arr[4] + arr[5] * arr[5])));
						} else {
							minsx = Math.min(minsx, (arr[0] + arr[6] + arr[12] + arr[18] + arr[24] + arr[30] + arr[36] + arr[42]) / 8);
							minsy = Math.min(minsy, (arr[1] + arr[7] + arr[13] + arr[19] + arr[25] + arr[31] + arr[37] + arr[43]) / 8);
							minsz = Math.min(minsz, (arr[2] + arr[8] + arr[14] + arr[20] + arr[26] + arr[32] + arr[38] + arr[44]) / 8);
							minsxy = Math.min(minsxy, (arr[3] + arr[9] + arr[15] + arr[21] + arr[27] + arr[33] + arr[39] + arr[45]) / 8);
							minsyz = Math.min(minsyz, (arr[4] + arr[10] + arr[16] + arr[22] + arr[28] + arr[34] + arr[40] + arr[46]) / 8);
							minsxz = Math.min(minsxz, (arr[5] + arr[11] + arr[17] + arr[23] + arr[29] + arr[35] + arr[41] + arr[47]) / 8);
							maxsx = Math.max(maxsx, (arr[0] + arr[6] + arr[12] + arr[18] + arr[24] + arr[30] + arr[36] + arr[42]) / 8);
							maxsy = Math.max(maxsy, (arr[1] + arr[7] + arr[13] + arr[19] + arr[25] + arr[31] + arr[37] + arr[43]) / 8);
							maxsz = Math.max(maxsz, (arr[2] + arr[8] + arr[14] + arr[20] + arr[26] + arr[32] + arr[38] + arr[44]) / 8);
							maxsxy = Math.max(maxsxy, (arr[3] + arr[9] + arr[15] + arr[21] + arr[27] + arr[33] + arr[39] + arr[45]) / 8);
							maxsyz = Math.max(maxsyz, (arr[4] + arr[10] + arr[16] + arr[22] + arr[28] + arr[34] + arr[40] + arr[46]) / 8);
							maxsxz = Math.max(maxsxz, (arr[5] + arr[11] + arr[17] + arr[23] + arr[29] + arr[35] + arr[41] + arr[47]) / 8);
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[0] - arr[1], 2) + Math.pow(arr[1] - arr[2], 2) + Math.pow(arr[2] - arr[0], 2) + 6 * (arr[3] * arr[3] + arr[4] * arr[4] + arr[5] * arr[5])));
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[6] - arr[7], 2) + Math.pow(arr[7] - arr[8], 2) + Math.pow(arr[8] - arr[6], 2) + 6 * (arr[9] * arr[9] + arr[10] * arr[10] + arr[11] * arr[11])));
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[12] - arr[13], 2) + Math.pow(arr[13] - arr[14], 2) + Math.pow(arr[14] - arr[12], 2) + 6 * (arr[15] * arr[15] + arr[16] * arr[16] + arr[17] * arr[17])));
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[18] - arr[19], 2) + Math.pow(arr[19] - arr[20], 2) + Math.pow(arr[20] - arr[18], 2) + 6 * (arr[21] * arr[21] + arr[22] * arr[22] + arr[23] * arr[23])));
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[24] - arr[25], 2) + Math.pow(arr[25] - arr[26], 2) + Math.pow(arr[26] - arr[24], 2) + 6 * (arr[27] * arr[27] + arr[28] * arr[28] + arr[29] * arr[29])));
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[30] - arr[31], 2) + Math.pow(arr[31] - arr[32], 2) + Math.pow(arr[32] - arr[30], 2) + 6 * (arr[33] * arr[33] + arr[34] * arr[34] + arr[35] * arr[35])));
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[36] - arr[37], 2) + Math.pow(arr[36] - arr[38], 2) + Math.pow(arr[38] - arr[36], 2) + 6 * (arr[39] * arr[39] + arr[40] * arr[40] + arr[41] * arr[41])));
							maxsi = (float) Math.max(maxsi, Math.sqrt(2) / 2 * Math.sqrt(Math.pow(arr[42] - arr[43], 2) + Math.pow(arr[43] - arr[44], 2) + Math.pow(arr[44] - arr[42], 2) + 6 * (arr[45] * arr[45] + arr[46] * arr[46] + arr[47] * arr[47])));
						}
						v.put(key, arr);
					}
					db_stresses.put(step, v);
				} else if (dat.indexOf("STRAINS") != -1) {
					StringTokenizer st_t = new StringTokenizer(dat.trim());
					st_t.nextToken();
					st_t.nextToken();
					String step = st_t.nextToken();
					Hashtable v = (Hashtable) db_strains.get(step);
					if (v == null)
						v = new Hashtable();
					while ((dat = in.readLine()) != null && dat.indexOf("LOCAL") == -1 && dat.indexOf("GLOBAL") == -1 && dat.indexOf("DISPLACEMENTS") == -1) {
						st_t = new StringTokenizer(dat);
						if (st_t.countTokens() == 7) {
							long pos = in.getFilePointer();
							String dat1 = in.readLine();
							StringTokenizer st_t1 = null;
							if (dat1 != null)
								st_t1 = new StringTokenizer(dat1);
							if (dat1 == null || st_t1.countTokens() == 7 || dat1.indexOf("LOCAL") != -1 || dat1.indexOf("GLOBAL") != -1 || dat1.indexOf("DISPLACEMENTS") != -1) {
								in.seek(pos);
							} else {
								dat += " " + dat1 + " " + in.readLine() + " " + in.readLine() + " " + in.readLine() + " " + in.readLine() + " " + in.readLine() + " " + in.readLine();
							}
						}
						String key = st_t.nextToken();
						float[] arr = toArrayFloat(dat);
						if (arr.length == 1) {
							minex = Math.min(minex, arr[0]);
							maxex = Math.max(maxex, arr[0]);
							maxei = Math.max(maxei, Math.abs(arr[0]));
						} else if (arr.length == 6) {
							minex = Math.min(minex, arr[0]);
							miney = Math.min(miney, arr[1]);
							minez = Math.min(minez, arr[2]);
							minexy = Math.min(minexy, arr[3]);
							mineyz = Math.min(mineyz, arr[4]);
							minexz = Math.min(minexz, arr[5]);
							maxex = Math.max(maxex, arr[0]);
							maxey = Math.max(maxey, arr[1]);
							maxez = Math.max(maxez, arr[2]);
							maxexy = Math.max(maxexy, arr[3]);
							maxeyz = Math.max(maxeyz, arr[4]);
							maxexz = Math.max(maxexz, arr[5]);
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[0] - arr[1], 2) + Math.pow(arr[1] - arr[2], 2) + Math.pow(arr[2] - arr[0], 2) + 1.5 * (arr[3] * arr[3] + arr[4] * arr[4] + arr[5] * arr[5])));
						} else {
							minex = Math.min(minex, (arr[0] + arr[6] + arr[12] + arr[18] + arr[24] + arr[30] + arr[36] + arr[42]) / 8);
							miney = Math.min(miney, (arr[1] + arr[7] + arr[13] + arr[19] + arr[25] + arr[31] + arr[37] + arr[43]) / 8);
							minez = Math.min(minez, (arr[2] + arr[8] + arr[14] + arr[20] + arr[26] + arr[32] + arr[38] + arr[44]) / 8);
							minexy = Math.min(minexy, (arr[3] + arr[9] + arr[15] + arr[21] + arr[27] + arr[33] + arr[39] + arr[45]) / 8);
							mineyz = Math.min(mineyz, (arr[4] + arr[10] + arr[16] + arr[22] + arr[28] + arr[34] + arr[40] + arr[46]) / 8);
							minexz = Math.min(minexz, (arr[5] + arr[11] + arr[17] + arr[23] + arr[29] + arr[35] + arr[41] + arr[47]) / 8);
							maxex = Math.max(maxex, (arr[0] + arr[6] + arr[12] + arr[18] + arr[24] + arr[30] + arr[36] + arr[42]) / 8);
							maxey = Math.max(maxey, (arr[1] + arr[7] + arr[13] + arr[19] + arr[25] + arr[31] + arr[37] + arr[43]) / 8);
							maxez = Math.max(maxez, (arr[2] + arr[8] + arr[14] + arr[20] + arr[26] + arr[32] + arr[38] + arr[44]) / 8);
							maxexy = Math.max(maxexy, (arr[3] + arr[9] + arr[15] + arr[21] + arr[27] + arr[33] + arr[39] + arr[45]) / 8);
							maxeyz = Math.max(maxeyz, (arr[4] + arr[10] + arr[16] + arr[22] + arr[28] + arr[34] + arr[40] + arr[46]) / 8);
							maxexz = Math.max(maxexz, (arr[5] + arr[11] + arr[17] + arr[23] + arr[29] + arr[35] + arr[41] + arr[47]) / 8);
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[0] - arr[1], 2) + Math.pow(arr[1] - arr[2], 2) + Math.pow(arr[2] - arr[0], 2) + 1.5 * (arr[3] * arr[3] + arr[4] * arr[4] + arr[5] * arr[5])));
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[6] - arr[7], 2) + Math.pow(arr[7] - arr[8], 2) + Math.pow(arr[8] - arr[6], 2) + 1.5 * (arr[9] * arr[9] + arr[10] * arr[10] + arr[11] * arr[11])));
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[12] - arr[13], 2) + Math.pow(arr[13] - arr[14], 2) + Math.pow(arr[14] - arr[12], 2) + 1.5 * (arr[15] * arr[15] + arr[16] * arr[16] + arr[17] * arr[17])));
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[18] - arr[19], 2) + Math.pow(arr[19] - arr[20], 2) + Math.pow(arr[20] - arr[18], 2) + 1.5 * (arr[21] * arr[21] + arr[22] * arr[22] + arr[23] * arr[23])));
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[24] - arr[25], 2) + Math.pow(arr[25] - arr[26], 2) + Math.pow(arr[26] - arr[24], 2) + 1.5 * (arr[27] * arr[27] + arr[28] * arr[28] + arr[29] * arr[29])));
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[30] - arr[31], 2) + Math.pow(arr[31] - arr[32], 2) + Math.pow(arr[32] - arr[30], 2) + 1.5 * (arr[33] * arr[33] + arr[34] * arr[34] + arr[35] * arr[35])));
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[36] - arr[37], 2) + Math.pow(arr[36] - arr[38], 2) + Math.pow(arr[38] - arr[36], 2) + 1.5 * (arr[39] * arr[39] + arr[40] * arr[40] + arr[41] * arr[41])));
							maxei = (float) Math.max(maxei, Math.sqrt(2) / 3 * Math.sqrt(Math.pow(arr[42] - arr[43], 2) + Math.pow(arr[43] - arr[44], 2) + Math.pow(arr[44] - arr[42], 2) + 1.5 * (arr[45] * arr[45] + arr[46] * arr[46] + arr[47] * arr[47])));
						}
						v.put(key, arr);
					}
					db_strains.put(step, v);
				} else
					dat = in.readLine();
			}
			in.close();
*/
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public Vec3D toVec3D(String st) throws Exception {
		StringTokenizer st_t = new StringTokenizer(st);
		st_t.nextToken();
		return new Vec3D(new Float(st_t.nextToken()).floatValue(), new Float(st_t.nextToken()).floatValue(), new Float(st_t.nextToken()).floatValue());
	}

	public int[] toArrayInt(String st) throws Exception {
		StringTokenizer st_t = new StringTokenizer(st);
		int[] arr = new int[st_t.countTokens() - 1];
		st_t.nextToken();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Integer.parseInt(st_t.nextToken());
		}
		return arr;
	}

	public float[] toArrayFloat(String st) throws Exception {
		StringTokenizer st_t = new StringTokenizer(st);
		float[] arr = new float[st_t.countTokens() - 1];
		st_t.nextToken();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Float.parseFloat(st_t.nextToken());
		}
		return arr;
	}
}
