package proyecto;

import processing.core.PApplet;
import controlP5.CallbackListener;
import controlP5.ControlFont;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.ControllerInterface;
import controlP5.Slider;
import controlP5.Textlabel;

public class Main extends PApplet {
	private static final long serialVersionUID = -7427606310430827788L;

	public ControlP5 cp5;
	private ScreenPhase currentScreenPhase;
	ControlFont defaultFont=new ControlFont(createFont("Arial",20));
	ControlFont smallFont=new ControlFont(createFont("Arial",14));
	
	@Override
	public void setup() {
		size(1280, 720, OPENGL);
		cp5 = new ControlP5(this);
//		ControlFont.RENDER_2X = true;
				
		drawStartScreen();
	}
	

	private void drawStartScreen() {
		currentScreenPhase = new StartScreen(this);
		currentScreenPhase.setup();
	}
	
	public void changeScreen(ScreenPhase phase){
		noLoop();
		currentScreenPhase.destroy();
		this.clearScreen();
		phase.setup();
		currentScreenPhase = phase;
		loop();
	}

	@Override
	public void draw() {
		currentScreenPhase.drawScreen();
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { proyecto.Main.class.getName() });
	}
	
	public void clearScreen(){
		for (ControllerInterface<?> controller: cp5.getAll()){
			controller.remove();
		}
	}
	
	public void addButton(String text, int width, int height, int posX, int posY){
		cp5.addButton(text).
			setSize(width, height)
			.setPosition(posX, posY)
			.getCaptionLabel().setFont(defaultFont).toUpperCase(false);
	}
	
	public void addButton(String text, int width, int height, int posX, int posY, CallbackListener listener){
		cp5.addButton(text).
			setSize(width, height)
			.setPosition(posX, posY)
			.addCallback(listener)
			.getCaptionLabel().setFont(defaultFont).toUpperCase(false);
	}
	
	public void addButton(String text, int width, int height, int posX,	int posY, ControlListener listener) {
		cp5.addButton(text).
		setSize(width, height)
		.setPosition(posX, posY)
		.addListener(listener)
		.getCaptionLabel().setFont(defaultFont).toUpperCase(false).align(ControlP5.CENTER, ControlP5.CENTER);
	}
	
	public Slider addSlider(String varname, String title, int width, int height, int posX, int posY, float min, float max, ControlListener listener){
		Slider slider = cp5.addSlider(varname);
			slider.setCaptionLabel(title)
			.setSize(width, height)
			.setPosition(posX, posY)
			.setRange(min, max)
			.addListener(listener)
			.getCaptionLabel().setFont(smallFont).toUpperCase(false);
		return slider;
	}
	
	public void setTitle(String title){
		Textlabel myTextlabelA = cp5.addTextlabel("label")
                .setText(title)
                .setColor(0xffffff00)
                .setFont(ControlP5.grixel)
                ;
		myTextlabelA.setSize(1280, 30);
//		print(getTextWidth(myTextlabelA));
//        myTextlabelA.setPosition((640 -(getTextWidth(myTextlabelA)/2)), 0);
	}
	
	public int getTextWidth(Controller<?> controller){
//		System.out.print("\n" + ControlFont.getWidthFor(controller.getStringValue(), controller.getCaptionLabel(), this));
		return ControlFont.getWidthFor(controller.getStringValue(), controller.getCaptionLabel(), this);
	}


}
