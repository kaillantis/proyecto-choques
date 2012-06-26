package proyecto;

import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.ControllerInterface;
import processing.core.PApplet;

public class Main extends PApplet {
	private static final long serialVersionUID = -7427606310430827788L;
	
	public enum Phase {
		START, PRE, POST;
	}

	public Phase pantalla = Phase.START;
	public ControlP5 cp5;

	@Override
	public void setup() {
		size(600, 600, OPENGL);
		cp5 = new ControlP5(this);
		drawStartScreen();
	}
	
	private void drawStartScreen() {
		new StartScreen(this);
	}

	private void drawPreScreen() {
		// TODO Auto-generated method stub
		
	}

	private void drawPostScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		background(0);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { proyecto.Main.class.getName() });
	}
	
	public void clearScreen(){
		for (ControllerInterface<?> controller: cp5.getControllerList()){
			controller.remove();
		}
	}
	
}
