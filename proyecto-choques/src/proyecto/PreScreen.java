package proyecto;

import peasy.PeasyCam;
import toxi.geom.Vec3D;
import toxi.geom.mesh.Face;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;


public class PreScreen implements ScreenPhase{
	private Main screen;
	TriangleMesh mesh;
	private PeasyCam camera;

	public PreScreen(Main screen) {
		this.screen = screen;		
	}

	@Override
	public void drawScreen() {
		screen.background(51);
		screen.noStroke();
		screen.lights();
		mesh();
		
//		Main.print("\nMesheando");
	}
	

	private void mesh() {
		screen.beginShape(Main.TRIANGLES);
		
		Vec3D colAmp=new Vec3D(400, 200, 200);
		Vec3D newColAmp=new Vec3D(1, 400, 1500);
		Vec3D neuColAmp=new Vec3D(255, 100, 100);
		int num=mesh.getNumFaces();
//		  mesh.computeVertexNormals();
		  for(int i=0; i<num; i++) {
		    Face f=mesh.faces.get(i);
		    Vec3D col=f.a.add(colAmp).scaleSelf((float) 0.5);
		    if (i == 50)
		    	col = f.a.add(newColAmp);
		    screen.fill(col.x,col.y,col.z);
//		    normal(f.a.normal.x,f.a.normal.y,f.a.normal.z);
		    vertex(f.a);
		    if (i == 50)
		    	col = f.b.add(neuColAmp.scaleSelf((float) 0.5));
		    screen.fill(col.x,col.y,col.z);
//		    normal(f.b.normal.x,f.b.normal.y,f.b.normal.z);
		    vertex(f.b);
		    if (i == 50)
		    	col = f.c.add(colAmp);
		    screen.fill(col.x,col.y,col.z);
//		    normal(f.c.normal.x,f.c.normal.y,f.c.normal.z);
		    vertex(f.c);
		    
		  }
			screen.endShape();

	}
	
		 
	private void vertex(Vec3D v) {
		  screen.vertex(v.x,v.y,v.z);
		}
	

	@Override
	public void setup() {
		screen.setTitle("Preprocesamiento");
		screen.addButton("Volver atras", 200, 25, 1150, 5, new OpenStartScreenListener(screen));
		screen.addButton("Seleccionar modelo", 200, 25, 15, 15, new OpenMeshListener(screen));
		
		
		mesh = (TriangleMesh)new STLReader().loadBinary(screen.sketchPath("/mesh/mesh.stl"),STLReader.TRIANGLEMESH).flipYAxis();
		
		camera = new PeasyCam(screen, 0, 0, 0, 50);
	}
	
	public class OpenMeshListener implements CallbackListener {

		private Main screen;
		
		public OpenMeshListener(Main screen) {
			this.screen = screen;
		}

		@Override
		public void controlEvent(CallbackEvent theEvent) {
			if (theEvent.getAction() == ControlP5.ACTION_RELEASED) {
				String meshFile = screen.selectInput();
				System.out.print(meshFile);
			}
		}
		

	}
}
