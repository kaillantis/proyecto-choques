package proyecto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

import peasy.PeasyCam;
import toxi.geom.Vec3D;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.ListBox;
import controlP5.ListBoxItem;
import controlP5.Slider;

public class PreScreen implements ScreenPhase, ControlListener {
	private Main screen;
	TriangleMesh mesh;
	Vec3D meshColor = new Vec3D(255, 255, 255);
	private List<Model> modelList = Collections.synchronizedList(new ArrayList<Model>());
	private static PeasyCam camera = null;
	private ListBox modelsListBox;
	private Model selectedModel;
	private Slider posX, posY, posZ, forceX, forceY, forceZ;
	private DropdownList matList;
	private List<ListBoxItem> modelItems = Collections.synchronizedList(new ArrayList<ListBoxItem>());

	public PreScreen(Main screen) {
		this.screen = screen;
	}

	@Override
	public void drawScreen() {
//		synchronized (screen) {
			screen.background(51);
			screen.noStroke();
			screen.lights();

			if (screen.cp5.getWindow(screen).isMouseOver()) {
				camera.setActive(false);
			} else {
				camera.setActive(true);
			}

			mesh();

			camera.beginHUD();
			screen.noLights();
			screen.cp5.draw();
			camera.endHUD();
			// Main.print(file);
			// Main.print("\nposX: "+ posX);
//		}
	}

	private void mesh() {
		synchronized (modelList) {
			for (Model model : modelList) {
				if (selectedModel == model) {
					screen.stroke(0);
					model.mesh(screen);
					screen.noStroke();
				} else {
					model.mesh(screen);
				}
			}
		}
	}

	@Override
	public void setup() {
		screen.setTitle("Preprocesamiento");
		screen.addButton("Volver atras", 130, 25, 1150, 5, this);
		screen.addButton("Agregar modelo", 200, 25, 1050, 50, this);

		posX = screen.addSlider("posX", "Posicion en X", 200, 15, 15, 50, -250, 250, this);
		posY = screen.addSlider("posY", "Posicion en Y", 200, 15, 15, 75, -250, 250, this);
		posZ = screen.addSlider("posZ", "Posicion en Z", 200, 15, 15, 100, -250, 250, this);

		forceX = screen.addSlider("forceX", "Fuerza en X (N)", 200, 15, 15, 175, 0, 100, this);
		forceY = screen.addSlider("forceY", "Fuerza en Y (N)", 200, 15, 15, 200, 0, 100, this);
		forceZ = screen.addSlider("forceZ", "Fuerza en Z (N)", 200, 15, 15, 225, 0, 100, this);

		screen.addButton("Procesar", 300, 30, 490, 690, this);

		addMaterialList();
		addModelList();

		if (camera == null) {
			camera = new PeasyCam(screen, 500);
		} else {
			camera.setActive(true);
			camera.reset(0);
		}
		screen.cp5.setAutoDraw(false);

		mesh = new TriangleMesh();
		selectedModel = new Model();
	}

	private void addModelList() {
		modelsListBox = screen.cp5.addListBox("Modelos").setPosition(1050, 110).setSize(200, 120).setItemHeight(18).setBarHeight(18);
		modelsListBox.getCaptionLabel().setFont(screen.defaultFont).toUpperCase(false).align(ControlP5.CENTER, ControlP5.CENTER);
		modelsListBox.addListener(this);
	}

	private void addMaterialList() {
		// screen.cp5.setFont(screen.smallFont);
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
				selectedModel.updatePosX(theEvent.getController().getValue());
			}

			if (theEvent.getController().getName() == "posY") {
				selectedModel.updatePosY(theEvent.getController().getValue());
			}

			if (theEvent.getController().getName() == "posZ") {
				selectedModel.updatePosZ(theEvent.getController().getValue());
			}

			if (theEvent.getController().getName() == "forceX") {
				selectedModel.updateForceX(theEvent.getController().getValue());
			}

			if (theEvent.getController().getName() == "forceY") {
				selectedModel.updateForceY(theEvent.getController().getValue());
			}

			if (theEvent.getController().getName() == "forceZ") {
				selectedModel.updateForceZ(theEvent.getController().getValue());
			}

			if (theEvent.getController().getName() == "Procesar") {
				process();
			}

			if (theEvent.getController().getName() == "Agregar modelo") {
				new Thread(new Runnable() {public void run() {synchronized (screen) {
							loadMesh(screen.selectInput());}}}).start();
			}

			if (theEvent.getController().getName() == "Volver atras") {
				screen.changeScreen(new StartScreen(screen));
			}
		}

		if (theEvent.isGroup()) {
			if (theEvent.getGroup().getName() == "Material") {
				MaterialItem mat = Material.getMaterialList().get((int) theEvent.getGroup().getValue());
				selectedModel.setMaterial(mat);
			}
			if (theEvent.getGroup().getName() == "Modelos") {
//				Main.print("float value: " + theEvent.getGroup().getValue() + "\n");
				int value = (int) (theEvent.getGroup().getValue());
//				Main.print("Select hash: " + value + "\n");
				setSelectedModel(findModelIndexById(value));
				
//				Main.print("After MODEL from findmodelbyhash\n");
			}
		}
	}

	private void setSelectedModel(Model model) {
//		Main.print("Selected Model start\n");
		selectedModel = model;
		setSliders(selectedModel);
//		Main.print("After set Sliders\n");
		setMaterial(selectedModel);
//		Main.print("Selected Model end\n");
//		modelsListBox.
		for (ListBoxItem modelItem : modelItems){
			if (modelItem.getValue() == model.getId()){
				modelItem.setColorBackground(0xff08a2cf);
			} else {
				modelItem.setColorBackground(0xff02344d);
			}
		}
		
//		findModelItem(selectedModel).setColorBackground(screen.color(255));
	}
	
	private ListBoxItem findModelItem(Model model){
		for (ListBoxItem modelItem : modelItems){
			if (modelItem.getValue() == model.getId()){
				return modelItem;
			}
		}
		throw new RuntimeErrorException(null, "findModelItem error. No model found.");
	}

	private void setMaterial(Model selectedModel) {
		MaterialItem material = selectedModel.getMaterial();
		if (material != null) {
			matList.getCaptionLabel().set(material.getName());
		} else {
			matList.getCaptionLabel().set("Material");
		}
	}

	private Model findModelIndexById(int value) {
		for (Model model : modelList) {
			if (model.getId() == value) {
//				Main.print("Before return MODEL from findmodelbyhash\n");
				return model;
			}
		}
		Main.print("findModelByHash failure.\n");
		return null;
	}

	protected TriangleMesh loadMesh(String selectedFile) {
		if (selectedFile != null) {
			mesh = (TriangleMesh) new STLReader().loadBinary(screen.sketchPath(selectedFile), STLReader.TRIANGLEMESH);
			Model newModel = new Model(mesh);
			modelList.add(newModel);
			int modelhash = newModel.getId();
			ListBoxItem newModelItem = modelsListBox.addItem("Model", modelhash);
//			Main.print("Model hash:" + modelhash + "\n");
			modelItems.add(newModelItem);
			setSelectedModel(newModel);
//			selectedModel = newModel;
//			resetSliders();
//			resetMaterial();
			return mesh;
		}
		return null;
	}

	private void resetMaterial() {
		matList.getCaptionLabel().set("Material");
	}

	private void resetSliders() {
		posX.setValue(0);
		posY.setValue(0);
		posZ.setValue(0);
		forceX.setValue(0);
		forceY.setValue(0);
		forceZ.setValue(0);
	}

	private void setSliders(Model model) {
		posX.setValue(model.getPosition().x);
		posY.setValue(model.getPosition().y);
		posZ.setValue(model.getPosition().z);
		forceX.setValue(model.getForce().x);
		forceY.setValue(model.getForce().y);
		forceZ.setValue(model.getForce().z);
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
