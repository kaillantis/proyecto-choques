package proyecto;

import java.util.ArrayList;
import java.util.List;

public class Material {
	private static List<MaterialItem> materialList = new ArrayList<MaterialItem>();
	
	public static List<MaterialItem> getMaterialList() {
		return materialList;
	}
	
	public static void addMaterial(String name, String chatacteristics, int colorR, int colorG, int colorB){
		MaterialItem newItem = new MaterialItem(name,chatacteristics, colorR,colorG,colorB);
		materialList.add(newItem);
	}
	
	public static MaterialItem getMaterial(String name) {
		for (MaterialItem tempItem : materialList) {
			if (tempItem.getName().equals(name)){
				return tempItem;
			}
		}
		return null;
	}
}
