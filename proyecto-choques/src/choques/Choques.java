package choques;

import processing.core.PApplet;
import toxi.geom.Vec3D;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import toxi.processing.ToxiclibsSupport;


public class Choques extends PApplet {

	private static final long serialVersionUID = -7144529710247863651L;
	TriangleMesh mesh;
	ToxiclibsSupport gfx;

	public void setup() {
	  size(600,600,P3D);
	  mesh=(TriangleMesh)new STLReader().loadBinary(sketchPath("/mesh/mesh.stl"),STLReader.TRIANGLEMESH);
//	  mesh=(TriangleMesh)new STLReader().loadBinary(sketchPath("mesh-flipped.stl"),STLReader.TRIANGLEMESH).flipYAxis();
	  gfx=new ToxiclibsSupport(this);
	}

	public void draw() {
	  background(51);
	  lights();
	  translate(width/2,height/2,0);
	  rotateX((float) (mouseY*0.01));
	  rotateY((float) (mouseX*0.01));
	  gfx.origin(new Vec3D(),200);
	  noStroke();
	  gfx.mesh(mesh,false,10);
//	  gfx.mesh(mesh);
	}
	public static void main(String _args[]) {
		PApplet.main(new String[] { choques.Choques.class.getName() });
	}
}
