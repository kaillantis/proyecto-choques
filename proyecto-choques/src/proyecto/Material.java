package proyecto;

import toxi.geom.Vec3D;

public class Material {
	private String name;
	private String characteristics;
	private Vec3D color;
	
	public Material(String name2, String characteristics, int colorR,int colorG,int colorB){
			this.name = name2;
			this.characteristics = characteristics;
			this.color = new Vec3D(colorR,colorG,colorB); 
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public Vec3D getColor() {
		return color;
	}

}
