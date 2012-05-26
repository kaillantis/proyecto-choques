package choques;

import controlP5.ControlP5;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PImage;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import toxi.processing.ToxiclibsSupport;


public class Choques extends PApplet {

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
//	  camera = new PeasyCam(this, 0, 0, 0, 50);
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

	  
	}

	public void draw() {
	  background(51);
	  lights();
//	  translate(width/2,height/2,0);
//	  rotateX((float) -.5);
//	  rotateY((float) -.5);
//	  rotateX((float) (mouseY*0.01));
//	  rotateY((float) (mouseX*0.01));
	  
//	  gfx.origin(new Vec3D(),200);
	  noStroke();
//	  gfx.mesh(mesh,false,10);
//	  gfx.mesh(mesh);
	  cp5.draw();
	}
	public static void main(String _args[]) {
		PApplet.main(new String[] { choques.Choques.class.getName() });
	}
}
