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

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PImage;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import toxi.processing.ToxiclibsSupport;
import controlP5.ControlEvent;
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
			super.setup(theApplet);
			noStroke();
			cp5 = new ControlP5(theApplet);
			// create a new button with name 'buttonA'
			cp5.addButton("colorA").setValue(0).setPosition(100, 100)
					.setSize(200, 19);

			// and add another 2 buttons
			cp5.addButton("colorB").setValue(100).setPosition(100, 120)
					.setSize(200, 19);

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
			// background(myColor);
			myColor = lerpColor(c1, c2, n);
			n += (1 - n) * 0.1;
		}

		public void controlEvent(ControlEvent theEvent) {
			println(theEvent.getController().getName());
			n = 0;
		}

		public void colorA(int theValue) {
			println("a button event from colorA: " + theValue);
			c1 = c2;
			c2 = color(0, 160, 100);
		}

		public void colorB(int theValue) {
			println("a button event from colorB: " + theValue);
			c1 = c2;
			c2 = color(150, 0, 0);
		}

		public void colorC(int theValue) {
			println("a button event from colorC: " + theValue);
			c1 = c2;
			c2 = color(255, 255, 0);
		}

		public void play(int theValue) {
			println("a button event from buttonB: " + theValue);
			c1 = c2;
			c2 = color(0, 0, 0);
		}
	}

	@Override
	public void setup() {
		// size(400, 400);
		// frameRate(30);

		size(600, 600, P3D);
		mesh = (TriangleMesh) new STLReader().loadBinary(
				sketchPath("mesh/mesh.stl"), STLReader.TRIANGLEMESH);
		// mesh=(TriangleMesh)new
		// STLReader().loadBinary(sketchPath("mesh-flipped.stl"),STLReader.TRIANGLEMESH).flipYAxis();
		gfx = new ToxiclibsSupport(this);
		camera = new PeasyCam(this, 0, 0, 0, 50);

		controlP5 = new ControlP5(this);

		controlWindow = controlP5.addControlWindow("controlP5window", 100, 100,
				width, height, 30);
		controlWindow.setUpdateMode(ControlWindow.NORMAL);
		// controlWindow.setUpdateMode(ControlWindow.NORMAL);
		//
		cc = new MyCanvas();
		cc.pre();
		controlWindow.addCanvas(cc);
		// controlP5.addCanvas(cc);

	}

	@Override
	public void draw() {
		background(51);
		lights();
		noStroke();
		gfx.mesh(mesh, false, 10);
		this.controlP5.draw();
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { controlp5.ejemplos.DrawIntoCanvas.class
				.getName() });
	}

}
