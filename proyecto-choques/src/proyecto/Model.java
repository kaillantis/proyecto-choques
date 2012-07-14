package proyecto;

import toxi.geom.Vec3D;
import toxi.geom.mesh.Face;
import toxi.geom.mesh.TriangleMesh;

public class Model {
	private static int IdCounter = 0;
	private TriangleMesh mesh;
	private Vec3D position;
	private Vec3D meshColor;
	private Vec3D force;
	private MaterialItem material;
	private int id;

	public Model() {
		this(new TriangleMesh());
	}

	public Model(TriangleMesh mesh2) {
		this.mesh = mesh2;
		this.position = new Vec3D(0, 0, 0);
		this.force = new Vec3D(0, 0, 0);
		this.meshColor = new Vec3D(255, 255, 255);
		this.id = this.generateId(); 
	}

	private int generateId() {
		int newId = IdCounter;
		IdCounter++;
		return newId;
	}

	public Vec3D getPosition(){
		return position;
	}
	public Vec3D getForce(){
		return force;
	}

	public void updatePosX(float value) {
		this.position.x = value;
	}

	public void updatePosY(float value) {
		this.position.y = value;
	}

	public void updatePosZ(float value) {
		this.position.z = value;
	}

	public void updateForceX(float value) {
		this.force.x = value;
	}

	public void updateForceY(float value) {
		this.force.y = value;
	}

	public void updateForceZ(float value) {
		this.force.z = value;
	}

	public void mesh(Main screen) {
		mesh.center(position);

		screen.beginShape(Main.TRIANGLES);

		int num = mesh.getNumFaces();
		for (int i = 0; i < num; i++) {
			Face f = mesh.faces.get(i);
			screen.fill(meshColor.x, meshColor.y, meshColor.z);
			vertex(f.a, screen);
			screen.fill(meshColor.x, meshColor.y, meshColor.z);
			vertex(f.b, screen);
			screen.fill(meshColor.x, meshColor.y, meshColor.z);
			vertex(f.c, screen);
		}
		screen.endShape();
	}

	private void vertex(Vec3D v, Main screen) {
		screen.vertex(v.x, v.y, v.z);
	}

	public void setMaterial(MaterialItem mat) {
		material = mat;
		meshColor = mat.getColor();
	}
	public MaterialItem getMaterial(){
		return material;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mesh == null) ? 0 : mesh.hashCode());
//		Main.print("Class hash: " + result + "\n");
		return result;
	}

	public int getId() {
		return id;
	}

}
