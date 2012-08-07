package proyecto;

import java.util.ArrayList;
import java.util.List;

public class MaterialList {
	private static List<Material> materialList = new ArrayList<Material>();
	
	static {
		MaterialList.addMaterial("Plata", "E = 68.9441 NU = 0.370000 RHO = 0.0000105 FAILURE_STRESS = 0.1240994 YIELD_STRESS = 0.05515528 EP = 0.01",192,192,192);
		MaterialList.addMaterial("Plomo","E = 13.78882 NU = 0.425000 RHO = 0.00001134 FAILURE_STRESS = 0.01792547 YIELD_STRESS = 0.008962733  EP = 0.001", 119,117,118);
	}
	
	public static List<Material> getMaterialList() {
		return materialList;
	}
	
	public static void addMaterial(String name, String chatacteristics, int colorR, int colorG, int colorB){
		Material newItem = new Material(name,chatacteristics, colorR,colorG,colorB);
		materialList.add(newItem);
	}
	
	public static Material getMaterial(String name) {
		for (Material tempItem : materialList) {
			if (tempItem.getName().equals(name)){
				return tempItem;
			}
		}
		return null;
	}
}
