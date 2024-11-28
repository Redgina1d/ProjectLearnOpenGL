package core.utils;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import test.Launcher;

public class MouseInput {
	
	private final Vector2d prevPos, currPos;
	private final Vector2f disVec;
	
	private boolean inWindow = false, leftButtonPress = false, rightButtonPress = false;
	
	public MouseInput() {
		prevPos = new Vector2d(-1, -1);
		currPos = new Vector2d(0, 0);
		disVec = new Vector2f();
	}
	
	public void init() {
		GLFW.glfwSetCursorPosCallback(Launcher.getWindow().getWindowHandle(), (window, xpos, ypos) -> {
			currPos.x = xpos;
			currPos.y = ypos;
		});
		
		GLFW.glfwSetCursorEnterCallback(Launcher.getWindow().getWindowHandle(), (window, entered) -> {
			inWindow = entered;
		});
		
		GLFW.glfwSetMouseButtonCallback(Launcher.getWindow().getWindowHandle(), (window, button, action, mods) -> {
			leftButtonPress = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
			rightButtonPress = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
		});
	}
	public void input() {
		disVec.x = 0;
		disVec.y = 0;
		if(prevPos.x > 0 && prevPos.y > 0 && inWindow) {
			double x = currPos.x - prevPos.x;
			double y = currPos.y - prevPos.y;
			boolean rotX = x != 0;
			boolean rotY = y != 0;
			if(rotX)
				disVec.y = (float) x;
			if(rotY)
				disVec.x = (float) y;
		}
		prevPos.x = currPos.x;
		prevPos.y = currPos.y;
	}

	public Vector2f getDisVec() {
		return disVec;
	}

	public boolean isLeftButtonPress() {
		return leftButtonPress;
	}

	public boolean isRightButtonPress() {
		return rightButtonPress;
	}
	
	
}
