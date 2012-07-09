package proyecto;

import peasy.PeasyCam;
import toxi.geom.Vec3D;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.DropdownList;

public class PreScreen implements ScreenPhase, ControlListener {
	private static final String NOMBRE_MESH_2 = "Mesh 2";
	private static final String NOMBRE_MESH_1 = "Mesh 1";
	private Main screen;
	Modelo mesh1;
	Modelo mesh2;
	Modelo focusedMesh;
	Vec3D meshColor = new Vec3D(255, 255, 255);
	private static PeasyCam camera = null;
	private String file = "";
	private float forceX;
	private float forceY;
	private float forceZ;
	private DropdownList modeloList;
	private DropdownList matList;

	public PreScreen(Main screen) {
		this.screen = screen;
	}

	@Override
	public void drawScreen() {
		screen.background(51);
		// screen.noStroke();
		screen.lights();
		
		

		if (screen.cp5.getWindow(screen).isMouseOver()) {
			camera.setActive(false);
		} else {
			camera.setActive(true);
		}

		mesh1.setName(NOMBRE_MESH_1);
		mesh1.rendeNewPos(screen);
//		mesh1.center(new Vec3D(posX, posY, posZ));
//		mesh(mesh1);
		mesh2.setName(NOMBRE_MESH_2);
		mesh2.rendeNewPos(screen);
//		mesh2.name = NOMBRE_MESH_2;
//		mesh2.center(new Vec3D(posXmodelo2, posYmodelo2, posZmodelo2));
//		mesh(mesh2);
		
		camera.beginHUD();
		screen.noLights();
		screen.cp5.draw();
		camera.endHUD();
		// Main.print(file);
		// Main.print("\nposX: "+ posX);
	}

	@Override
	public void setup() {
		screen.setTitle("Preprocesamiento");
		screen.addButton("Volver atras", 130, 25, 1150, 5, this);
		screen.addButton("Seleccionar modelo 1", 200, 25, 15, 15, this);
		screen.addButton("Seleccionar modelo 2", 200, 25, 15, 50, this);
		screen.addSlider("posX", "Posicion en X", 200, 15, 15, 225, -250, 250,
				this);
		screen.addSlider("posY", "Posicion en Y", 200, 15, 15, 250, -250, 250,
				this);
		screen.addSlider("posZ", "Posicion en Z", 200, 15, 15, 275, -250, 250,
				this);

		screen.addSlider("forceX", "Fuerza en X (N)", 200, 15, 15, 325, 0, 100,
				this);
		screen.addSlider("forceY", "Fuerza en Y (N)", 200, 15, 15, 350, 0, 100,
				this);
		screen.addSlider("forceZ", "Fuerza en Z (N)", 200, 15, 15, 375, 0, 100,
				this);

		screen.addButton("Procesar", 300, 30, 490, 600, this);

		addMaterialList();
		addModelosList();

		if (camera == null) {
			camera = new PeasyCam(screen, 500);
		} else {
			camera.setActive(true);
			camera.reset(0);
		}
		screen.cp5.setAutoDraw(false);

		mesh1 = new Modelo();
		mesh1.setName(NOMBRE_MESH_1);
		mesh2 = new Modelo();
		mesh2.setName(NOMBRE_MESH_2);
//		mesh2 = new TriangleMesh(NOMBRE_MESH_2);

	}

	private void addModelosList() {
		modeloList = screen.cp5.addDropdownList("Modelos");
		modeloList.setPosition(15, 175);
		modeloList.setSize(200, 200);
		modeloList.setBarHeight(18);
		modeloList.getCaptionLabel().setFont(screen.smallFont).toUpperCase(false);
		modeloList.addListener(this);
	}

	private void addMaterialList() {
		matList = screen.cp5.addDropdownList("Material");
		matList.setPosition(15, 150);
		matList.setSize(200, 200);
		matList.setBarHeight(18);
		matList.getCaptionLabel().setFont(screen.smallFont).toUpperCase(false);
		matList.addItems(Material.getMaterialList());
		matList.addListener(this);

	}

	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isController()) {
			if (theEvent.getController().getName() == "posX") {
				if(focusedMesh!= null){
					focusedMesh.updatePosX(theEvent.getController().getValue());
				}
			}

			if (theEvent.getController().getName() == "posY") {
				if(focusedMesh!= null){					
					focusedMesh.updatePosY(theEvent.getController().getValue());
				}
			}

			if (theEvent.getController().getName() == "posZ") {
				if(focusedMesh!= null){						
					focusedMesh.updatePosZ(theEvent.getController().getValue());
				}
			}

			if (theEvent.getController().getName() == "forceX") {
				forceX = theEvent.getController().getValue();
			}

			if (theEvent.getController().getName() == "forceY") {
				forceY = theEvent.getController().getValue();
			}

			if (theEvent.getController().getName() == "forceZ") {
				forceZ = theEvent.getController().getValue();
			}

			if (theEvent.getController().getName() == "Procesar") {
				process();
			}

			if (theEvent.getController().getName() == "Seleccionar modelo 1") {
				new Thread(new Runnable() {
					public void run() {
						synchronized (screen) {
							mesh1.setMesh(getMeshFromFile(screen.selectInput()));
							modeloList.addItem("Modelo 1", 1);
						}
					}
				}).start();
			}

			if (theEvent.getController().getName() == "Seleccionar modelo 2") {
				new Thread(new Runnable() {
					public void run() {
						synchronized (screen) {
							mesh2.setMesh(getMeshFromFile(screen.selectInput()));
							modeloList.addItem("Modelo 2", 2);
						}
					}

				}).start();
			}

			if (theEvent.getController().getName() == "Volver atras") {
				screen.changeScreen(new StartScreen(screen));
			}
		}

		if (theEvent.isGroup()) {
			if (theEvent.getGroup().getName() == "Material") {
				MaterialItem mat = Material.getMaterialList().get(
						(int) theEvent.getGroup().getValue());
				
				if(focusedMesh != null){
					this.focusedMesh.changeMeshColor(mat.getColor());
				}
			}
		}
		
		if (theEvent.isGroup()) {
			if (theEvent.getGroup().getName() == "Modelos") {
				if(theEvent.getGroup().getValue() == 1){
					this.focusedMesh = mesh1;
				}
				else{
					this.focusedMesh = mesh2;
				}
			}
		}
	}

	protected TriangleMesh getMeshFromFile(String selectedFile) {
		if (selectedFile != null) {
			return (TriangleMesh) new STLReader().loadBinary(
					screen.sketchPath(selectedFile), STLReader.TRIANGLEMESH);
		}
		return null;
		
	}

	private void process() {
		// Aca se empieza el procesamiento.
	}

	@Override
	public void destroy() {
		camera.reset(0);
		camera.setActive(false);
		screen.camera();
	}
}
