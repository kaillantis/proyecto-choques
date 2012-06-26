package proyecto;

import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;

public class OpenPreScreenListener implements CallbackListener {

	private Main screen;
	
	public OpenPreScreenListener(Main screen) {
		this.screen = screen;
	}

	@Override
	public void controlEvent(CallbackEvent theEvent) {
		if (theEvent.getAction() == ControlP5.ACTION_RELEASED) {
			System.out.print("Presione!");
			new PreScreen(screen);
		}
	}
	

}
