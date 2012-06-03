package controlp5.ejemplos;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MeshFilter extends FileFilter {

	private String[] extensiones = new String[] {".stl"};
	@Override
	public boolean accept(File archivo) {
		
		for(String extension : extensiones){
			if(archivo.getName().toLowerCase().endsWith(extension))
				return true;
		}
		return false;
	}

	@Override
	public String getDescription() {

		return ".stl";
	}

}
