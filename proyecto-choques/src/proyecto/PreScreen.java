package proyecto;

import org.apache.tools.ant.taskdefs.Sleep;

import peasy.PeasyCam;
import processing.core.PGraphics3D;
import processing.core.PMatrix3D;
import toxi.geom.Vec3D;
import toxi.geom.mesh.Face;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;


public class PreScreen implements ScreenPhase, ControlListener{
	private Main screen;
	TriangleMesh mesh;
	private static PeasyCam camera = null;
	float posX = 0;
	String file = "";
	
	public PreScreen(Main screen) {
		this.screen = screen;		
	}

	@Override
	public void drawScreen() {
		screen.background(51);
//		screen.noStroke();
		screen.lights();

		if (screen.cp5.window(screen).isMouseOver()) {
	        camera.setActive(false);
	      } else {
	        camera.setActive(true);
	      }

		
		mesh.center(new Vec3D(posX,0,0));
		mesh();
		camera.beginHUD();
		screen.noLights();
		screen.cp5.draw();
		camera.endHUD();
//		Main.print(file);
//		Main.print("\nposX: "+ posX);
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
		screen.addButton("Seleccionar modelo", 200, 25, 15, 15, this);
		screen.addSlider("posX","Posicion en X", 200, 15, 15, 50, -250,250,this);
		
		if (camera == null){
			camera = new PeasyCam(screen, 500);
		} else {
			camera.setActive(true);
			camera.reset(0);
		}
		screen.cp5.setAutoDraw(false);
		
//		mesh = (TriangleMesh)new STLReader().loadBinary(screen.sketchPath("/mesh/mesh.stl"),STLReader.TRIANGLEMESH).flipYAxis();
		mesh = new TriangleMesh();
		
	}
	
	
	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isController()) { 
			if(theEvent.getController().getName()=="posX") {
				posX = theEvent.getController().getValue();
//				Main.print("\nposX: "+ posX);
			 }
			if(theEvent.getController().getName()=="Seleccionar modelo") {
				new Thread(
					    new Runnable() {
					      public void run() {
					    	  synchronized (screen){
					    		  loadMesh(screen.selectInput());
					    	  }
					      }
					    }
					  ).start();
//				screen.noLoop();
//				String meshFile = screen.selectInput();
//				screen.loop();
			 }
			
		}
	}
	
	protected TriangleMesh loadMesh(String selectInput) {
		if (selectInput!= null){
			mesh = (TriangleMesh)new STLReader().loadBinary(screen.sketchPath(selectInput),STLReader.TRIANGLEMESH);
			return mesh;
		}
		return null;
	}

//	public class OpenMeshListener implements CallbackListener {
//		private Main screen;
//		public OpenMeshListener(Main screen) {
//			this.screen = screen;
//		}
//		@Override
//		public void controlEvent(CallbackEvent theEvent) {
//			if (theEvent.getAction() == ControlP5.ACTION_RELEASED) {
//				String meshFile = screen.selectInput();
//			}
//		}
//	}
	

	@Override
	public void destroy() {
		camera.reset(0);
		camera.setActive(false);
		screen.camera();
		
	}
}
