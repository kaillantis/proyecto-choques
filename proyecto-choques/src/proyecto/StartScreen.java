package proyecto;

import controlP5.Button;
import controlP5.ControlFont;

public class StartScreen implements ScreenPhase {
	private Main screen;

	public StartScreen(Main screen) {
		this.screen = screen;

	}

	@Override
	public void drawScreen() {
		screen.background(0);		
//		screen.cp5.draw();
	}

	@Override
	public void setup() {
		screen.addButton("Nueva simulacion", 200, 25, 250, 200, new OpenPreScreenListener(screen));
		
//		Button b = screen.cp5.addButton("Nueva Simulacion")
//			.setPosition(100, 120)
//			.addCallback(new OpenPreScreenListener(screen));
//			
//		b.getCaptionLabel().setFont(cf1).toUpperCase(false);
//		b.getCaptionLabel().setPadding(10, 10);
//		b.getCaptionLabel().setFixedSize(true);
//		b.setWidth(ControlFont.getWidthFor("Nueva Simulacion", b.getCaptionLabel(), screen)+ 20);
//		System.out.print(ControlFont.getWidthFor("Nueva Simulacion", b.getCaptionLabel(), screen));
		
		screen.cp5.setAutoDraw(true);
		screen.addButton("Ver resultados", 200, 25, 250, 250);
		screen.setTitle("Pantala principal");
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
