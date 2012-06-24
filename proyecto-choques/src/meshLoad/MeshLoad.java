package meshLoad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import processing.core.PApplet;
import toxi.geom.Vec3D;

public class MeshLoad extends PApplet {
	private static final long serialVersionUID = 6637231894245735116L;
	
	@Override
	public void setup() {
		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(MeshLoad.class.getResourceAsStream("mesh/test.msh")));
//			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + "mesh/test.msh"));
			BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")).getParent() + File.separator + "mesh" + File.separator +"test.msh"));
			
			String line = new String();
			int nodeNumber;
			int elemNumber;
			ArrayList<Vec3D> nodeList = new ArrayList<Vec3D>();
			
			//Skippear hasta que hayan nodos
			while((line = br.readLine()).compareTo("$Nodes") != 0);
			//Leer numero de nodos
			nodeNumber = Integer.parseInt(br.readLine());
			System.out.print("Numero de nodos: " + nodeNumber + "\n");
			//Leer nodos
			for (int i=0;i<nodeNumber;i++){
				Scanner scanner = new Scanner(br.readLine());
				scanner.useLocale(Locale.US);
			    if ( scanner.hasNext() ){
			        float x,y,z;
			        //descartamos el numero de nodo, se obtiene desde la posicion en el array
			        scanner.nextInt();
			        x = scanner.nextFloat();
			        y = scanner.nextFloat();
			        z = scanner.nextFloat();
//			        System.out.print("Nodo: " + nodo + " Vector: " + x + " " + y + " " + z + "\n");
			        nodeList.add(new Vec3D(x, y, z));
			    }
			}
			
			//Skippear hasta que hayan elements
			while((line = br.readLine()).compareTo("$Elements") != 0);
			elemNumber = Integer.parseInt(br.readLine());
			System.out.print("Numero de elementos: " + elemNumber + "\n");
			for (int i=0;i<nodeNumber;i++){
				Scanner scanner = new Scanner(br.readLine());
				scanner.useLocale(Locale.US);
				
			}
			
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
