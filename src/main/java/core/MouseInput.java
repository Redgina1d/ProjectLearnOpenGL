package core;

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
	/*
	public void init() {
		GLFW.glfwSetCursorPosCallback(Launcher.getWindow().getWindowHandle(), (window, xpos, ypos) -> {
			currPos.x = xpos;
			currPos.y = ypos;
		});
		GLFW.nglfwSetCursorEnterCallback(Launcher.getWindow().getWindowHandle(), (window, entered) -> {
			inWindow = entered;
		});
	}
	*/
}
