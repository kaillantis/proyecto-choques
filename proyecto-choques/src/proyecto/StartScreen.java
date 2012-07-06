package proyecto;

import controlP5.ControlEvent;
import controlP5.ControlListener;

public class StartScreen implements ScreenPhase,ControlListener {
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
		screen.addButton("Nueva simulacion", 200, 25, 250, 200, this);
		
//		b.setWidth(ControlFont.getWidthFor("Nueva Simulacion", b.getCaptionLabel(), screen)+ 20);
//		System.out.print(ControlFont.getWidthFor("Nueva Simulacion", b.getCaptionLabel(), screen));
		
		screen.cp5.setAutoDraw(true);
		screen.addButton("Ver resultados", 200, 25, 250, 250,this);
		screen.setTitle("Pantala principal");
		
	}
	
	@Override
	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isController()) { 
			if(theEvent.getController().getName()=="Nueva simulacion") {
				screen.changeScreen(new PreScreen(screen));
			}	
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
