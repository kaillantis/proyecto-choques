package proyecto;

import toxi.geom.Vec3D;
import toxi.geom.mesh.Face;
import toxi.geom.mesh.TriangleMesh;

public class Modelo {
	
	private TriangleMesh mesh;
	private Vec3D posicion;
	private Vec3D meshColor;
	private int index;
	
	public Modelo() {
		this.mesh = new TriangleMesh();
		this.posicion = new Vec3D(0, 0, 0);
		meshColor = new Vec3D(255, 255, 255);
	}


	public void setName(String nombreMesh) {
		this.getMesh().name = nombreMesh;
	}

	public void updatePosX(float value) {
		this.posicion.x = value;
	}
	
	public void updatePosY(float value) {
		this.posicion.y = value;
	}
	
	public void updatePosZ(float value) {
		this.posicion.z = value;
	}

	public void rendeNewPos(Main screen) {
		this.getMesh().center(posicion);
		
		screen.beginShape(Main.TRIANGLES);

		int num = this.getMesh().getNumFaces();
		for (int i = 0; i < num; i++) {
			Face f = this.getMesh().faces.get(i);
			Vec3D col = meshColor;
			screen.fill(col.x, col.y, col.z);
			vertex(f.a, screen);
			screen.fill(col.x, col.y, col.z);
			vertex(f.b, screen);
			screen.fill(col.x, col.y, col.z);
			vertex(f.c, screen);
		}
		screen.endShape();
	}
	
	private void vertex(Vec3D v, Main screen) {
		screen.vertex(v.x, v.y, v.z);
	}

	public TriangleMesh getMesh() {
		return mesh;
	}

	public void setMesh(TriangleMesh mesh) {
		this.mesh = mesh;
	}

	public void changeMeshColor(Vec3D color) {
		this.meshColor = color;
	}

	public void setIndex(int i) {
		this.index = i;
	}

	public int getIndex() {
		return this.index;
	}

}
