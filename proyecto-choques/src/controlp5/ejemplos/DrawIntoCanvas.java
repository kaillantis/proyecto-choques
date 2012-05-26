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
	int myColorBackground = color(0, 0, 0);

	ControlWindow controlWindow;

	public int sliderValue = 40;
	private static final long serialVersionUID = -7144529710247863651L;
	TriangleMesh mesh;
	ToxiclibsSupport gfx;
	PeasyCam camera;
	private ControlP5 cp5;
	int myColor = color(255);

	public void setup() {
	  size(600,600,P3D);
	  mesh=(TriangleMesh)new STLReader().loadBinary(sketchPath("mesh/mesh.stl"),STLReader.TRIANGLEMESH);
//	  mesh=(TriangleMesh)new STLReader().loadBinary(sketchPath("mesh-flipped.stl"),STLReader.TRIANGLEMESH).flipYAxis();
	  gfx=new ToxiclibsSupport(this);
	  camera = new PeasyCam(this, 0, 0, 0, 50);
	  cp5 = new ControlP5(this);
	  

	  // create a new button with name 'buttonA'
	  cp5.addButton("colorA")
	     .setValue(0)
	     .setPosition(100,100)
	     .setSize(200,19)
	     ;
	  
	  // and add another 2 buttons
	  cp5.addButton("colorB")
	     .setValue(100)
	     .setPosition(100,120)
	     .setSize(200,19)
	     ;
	     
	  cp5.addButton("colorC")
	     .setPosition(100,140)
	     .setSize(200,19)
	     .setValue(0)
	     ;

	  PImage[] imgs = {loadImage("res/button_a.png"),loadImage("res/button_b.png"),loadImage("res/button_c.png")};
	  cp5.addButton("play")
	     .setValue(128)
	     .setPosition(140,300)
	     .setImages(imgs)
	     .updateSize()
	     ;
	     
	  cp5.addButton("playAgain")
	     .setValue(128)
	     .setPosition(210,300)
	     .setImages(imgs)
	     .updateSize()
	     ;

	  cp5 = new ControlP5(this);

	  controlWindow = cp5.addControlWindow("controlP5window", 100, 100, 400, 200)
	    .hideCoordinates()
	      .setBackground(color(40))
	        ;

	  cp5.addSlider("sliderValue")
	    .setRange(0, 255)
	      .setPosition(40, 40)
	        .setSize(200, 29)
	          .setWindow(controlWindow)
	            ;	  
	}


	public void draw() {
		 background(sliderValue);
		  lights();
		  gfx.mesh(mesh,false,10);
	  
	}

	void myTextfield(String theValue) {
	  println(theValue);
	}

	void myWindowTextfield(String theValue) {
	  println("from controlWindow: "+theValue);
	}

	public void keyPressed() {
	  if (key==',') cp5.window("controlP5window").hide();
	  if (key=='.') cp5.window("controlP5window").show();
	  // controlWindow = controlP5.addControlWindow("controlP5window2",600,100,400,200);
	  // controlP5.controller("sliderValue1").moveTo(controlWindow);

	  if (key=='d') {
	    if (controlWindow.isUndecorated()) {
	      controlWindow.setUndecorated(false);
	    } 
	    else {
	      controlWindow.setUndecorated(true);
	    }
	  }
	  if (key=='t') {
	    controlWindow.toggleUndecorated();
	  }
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { controlp5.ejemplos.DrawIntoCanvas.class
				.getName() });
	}

}
