package test;


import java.util.ArrayList;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import core.Area;
import core.Camera;
import core.ILogic;
import core.ObjectLoader;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.entity.Model;
import core.utils.Constants;
import core.utils.MouseInput;
import render.EntityRenderer;
import render.SSRenderer;
import render.MagicRenderer;

public class TestGame implements ILogic {
	
	private final EntityRenderer renderer;
	private final SSRenderer gRend;
	private final MagicRenderer mRend;
	
	private final ObjectLoader loader;
	private final WindowManager window;
	
	private ArrayList<Entity> allEntities;
	private ArrayList<Entity> allGUIs;
	
	private ArrayList<Area> areas;
	private ArrayList<Light> lights;
	private Camera cam;
	private Light light;
	
	private ArrayList<Entity> damnList;


	Vector3f camInc;
	
	Entity ent;
	Entity crosshair;
	Entity consoleBackground;
	
	
	TestGame() {
		renderer = new EntityRenderer();
		mRend = new MagicRenderer();
		gRend = new SSRenderer();
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
		
		damnList = new ArrayList<Entity>();
		
		areas = new ArrayList<Area>(); 
	}

	@Override
	public void init() throws Exception {


		
		

		Area domain = new Area(new Vector3f(1.1f, 2.2f, 3.3f), new Vector3f(5.0f, 4.0f, 7.0f));
		
		Model sunModel = loader.loadOBJModel("uv_sphere", Constants.DIR + "/src/main/resources/textures/YELLOW.png");
		Model cubeModel = loader.loadOBJModel("uv_sphere", Constants.DIR + "/src/main/resources/textures/touchgrass.png");
		Model surfaceModel = loader.loadOBJModel("detailed_surface", Constants.DIR + "/src/main/resources/textures/green.png");
		Model skyboxModel = loader.loadOBJModel("skybox", Constants.DIR + "/src/main/resources/textures/sky.png");
		Model pointModel = loader.loadOBJModel("cubepoint", Constants.DIR + "/src/main/resources/textures/red.png");
		Model point2Model = loader.loadOBJModel("cubepoint", Constants.DIR + "/src/main/resources/textures/white.png");
		Model bricks = loader.loadOBJModel("Cube", Constants.DIR + "/src/main/resources/textures/brick_wall.png");
		
		Model guiModel = loader.loadOBJModel("sqr_1x1", Constants.DIR + "/src/main/resources/textures/aim2.png");
		Model consoleModel = loader.loadOBJModel("sqr_1x1", Constants.DIR + "/src/main/resources/textures/console.png");

		
		areas.add(domain);
		
		Entity p1 = new Entity(pointModel, domain.p1, new Vector3f(0, 0, 0), 0.4f);
		Entity p2 = new Entity(point2Model, domain.getPointByNumber(2), new Vector3f(0, 0, 0), 0.2f);
		Entity p3 = new Entity(point2Model, domain.getPointByNumber(3), new Vector3f(0, 0, 0), 0.2f);
		Entity p4 = new Entity(point2Model, domain.getPointByNumber(4), new Vector3f(0, 0, 0), 0.2f);
		Entity p5 = new Entity(point2Model, domain.getPointByNumber(5), new Vector3f(0, 0, 0), 0.2f);
		Entity p6 = new Entity(point2Model, domain.getPointByNumber(6), new Vector3f(0, 0, 0), 0.2f);
		Entity p7 = new Entity(point2Model, domain.getPointByNumber(7), new Vector3f(0, 0, 0), 0.2f);
		Entity p8 = new Entity(pointModel, domain.p8, new Vector3f(0, 0, 0), 0.4f);
		ent = new Entity(cubeModel, new Vector3f(0.1f, 1.5f, 0.1f), new Vector3f(0, 0, 0), 1);
		
		Entity skyEntity = new Entity(skyboxModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 10);
		Entity sun = new Entity(sunModel, new Vector3f(50.0f,40.5f,-60.0f), new Vector3f(0, 0, 0), 3);
		Entity surface = new Entity(surfaceModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1);
		Entity brick = new Entity(bricks, new Vector3f(4.0f,10.5f,-3.0f), new Vector3f(0, 0, 0), 1);
		
		crosshair = new Entity(guiModel, new Vector3f(0, 0, 0), 0.0f, new Vector2f(0.1f, 0.1f));
		consoleBackground = new Entity(consoleModel, new Vector3f(0, 0, 0), 0.0f, 1.0f);
		
		
		allEntities.add(skyEntity);
		
		//allEntities.add(ent);
		allEntities.add(surface);
		allEntities.add(sun);
		
		allGUIs.add(crosshair);
		
		allEntities.add(p1);
		allEntities.add(p8);
		
		allEntities.add(p2);
		allEntities.add(p3);
		allEntities.add(p4);
		allEntities.add(p5);
		allEntities.add(p6);
		allEntities.add(p7);
		
		allEntities.add(brick);
		damnList.add(ent);
		
		
		//renderer.initRender(ent, cam, window, light);
		
		renderer.init();
		mRend.init();
		gRend.init();

	}


	@Override
	public void input() {
		camInc.set(0, 0, 0);
		if(window.isKeyHit(GLFW.GLFW_KEY_W))
			camInc.z = -1;
		if(window.isKeyHit(GLFW.GLFW_KEY_S))
			camInc.z = 1;
		
		if(window.isKeyHit(GLFW.GLFW_KEY_A))
			camInc.x = -1;
		if(window.isKeyHit(GLFW.GLFW_KEY_D))
			camInc.x = 1;
		
		if(window.isKeyHit(GLFW.GLFW_KEY_SPACE))
			camInc.y = 1;
		if(window.isKeyHit(GLFW.GLFW_KEY_LEFT_SHIFT))
			camInc.y = -1;
		
		if(window.isKeyHit(GLFW.GLFW_KEY_SLASH))
			if (keyCD == 0) {
				if(consoleOpened) {
					consoleOpened = false;
					keyCD = 100;
				} else {
					consoleOpened = true;
					keyCD = 100;
				}
			}
			
		
	}

	int keyCD;
	boolean consoleOpened = false;
	
	float m = 0.01f;
	int dir = 1;
	int dir2 = 1;
	int dir3 = 1;
	Vector3f fog = new Vector3f(0.1f,0.1f,0.1f);
	@Override
	public void update(float interval, MouseInput mouseInput) {
		
		if (keyCD > 0)
			keyCD -= 1;
		//damnList.get(0).setPos(cam.getPos().x, cam.getPos().y, cam.getPos().z - 3.0f);
		
		cam.movePos(camInc.x * Constants.CAM_STEP, camInc.y * Constants.CAM_STEP, camInc.z * Constants.CAM_STEP);
		
		crosshair.incRotation(0, 0, 1.0f);
		
		allEntities.get(0).setPos(cam.getPos().x, cam.getPos().y, cam.getPos().z);
		damnList.get(0).incRotation(0.1f, 0.1f, 0.1f);
		//damnList.get(0).incScale(m * dir);
		
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
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		renderer.renderList(allEntities, cam);
		renderer.shader.bind();
		renderer.loadLight(light);
		renderer.shader.unbind();
		mRend.renderList(damnList, cam);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		gRend.shader.bind();
		gRend.render(crosshair, cam);
		if (consoleOpened) {
			gRend.render(consoleBackground, cam);
		}
		gRend.shader.unbind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		


	}
	

	@Override
	public void cleanup() {
		renderer.cleanup();
		mRend.cleanup();
		gRend.cleanup();
		loader.cleanup();
		window.cleanup();
	}
}
