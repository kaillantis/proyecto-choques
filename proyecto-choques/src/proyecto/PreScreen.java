package proyecto;

import controlP5.ControlFont;

public class PreScreen {
	private Main screen;

	public PreScreen(Main screen) {
		this.screen = screen;
		
		screen.clearScreen();
		
		ControlFont cf1 = new ControlFont(screen.createFont("Arial",20));
		
		screen.cp5.addButton("Aca hacemos el preprocesamiento wachi!")
		.setValue(100)
		.setPosition(100, 120)
		.setSize(200, 19)
		.getCaptionLabel().setFont(cf1);
		
	screen.cp5.addButton("Volver atras")
		.setValue(100)
		.setPosition(100, 220)
		.setSize(200, 19)
		.addCallback(new OpenStartScreenListener(screen))
		.getCaptionLabel().setFont(cf1);
		
	}
}
