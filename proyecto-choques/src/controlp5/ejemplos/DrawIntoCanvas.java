package controlp5.ejemplos;

/**
 * ControlP5 DrawIntoCanvas
 *
 * this example demonstrates how to draw into a ControlWindowCanvas 
 * from another window. Click and drag the mouse inside each of one of the 
 * windows to see its effect.
 *
 * by Andreas Schlegel, 2011
 * www.sojamo.de/libraries/controlp5
 *
 */

import java.awt.Frame;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PImage;
import toxi.geom.Vec3D;
import toxi.geom.mesh.Face;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import toxi.processing.ToxiclibsSupport;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import controlP5.ControlWindow;
import controlP5.ControlWindowCanvas;

public class DrawIntoCanvas extends PApplet {

	ControlP5 controlP5;
	ControlWindow controlWindow;
	ControlWindowCanvas cc;
	private TriangleMesh mesh;
	private ToxiclibsSupport gfx;
	private PeasyCam camera;

	// your controlWindowCanvas class
	class MyCanvas extends ControlWindowCanvas {

		private ControlP5 cp5;
		int myColor = color(255);
		int c1, c2;
		float n, n1;

		@Override
		public void setup(PApplet theApplet) {
			
//			CallbackListener cb = new CallbackListener() {
//				@Override
//				public void controlEvent(CallbackEvent theEvent) {		
//					if (theEvent.getAction() == ControlP5.ACTION_RELEASED){
//							System.out.print("Before noLoop\n");
//							noLoop();
//							JFileChooser fc = new JFileChooser();
//							System.out.print("Before frame\n");
//							fc.showOpenDialog(DrawIntoCanvas.this);
//							System.out.print("Before loop\n");
//							loop();
//					}
//				}
//			};
			
			CallbackListener cb2 = new CallbackListener() {
				@Override
				public void controlEvent(CallbackEvent theEvent) {
					if (theEvent.getAction() == ControlP5.ACTION_RELEASED) {

						noLoop();
						
						javax.swing.JOptionPane.showMessageDialog(new Frame(),"what?");
						
						loop();
					}
				}
			};
			
			super.setup(theApplet);
			noStroke();
			cp5 = new ControlP5(theApplet);
			// create a new button with name 'buttonA'
			cp5.addButton("colorA").setValue(0).setPosition(100, 100)
					.setSize(200, 19);//.addCallback(cb);

			// and add another 2 buttons
			cp5.addButton("colorB").setValue(100).setPosition(100, 120)
					.setSize(200, 19).addCallback(cb2);

			cp5.addButton("colorC").setPosition(100, 140).setSize(200, 19)
					.setValue(0);

			PImage[] imgs = { loadImage("res/button_a.png"),
					loadImage("res/button_b.png"),
					loadImage("res/button_c.png") };
			cp5.addButton("play").setValue(128).setPosition(140, 300)
					.setImages(imgs).updateSize();

			cp5.addButton("playAgain").setValue(128).setPosition(210, 300)
					.setImages(imgs).updateSize();

		}

		@Override
		public void draw(PApplet theApplet) {
//			theApplet.draw();
			
		}
	}

	@Override
	public void setup() {
		// size(400, 400);
		// frameRate(30);
//		Color col = new Color(31);
		
		size(600, 600, P3D);
		
		mesh=(TriangleMesh)new STLReader().loadBinary(sketchPath("/mesh/mesh.stl"),STLReader.TRIANGLEMESH).flipYAxis();
		gfx = new ToxiclibsSupport(this);
		camera = new PeasyCam(this, 0, 0, 0, 50);

		controlP5 = new ControlP5(this);

		controlWindow = controlP5.addControlWindow("controlP5window", 100, 100,
				width, height, 30);
//		controlWindow.setUpdateMode(ControlWindow.NORMAL);
		// controlWindow.setUpdateMode(ControlWindow.NORMAL);
		cc = new MyCanvas();
		cc.post();
		controlWindow.addCanvas(cc);
		// controlP5.addCanvas(cc);

	}
	
	@Override
	public void draw() {
		background(51);
//		lights();
		noStroke();
//		gfx.mesh(mesh, false, 10);

		mesh();

		this.controlP5.draw();
//		System.out.print("DRAW");
	}

	private void mesh() {
		beginShape(TRIANGLES);
		
		Vec3D colAmp=new Vec3D(400, 200, 200);
		Vec3D newColAmp=new Vec3D(1, 400, 1500);
		Vec3D neuColAmp=new Vec3D(255, 100, 100);
		int num=mesh.getNumFaces();
		  mesh.computeVertexNormals();
		  for(int i=0; i<num; i++) {
		    Face f=mesh.faces.get(i);
		    Vec3D col=f.a.add(colAmp).scaleSelf((float) 0.5);
		    if (i == 50)
		    	col = f.a.add(newColAmp);
		    fill(col.x,col.y,col.z);
//		    normal(f.a.normal.x,f.a.normal.y,f.a.normal.z);
		    vertex(f.a);
		    if (i == 50)
		    	col = f.b.add(neuColAmp.scaleSelf((float) 0.5));
		    fill(col.x,col.y,col.z);
//		    normal(f.b.normal.x,f.b.normal.y,f.b.normal.z);
		    vertex(f.b);
		    if (i == 50)
		    	col = f.c.add(colAmp);
		    fill(col.x,col.y,col.z);
//		    normal(f.c.normal.x,f.c.normal.y,f.c.normal.z);
		    vertex(f.c);
		    
		  }
			endShape();

	}
	
	 private void normal(Vec3D v) {
		  normal(v.x,v.y,v.z);
		}
		 
	private void vertex(Vec3D v) {
		  vertex(v.x,v.y,v.z);
		}
	
	
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { controlp5.ejemplos.DrawIntoCanvas.class
				.getName() });
	}

}
