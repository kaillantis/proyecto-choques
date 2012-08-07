package proyecto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import util.IoUtils;

public class CDVReader {
	Writer msh, stl;
	public CDVReader(String path) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String str;
			FileOutputStream meshOS = new FileOutputStream("temp.stl");
			if (!in.readLine().equals("$ComienzoFormatoCDV")){
				System.out.print("Formato no es CDV ");
			}
			while ((str = in.readLine()) != null) {
				if (str.equals("$ComienzoFormatoSTL")) {
					stl = new StringWriter();
					while ((str = in.readLine()) != null) {
						if (str.equals("$FinFormatoSTL")){
							break;
						}
						byte buf[] = str.getBytes(); 
						stl.write(str);
						stl.write('\n');
						meshOS.write(buf);
						meshOS.write('\n');
					}
					stl.close();
					continue;
				}
				if (str.equals("$ComienzoFormatoMSH")) {
					msh = new StringWriter();
					while ((str = in.readLine()) != null) {
						if (str.equals("$FinFormatoMSH")){
							break;
						}
						msh.write(str);
						stl.write('\n');
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TriangleMesh getMesh() {
		Reader tempReader = new StringReader(stl.toString());
		InputStream meshIS;
		try {
//			meshIS = new ReaderInputStream(tempReader);
			meshIS = new FileInputStream("temp.stl");
			
			
			FileOutputStream meshOS = new FileOutputStream("temp.stl");
			IoUtils.copy(meshIS, meshOS);
//			return (TriangleMesh) new STLReader().loadBinary(meshIS, "Mesh", STLReader.TRIANGLEMESH); 
			return (TriangleMesh) new STLReader().loadBinary("temp.stl",STLReader.TRIANGLEMESH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TriangleMesh();
	}
}
