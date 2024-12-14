package test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import core.Area;
import core.Camera;
import core.EngineManager;
import core.ILogic;
import core.ObjectLoader;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.entity.Model;
import core.entity.Texture;
import core.utils.Constants;
import core.utils.MouseInput;
import render.EntityRenderer;
import render.GUI2DRenderer;
import render.ShaderManager;

public class TestGame implements ILogic {
	
	private final EntityRenderer renderer;
	//private final GUI2DRenderer guiRenderer;
	private final ObjectLoader loader;
	private final WindowManager window;
	
	private ArrayList<Entity> allEntities;

	private ArrayList<Entity> allGUIs;
	private ArrayList<Area> areas;
	private ArrayList<Light> lights;
	private Camera cam;
	private Light light;


	Vector3f camInc;
	
	Entity ent;
	Entity gui;
	
	
	TestGame() {
		renderer = new EntityRenderer();
		//guiRenderer = new GUI2DRenderer();
		window = Launcher.getWindow();
		loader = new ObjectLoader();
		cam = new Camera();
		camInc = new Vector3f(0, 0, 0);
		light = new Light(new Vector3f(50.0f,40.5f,-60.0f), new Vector3f(1.9f,1.9f,1.2f));
		allEntities = new ArrayList<Entity>();
		allGUIs = new ArrayList<Entity>();
		lights = new ArrayList<Light>();
		lights.add(light);
		
		areas = new ArrayList<Area>(); 
	}

	@Override
	public void init() throws Exception {

		renderer.init();
		renderer.initRender(ent, cam, window, light);

		Area domain = new Area(new Vector3f(1.1f, 2.2f, 3.3f), new Vector3f(5.0f, 4.0f, 7.0f));
		
		Model sunModel = loader.loadOBJModel("uv_sphere", Constants.DIR + "/src/main/resources/textures/YELLOW.png");
		Model cubeModel = loader.loadOBJModel("uv_sphere", Constants.DIR + "/src/main/resources/textures/touchgrass.png");
		Model surfaceModel = loader.loadOBJModel("detailed_surface", Constants.DIR + "/src/main/resources/textures/green.png");
		Model skyboxModel = loader.loadOBJModel("skybox", Constants.DIR + "/src/main/resources/textures/sky.png");
		Model pointModel = loader.loadOBJModel("cubepoint", Constants.DIR + "/src/main/resources/textures/red.png");
		Model point2Model = loader.loadOBJModel("cubepoint", Constants.DIR + "/src/main/resources/textures/white.png");
		Model bricks = loader.loadOBJModel("Cube", Constants.DIR + "/src/main/resources/textures/brick_wall.png");
		
		Model guiModel = loader.loadOBJModel("gui", Constants.DIR + "/src/main/resources/textures/brick_wall.png");
		
		pointModel.getTexture().setShineDamper(-1);
		point2Model.getTexture().setShineDamper(-1);
		sunModel.getTexture().setShineDamper(-1);
		surfaceModel.getTexture().setShineDamper(-1);
		cubeModel.getTexture().setShineDamper(1);
		skyboxModel.getTexture().setShineDamper(-1);
		bricks.getTexture().setShineDamper(-1);
		guiModel.getTexture().setShineDamper(-1);
		
		guiModel.getMaterial().setLightAffected(false);
		sunModel.getMaterial().setLightAffected(false);
		pointModel.getMaterial().setLightAffected(false);
		point2Model.getMaterial().setLightAffected(false);
		cubeModel.getMaterial().setLightAffected(true);
		skyboxModel.getMaterial().setLightAffected(false);
		
		areas.add(domain);
		
		Entity p1 = new Entity(pointModel, domain.p1, new Vector3f(0, 0, 0), 0.4f);
		Entity p2 = new Entity(point2Model, domain.getPointByNumber(2), new Vector3f(0, 0, 0), 0.2f);
		Entity p3 = new Entity(point2Model, domain.getPointByNumber(3), new Vector3f(0, 0, 0), 0.2f);
		Entity p4 = new Entity(point2Model, domain.getPointByNumber(4), new Vector3f(0, 0, 0), 0.2f);
		Entity p5 = new Entity(point2Model, domain.getPointByNumber(5), new Vector3f(0, 0, 0), 0.2f);
		Entity p6 = new Entity(point2Model, domain.getPointByNumber(6), new Vector3f(0, 0, 0), 0.2f);
		Entity p7 = new Entity(point2Model, domain.getPointByNumber(7), new Vector3f(0, 0, 0), 0.2f);
		Entity p8 = new Entity(pointModel, domain.p8, new Vector3f(0, 0, 0), 0.4f);
		ent = new Entity(cubeModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.1f);
		Entity skyEntity = new Entity(skyboxModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 10);
		Entity sun = new Entity(sunModel, new Vector3f(50.0f,40.5f,-60.0f), new Vector3f(0, 0, 0), 3);
		Entity surface = new Entity(surfaceModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1);
		Entity brick = new Entity(bricks, new Vector3f(4.0f,10.5f,-3.0f), new Vector3f(0, 0, 0), 1);
		gui = new Entity(guiModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1);
		
		
		allEntities.add(skyEntity);
		allEntities.add(p1);
		allEntities.add(p8);
		//allEntities.add(ent);
		allEntities.add(surface);
		allEntities.add(sun);
		allEntities.add(p2);
		allEntities.add(p3);
		allEntities.add(p4);
		allEntities.add(p5);
		allEntities.add(p6);
		allEntities.add(p7);
		allEntities.add(brick);
		
		allGUIs.add(gui);
		
		

	}


	@Override
	public void input() {
		camInc.set(0, 0, 0);
		if(window.isKeyPressed(GLFW.GLFW_KEY_W))
			camInc.z = -1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_S))
			camInc.z = 1;
		
		if(window.isKeyPressed(GLFW.GLFW_KEY_A))
			camInc.x = -1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_D))
			camInc.x = 1;
		
		if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
			camInc.y = 1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
			camInc.y = -1;
		
	}
	
	//int m = 1;

	@Override
	public void update(float interval, MouseInput mouseInput) {
		
		cam.movePos(camInc.x * Constants.CAM_STEP, camInc.y * Constants.CAM_STEP, camInc.z * Constants.CAM_STEP);
		
		allEntities.get(0).setPos(cam.getPos().x, cam.getPos().y, cam.getPos().z);
		allEntities.get(1).incRotation(0.01f, 0, 0);
		
		if(mouseInput.isRightButtonPress()) {
			Vector2f rotVec = mouseInput.getDisVec();
			cam.moveRotation(rotVec.x * Constants.MOUSE_SENSITIVITY, rotVec.y * Constants.MOUSE_SENSITIVITY, 0);
		}
	}

	@Override
	public void render() {
		if(window.isResize()) {
			GL11.glViewport(0, 0, 5, 5);
			window.setResize(true);
		}
		
		renderer.renderList(allEntities, cam, window);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
		window.cleanup();
	}
}
