package proyecto;

import controlP5.ControlFont;
import controlP5.Group;

public class StartScreen {
	private Main screen;

	public StartScreen(Main screen) {
		this.screen = screen;
		ControlFont cf1 = new ControlFont(screen.createFont("Arial",20));
		
		screen.clearScreen();
		
		screen.cp5.addButton("Nueva Simulacion")
			.setValue(100)
			.setPosition(100, 120)
			.setSize(200, 19)
			.addCallback(new OpenPreScreenListener(screen))
			.getCaptionLabel().setFont(cf1);
		
		screen.cp5.addButton("Ver resultados")
			.setValue(100)
			.setPosition(100, 220)
			.setSize(200, 19)
			.getCaptionLabel().setFont(cf1);
	}
}
