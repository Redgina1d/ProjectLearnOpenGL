package core;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;


import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import core.utils.Constants;

public class WindowManager {

	//0.5625
	//1.7777778
	
	private final String title;
	
	private int width, height;
	private long window;
	
	private boolean resize, vSync;
	
	private final Matrix4f projMatrix;
	
	public WindowManager (String title, int width, int height, boolean vSync) {
		this.title = title;
		this.height = height;
		this.width = width;
		this.vSync = vSync;
		projMatrix = new Matrix4f();
	}
	
	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will NOT be resizable
		glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW_TRUE);
		glfwWindowHint(GLFW.GLFW_DECORATED, GLFW_FALSE);
		//glfwWindowHint(GLFW.GLFW_DECORATED, GLFW_FALSE);
		glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);  // OpenGL 3
		glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);  // OpenGL 3.2
		glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		width = vidMode.width();
		height = vidMode.height();
		
		boolean maximized = true;
		if (width == 0 || height == 0) {
			width = vidMode.width();
			height = vidMode.height();
			glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW_TRUE);
		}
		
		window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
			this.width = width;
			this.height = height;
		});
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
				try {
					glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else if (key == GLFW.GLFW_KEY_MINUS && action == GLFW_RELEASE) {
				try {
					GLFW.glfwIconifyWindow(window);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
		if(maximized)
			GLFW.glfwMaximizeWindow(window);
		else {
			//
		}
		GLFW.glfwMakeContextCurrent(window);
		
		if(isvSync()) {
			GLFW.glfwSwapInterval(1);
		}
		
		GLFW.glfwShowWindow(window);
		
		GL.createCapabilities();
		
		GL11.glClearColor(0.4f, 0.2f, 0.3f, 0.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_ALPHA);
	}
	
	public void update() {

		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();

	}
		
	public void cleanup() {
		GLFW.glfwDestroyWindow(window);
		
	}
	
	public void setClearColour(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}
	
	public boolean isKeyHit(int key) {
		return (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS);
	}
	
	
	public boolean windowShouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public String getTitle() {
		return title;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Matrix4f getProjMatrix() {
		return projMatrix;
	}
	
	public Matrix4f updateProjMatrix() {
		float aspectRatio = (float) width / height;
		return projMatrix.setPerspective(Constants.FOV, aspectRatio, Constants.Z_NEAR, Constants.Z_FAR);
	}
	
	public Matrix4f updateProjMatrix(Matrix4f matrix, int width, int height) {
		float aspectRatio = (float) width / height;
		return matrix.setPerspective(Constants.FOV, aspectRatio, Constants.Z_NEAR, Constants.Z_FAR);
	}
	
	public void setTitle(String titl) {
		GLFW.glfwSetWindowTitle(window, titl);
	}
	
	public boolean isResize() {
		return resize;
	}
	
	public boolean isvSync() {
		return vSync;
	}
	
	public long getWindowHandle() {
		return window;
	}
	
	public void setResize(boolean resize) {
		this.resize = resize;
	}

}
