package test;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import core.Area;
import core.Camera;
import core.ILogic;
import core.Text;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.entity.Model;
import core.utils.Constants;
import core.utils.MouseInput;
import readers.ObjectLoader;
import render.EntityRenderer;
import render.SSRenderer;

public class TestGame implements ILogic {

	private final EntityRenderer eRend;
	private final SSRenderer ssRend;
	
	private final ObjectLoader loader;
	private final WindowManager window;
	
	private Camera camera;
	
	private ArrayList<Entity> entList;
	private ArrayList<Entity> terrainList;
	private ArrayList<Entity> ssList;
	private ArrayList<Entity> txtList;
	
	private ArrayList<Area> areaList;
	private ArrayList<Light> lightList;
	
	private Light light;

	Vector3f camInc;
	
	private long[] cdList;
	
	private boolean[] guiBools;
	
	TestGame() {
		
		eRend = new EntityRenderer();
		ssRend = new SSRenderer();
		
		loader = new ObjectLoader();
		window = Launcher.getWindow();
		
		camera = new Camera();
		entList = new ArrayList<Entity>();
		terrainList = new ArrayList<Entity>();
		ssList = new ArrayList<Entity>();
		txtList = new ArrayList<Entity>();
		
		areaList = new ArrayList<Area>();
		lightList = new ArrayList<Light>();
		
		cdList = new long[200];
		guiBools = new boolean[500];
		
		light = new Light(new Vector3f(0,10.0f,0), new Vector3f(1.9f,1.9f,1.2f));
		lightList.add(light);
		
		camInc = new Vector3f(0,0,0);
		
	}
	
	@Override
	public void init() throws Exception {
		
		
		
		Text txt = new Text("HELLO WORLDD!!", 0.1f, new Vector2f(0.5f, 0.5f), new Vector2f(0.8f, 0.8f), 0);
		
		Model guiModel = loader.loadOBJModel("sqr_1x1", "AIM2");
		Model consoleModel = loader.loadOBJModel("sqr_1x1", "CONSOLE");
		
		Entity crosshair = new Entity(guiModel, new Vector3f(0, 0, 0), 0.0f, 0.1f);
		Entity consoleBackground = new Entity(consoleModel, new Vector3f(0, 0, 0), 0.0f, 1.0f);
		
		Model surfaceModel = loader.loadOBJModel("detailed_surface", "green");
		Entity surface = new Entity(surfaceModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1);
		
		
		ssList.add(crosshair);
		ssList.add(consoleBackground);
		for (int i = 0; i < txt.getChars().size(); i++) {
			txtList.add(txt.getChars().get(i));
		}
		entList.add(surface);
		
		
		guiBools[0] = false;
		
		eRend.init();
		ssRend.init();	
	}

	@Override
	public void input() {
		camInc.set(0, 0, 0);
		if(window.isKeyInteract(GLFW.GLFW_KEY_W, GLFW.GLFW_PRESS))
			camInc.z = -1;
		if(window.isKeyInteract(GLFW.GLFW_KEY_S, GLFW.GLFW_PRESS))
			camInc.z = 1;
		
		if(window.isKeyInteract(GLFW.GLFW_KEY_A, GLFW.GLFW_PRESS))
			camInc.x = -1;
		if(window.isKeyInteract(GLFW.GLFW_KEY_D, GLFW.GLFW_PRESS))
			camInc.x = 1;
		
		if(window.isKeyInteract(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_PRESS))
			camInc.y = 1;
		if(window.isKeyInteract(GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_PRESS))
			camInc.y = -1;
		
		if(window.isKeyInteract(GLFW.GLFW_KEY_SLASH, GLFW.GLFW_PRESS)) {
			if (cdList[0] < System.currentTimeMillis()) {
				if (!guiBools[0]) {
					cdList[0] = System.currentTimeMillis() + 1000L;
					guiBools[0] = true;
				} else {
					cdList[0] = System.currentTimeMillis() + 1000L;
					guiBools[0] = false;
				}
			}
		}
	}

	
	@Override
	public void update(float interval, MouseInput mouseInput) {
		

		
		camera.movePos(camInc.x * Constants.CAM_STEP, camInc.y * Constants.CAM_STEP, camInc.z * Constants.CAM_STEP);
		if(mouseInput.isRightButtonPress()) {
			Vector2f rotVec = mouseInput.getDisVec();
			camera.moveRotation(rotVec.x * Constants.MOUSE_SENSITIVITY, rotVec.y * Constants.MOUSE_SENSITIVITY, 0);
		}
		
	}

	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		eRend.renderList(entList, camera);
		eRend.shader.bind();
		eRend.loadLight(light);
		eRend.shader.unbind();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		ssRend.shader.bind();
		ssRend.render(ssList.get(0), camera);
		if (guiBools[0]) {
			ssRend.render(ssList.get(1), camera);
		}
		ssRend.renderList(txtList, camera);
		ssRend.shader.unbind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
	}

	@Override
	public void cleanup() {
		eRend.cleanup();
		ssRend.cleanup();
		loader.cleanup();
		window.cleanup();
	}
	
}
