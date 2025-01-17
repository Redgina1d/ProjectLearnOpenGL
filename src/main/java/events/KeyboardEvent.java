package events;

import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;
import core.WindowManager;

// idk what the damn it should be
@Deprecated
public class KeyboardEvent implements IEvent {

	private ArrayList<Integer> keyCombo;
	private WindowManager window;
	
	private int key;
	private int holdTime;
	
	public KeyboardEvent(WindowManager window, ArrayList<Integer> keyCombo) {
		this.window = window;
		this.keyCombo = keyCombo;
	}
	public KeyboardEvent(WindowManager window, int key, int holdTime) {
		this.window = window;
		this.key = key;
		this.holdTime = holdTime;
	}
	
	
	public void onRelease(Runnable action) {
		if (GLFW.glfwGetKey(window.getWindowHandle(), key) == GLFW.GLFW_RELEASE) {
			action.run();
		}
	}
	public void whileHeld(Runnable action, int rate) {
		
	}
	public void onHit(Runnable action) {
		
	}
	@Override
	public void init(){
		// TODO Auto-generated method stub
		
	}
	@Override
	public void input() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}
}
