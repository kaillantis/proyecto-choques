package impactTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CDVManager {

	//Asumo que el primer parámetro corresponde al .stl y el 2do al .mshs
	private File archivoCDV = null;

	public void crearArchivoCDV(String pathSTL, String pathMSH){

		BufferedReader fileReaderSTL = this.abrirArchivo(pathSTL);
		BufferedReader fileReaderMSH = this.abrirArchivo(pathMSH);
		BufferedWriter fileWriterCDV = this.crearArchivoCDV();
		String lineaSTL = null;
		String lineaMSH = null;
		
		try {
			fileWriterCDV.write("$ComienzoFormatoCDV");
			fileWriterCDV.newLine();
			fileWriterCDV.write("$ComienzoFormatoSTL");
			fileWriterCDV.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			lineaSTL = fileReaderSTL.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(lineaSTL != null) {
			try {
				fileWriterCDV.write(fileReaderSTL.readLine());
				fileWriterCDV.newLine();
				lineaSTL = fileReaderSTL.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			fileWriterCDV.write("$FinFormatoSTL");
			fileWriterCDV.newLine();
			fileWriterCDV.write("$ComienzoFormatoMSH");
			fileWriterCDV.newLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			lineaMSH = fileReaderMSH.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(lineaMSH != null){
			try {
				fileWriterCDV.write(lineaMSH);
				fileWriterCDV.newLine();
				lineaMSH = fileReaderMSH.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			fileWriterCDV.write("$FinFormatoMSH");
			fileWriterCDV.newLine();
			fileWriterCDV.write("$FinFormatoCDV");
			fileWriterCDV.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
			try {
				fileWriterCDV.flush();
				fileWriterCDV.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private BufferedWriter crearArchivoCDV() {
				
		archivoCDV = new File("C:/Users/Ivan/Desktop/archivo.cdv");
		try {
			archivoCDV.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter fileWriter = null;
		try {
			fileWriter = new BufferedWriter(new FileWriter(archivoCDV));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileWriter;
	}

	private BufferedReader abrirArchivo(String pathArchivo) {
		BufferedReader fileReader = null;
		  try {
		    	 fileReader = new BufferedReader(new FileReader(pathArchivo));
		    } catch (Exception e) { 
		       	e.printStackTrace();
		    }
		return fileReader;
	}
	

	public static void main(String _args[]) {
			new CDVManager().crearArchivoCDV("C:/Users/Ivan/Desktop/tuviejo.stl","C:/Users/Ivan/Desktop/tuvieja.msh");
				
		}
	}



