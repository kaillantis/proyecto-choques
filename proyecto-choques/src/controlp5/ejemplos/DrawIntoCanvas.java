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
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import toxi.processing.ToxiclibsSupport;
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

		@Override
		public void draw(PApplet theApplet) {
			// a rectangle will be drawn if the mouse has been
			// pressed inside the main sketch window.
			// mousePressed here refers to the mousePressed
			// variable of your main sketch
			// if (mousePressed) {
			// theApplet.fill(255, 0, 0);
			// theApplet.rect(10, 10, 100, 100);
			// theApplet.fill(0);
			// theApplet.ellipse(mouseX, mouseY, 20, 20);
			// }
			// will draw a rectangle into the controlWindow
			// if the mouse has been pressed inside the controlWindow itself.
			// theApplet.mousePressed here refers to the
			// mousePressed variable of the controlWindow.
			// if (theApplet.mousePressed) {
			// theApplet.fill(0);
			// theApplet.rect(10, 10, 100, 100);
			// theApplet.fill(255, 0, 0);
			// theApplet.ellipse(theApplet.mouseX, theApplet.mouseY, 20, 20);
			// }

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
				500, 100, 30);
		controlWindow.setUpdateMode(ControlWindow.NORMAL);
		//
		// cc = new MyCanvas();
		// cc.pre();
		// controlWindow.addCanvas(cc);

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
