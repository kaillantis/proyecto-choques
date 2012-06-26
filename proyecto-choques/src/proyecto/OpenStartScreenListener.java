package proyecto;

import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;

public class OpenStartScreenListener implements CallbackListener {

	private Main screen;
	
	public OpenStartScreenListener(Main screen) {
		this.screen = screen;
	}

	@Override
	public void controlEvent(CallbackEvent theEvent) {
		if (theEvent.getAction() == ControlP5.ACTION_RELEASED) {
			new StartScreen(screen);
		}
	}

}
