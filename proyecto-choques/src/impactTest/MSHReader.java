package impactTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MSHReader {

	public void loadBinary() throws IOException {
	
	ArrayList<Node> listaDeNodos = new ArrayList<Node>();
	BufferedReader fileReader = null;
	int cantidadDeNumeros = 0;
	
	char[] coordenadaX;
	char[] coordenadaY;
	char[] coordenadaZ;
    
    try {
    	fileReader = new BufferedReader(new FileReader("C:/Users/Ivan/Desktop/tuvieja.msh"));
    } catch (Exception e) { 
       	e.printStackTrace();
    }
     
	fileReader.skip(11+1+1); //this.skippearEncabezadoFormatoMesh(); +1+1 --> \r y \n
	fileReader.skip(7+1+1);	//	+1+1 --> \r y \n	//this.skippearFormatoMesh();
	fileReader.skip(14+1+1); //this.skippearFinEncabezadoFormatoMesh(); +1+1 --> \r y \n
	fileReader.skip(6+1+1); //this.skippearEncabezadoNodos();
	
	int cantidadDeNodos = this.getCantidadDeNodos(fileReader);

	int j = 0;
	
	for(int i = 0; i <= cantidadDeNodos; i++){
		
		String lineaDeNodo = fileReader.readLine();
		char[] posicionesDelNodo = lineaDeNodo.toCharArray();
		
		this.bypassearPrimerNumero(j, posicionesDelNodo);
		this.contarCantidadCifrasDeLaCoordenada(cantidadDeNumeros, j, posicionesDelNodo);
		
		coordenadaX = new char[cantidadDeNumeros];
		
		for(int m = j, p = 0 ; m <= posicionesDelNodo.length && posicionesDelNodo[m] != ' '; m++,p++){
			coordenadaX[p] = posicionesDelNodo[m]; 
		}
		
		this.bypassearPrimerNumero(j, posicionesDelNodo);
		this.contarCantidadCifrasDeLaCoordenada(cantidadDeNumeros, j, posicionesDelNodo);
		
		coordenadaY = new char[cantidadDeNumeros];
			
		for(int m = j, p = 0 ; m <= posicionesDelNodo.length && posicionesDelNodo[m] != ' '; m++,p++){
			coordenadaY[p] = posicionesDelNodo[m]; 
		}
		
		this.bypassearPrimerNumero(j, posicionesDelNodo);
		coordenadaZ = new char[posicionesDelNodo.length-j];
		
		for(int m = 0, p = 0 ; m < posicionesDelNodo.length-j; m++,p++){
			coordenadaZ[p] = posicionesDelNodo[m]; 
		}
			
			float posicionX = Integer.parseInt(String.valueOf(coordenadaX));
			float posicionY = Integer.parseInt(String.valueOf(coordenadaY));
			float posicionZ = Integer.parseInt(String.valueOf(coordenadaZ));
			
			Node nuevoNodo = new Node();
			
			nuevoNodo.setX_pos_orig(posicionX);
			nuevoNodo.setY_pos_orig(posicionY);
			nuevoNodo.setZ_pos_orig(posicionZ);
			
			listaDeNodos.add(nuevoNodo);
					 
		}
	
		fileReader.skip(9+1+1);
		fileReader.skip(9+1+1);
	
		int cantidadDeElementos = this.getCantidadDeElementos(fileReader);
	
		//Leo elementos
		for(int i=0; i<= cantidadDeElementos; i++){
			String lineaDeElementos = fileReader.readLine();
			char[] posicionesDeElementos = lineaDeElementos.toCharArray();
			
			this.bypassearPrimerNumero(j, posicionesDeElementos);
			//Habr�a que cambiar Object por un tipo que comprenda a todos los poligonos y figuras
			Object tipoDeElemento = this.crearTipoDeElemento(this.getTipoDelElemento(cantidadDeNumeros,j,posicionesDeElementos));
			int numeroDeTagsDelElemento = this.getNumeroDeTagsDelElemento(cantidadDeNumeros,j,posicionesDeElementos);
			//�Se podr� hacer un diccionario con m�todos en cascada que sean strings y con el invoke invocarlos? --> Estoy relimado
			
			if(numeroDeTagsDelElemento == 3)
			for(int l = 0; l <= numeroDeTagsDelElemento; l++){
				
			}
		}
			
	}

	private int getNumeroDeTagsDelElemento(int cantidadDeNumeros, int j,
			char[] posicionesDeElementos) {
				return j;
		// TODO Auto-generated method stub
		
	}


	private Object crearTipoDeElemento(int tipoDelElemento) {
		// TODO Auto-generated method stub
		return null;
	}


	private int getTipoDelElemento(int cantidadDeNumeros, int j,char[] posicionesDeElementos) {
			this.contarCantidadCifrasDeLaCoordenada(cantidadDeNumeros, j, posicionesDeElementos);
			return cantidadDeNumeros;
	}


	private int contarCantidadCifrasDeLaCoordenada(int cantidadDeNumeros,int j, char[] posicionesDelNodo) {
		cantidadDeNumeros = 0;

		for(int m = j; m <= posicionesDelNodo.length && posicionesDelNodo[m] != ' '; m++){
			
			cantidadDeNumeros++;
			
		}
		return cantidadDeNumeros;
	}


	private void bypassearPrimerNumero(int j, char[] posicionesDelNodo) {
		for( ; j <= posicionesDelNodo.length && posicionesDelNodo[j] != ' '; j++);
		
		j++;
	}


    private int getCantidadDeElementos(BufferedReader fileReader) {
		return this.getCantidadDeNodos(fileReader);
	}


	private int getCantidadDeNodos(BufferedReader fileReader) {
	
    	String linea = null;
	
    	try {
    		linea = fileReader.readLine();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	
    	Integer numero = new Integer(linea);
    	return numero.intValue();
}


public static void main(String _args[]) {
	try {
		new MSHReader().loadBinary();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	}
}

