package proyecto;

import peasy.PeasyCam;
import toxi.geom.Vec3D;
import toxi.geom.mesh.Face;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.DropdownList;


public class PreScreen implements ScreenPhase, ControlListener{
	private Main screen;
	TriangleMesh mesh;
	Vec3D meshColor= new Vec3D(255,255,255);
	private static PeasyCam camera = null;
	private float posX = 0;
	private float posY = 0;
	private float posZ = 0;
	private String file = "";
	private float forceX;
	private float forceY;
	private float forceZ;

	
	public PreScreen(Main screen) {
		this.screen = screen;		
	}

	@Override
	public void drawScreen() {
		screen.background(51);
//		screen.noStroke();
		screen.lights();

		if (screen.cp5.getWindow(screen).isMouseOver()) {
	        camera.setActive(false);
	      } else {
	        camera.setActive(true);
	      }

		mesh.center(new Vec3D(posX,posY,posZ));
		mesh();
		camera.beginHUD();
		screen.noLights();
		screen.cp5.draw();
		camera.endHUD();
//		Main.print(file);
//		Main.print("\nposX: "+ posX);
	}

	private void mesh() {
		screen.beginShape(Main.TRIANGLES);

		int num = mesh.getNumFaces();
		for (int i = 0; i < num; i++) {
			Face f = mesh.faces.get(i);
			Vec3D col = meshColor;
			screen.fill(col.x, col.y, col.z);
			vertex(f.a);
			screen.fill(col.x, col.y, col.z);
			vertex(f.b);
			screen.fill(col.x, col.y, col.z);
			vertex(f.c);
		}
		screen.endShape();
	}
	
		 
	private void vertex(Vec3D v) {
		  screen.vertex(v.x,v.y,v.z);
		}
	

	@Override
	public void setup() {
		screen.setTitle("Preprocesamiento");
		screen.addButton("Volver atras", 130, 25, 1150, 5,this);
		screen.addButton("Seleccionar modelo", 200, 25, 15, 15, this);
		screen.addSlider("posX","Posicion en X", 200, 15, 15, 50, -250,250,this);
		screen.addSlider("posY","Posicion en Y", 200, 15, 15, 75, -250,250,this);
		screen.addSlider("posZ","Posicion en Z", 200, 15, 15, 100, -250,250,this);
		
		
		screen.addSlider("forceX","Fuerza en X (N)", 200, 15, 15, 175, 0, 100,this);
		screen.addSlider("forceY","Fuerza en Y (N)", 200, 15, 15, 200, 0, 100,this);
		screen.addSlider("forceZ","Fuerza en Z (N)", 200, 15, 15, 225, 0, 100,this);
		
		screen.addButton("Procesar", 300, 30, 490, 690,this);
		
		addMaterialList();
		
		if (camera == null){
			camera = new PeasyCam(screen, 500);
		} else {
			camera.setActive(true);
			camera.reset(0);
		}
		screen.cp5.setAutoDraw(false);
		
		mesh = new TriangleMesh();
		
	}
	
	
	private void addMaterialList() {
//		screen.cp5.setFont(screen.smallFont);
		DropdownList matList = screen.cp5.addDropdownList("Material");
		matList.setPosition(15,150);
		matList.setSize(200, 200);
		matList.setBarHeight(18);
		matList.getCaptionLabel().setFont(screen.smallFont).toUpperCase(false);
		matList.addItems(Material.getMaterialList());
		matList.addListener(this);
		
	}

	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isController()) { 
			if(theEvent.getController().getName()=="posX") {
				posX = theEvent.getController().getValue();
			 }
			
			if(theEvent.getController().getName()=="posY") {
				posY = theEvent.getController().getValue();
			 }
			
			if(theEvent.getController().getName()=="posZ") {
				posZ = theEvent.getController().getValue();
			 }
			
			if(theEvent.getController().getName()=="forceX") {
				forceX = theEvent.getController().getValue();
			 }
			
			if(theEvent.getController().getName()=="forceY") {
				forceY = theEvent.getController().getValue();
			 }
			
			if(theEvent.getController().getName()=="forceZ") {
				forceZ = theEvent.getController().getValue();
			 }
			
			if(theEvent.getController().getName()=="Procesar") {
				process();
			 }
			
			if(theEvent.getController().getName()=="Seleccionar modelo") {
				new Thread(new Runnable() {public void run() {synchronized (screen){
					    		  loadMesh(screen.selectInput());}}}).start();}
			
			if(theEvent.getController().getName()=="Volver atras") {
				screen.changeScreen(new StartScreen(screen));
			}
		}
		
		if (theEvent.isGroup()) {
			if(theEvent.getGroup().getName()=="Material") {
				MaterialItem mat = Material.getMaterialList().get((int) theEvent.getGroup().getValue());
				this.changeMeshColor(mat.getColor());
			 }
		}
	}
	
	private void changeMeshColor(Vec3D color) {
		meshColor = color;
	}

	protected TriangleMesh loadMesh(String selectedFile) {
		if (selectedFile!= null){
			mesh = (TriangleMesh)new STLReader().loadBinary(screen.sketchPath(selectedFile),STLReader.TRIANGLEMESH);
			return mesh;
		}
		return null;
	}
	
	private void process(){
		// Aca se empieza el procesamiento.
	}


	@Override
	public void destroy() {
		camera.reset(0);
		camera.setActive(false);
		screen.camera();
	}
}
