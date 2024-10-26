package test;


import java.util.LinkedList;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import core.Camera;
import core.ILogic;
import core.MouseInput;
import core.ObjectLoader;
import core.RenderManager;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.entity.Model;
import core.entity.Texture;
import core.utils.Constants;

public class TestGame implements ILogic {
	
	private float r = 0.0f;
	private float g = 0.3f;
	private float b = 0.3f;
	private float a = 0.0f;
	
	private float d1 = 0.003f;
	private float d2 = 0.003f;
	private float d3 = 0.003f;
	
	//private int dir = 1;
	
	private final RenderManager renderer;
	private final ObjectLoader loader;
	private final WindowManager window;
	
	private LinkedList<Entity> allEntities;
	private Camera cam;
	private Light light;
	
	Vector3f camInc;
	//Vector3f camRot;
	
	TestGame() {
		renderer = new RenderManager();
		window = Launcher.getWindow();
		loader = new ObjectLoader();
		cam = new Camera();
		camInc = new Vector3f(0, 0, 0);
		//camRot = new Vector3f(0, 0, 0);
		light = new Light(new Vector3f(0,0,0), new Vector3f(2.2f,2.2f,2.2f));
		allEntities = new LinkedList<Entity>();
		
	}
	


	@Override
	public void init() throws Exception {
		
		Random random = new Random();
		
		renderer.init();

		
		Model cubeModel = loader.loadOBJModel("tr_cube_2");
		cubeModel.setTexture(new Texture(loader.loadTexture(Constants.DIR + "/src/main/resources/textures/sky.png")));
		
		Entity ent = new Entity(cubeModel, new Vector3f(0, 0, 0), new Vector3f(0,0,0), 1);
		allEntities.add(ent);
		
		//gen params
		/*
		for (int i = 0; i < 300; i++) {
			float x = 0;
			float z = 0;
			if(i == 0) {
				x = 0;
				z = 0;
			} else {
				x = allEntities.get(i-1).getPos().x + 2;
				z = allEntities.get(i-1).getPos().z + 2;
			}
			


			Entity ent = new Entity(cubeModel, new Vector3f(x, 0, z), new Vector3f(0,0,0), 1);
			allEntities.add(ent);
			
		}
		*/	
	}
	
	/*
	 * Texture tex = allEntities.get(i).getModel().getTexture();
			tex.setShineDamper(20);
			tex.setReflectivity(1);
	 */

	@Override
	public void input() {
		camInc.set(0, 0, 0);
		if(window.isKeyPressed(GLFW.GLFW_KEY_W))
			camInc.z = -1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_S))
			camInc.z = 1;
		
		if(window.isKeyPressed(GLFW.GLFW_KEY_A))
			camInc.x = 1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_D))
			camInc.x = -1;
		
		if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
			camInc.y = 1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
			camInc.y = -1;
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		renderer.loadLight(light);
		
		
		cam.movePos(camInc.x * Constants.CAM_STEP, camInc.y * Constants.CAM_STEP, camInc.z * Constants.CAM_STEP);
		
		if(mouseInput.isRightButtonPress()) {
			Vector2f rotVec = mouseInput.getDisVec();
			cam.moveRotation(rotVec.x * Constants.MOUSE_SENSITIVITY, rotVec.y * Constants.MOUSE_SENSITIVITY, 0);
		}
		

		
		//entity.incRotation(0.0f, 0.1f, 0.1f);
		
		/*
		 * party mode
		Constants.AMB_LIGHT.x += d1;
		Constants.AMB_LIGHT.y += d2;
		Constants.AMB_LIGHT.z += d3;
		if(Constants.AMB_LIGHT.x >= 0.2f)
			Constants.AMB_LIGHT.y += d2;
		if(Constants.AMB_LIGHT.y >= 0.2f)
			Constants.AMB_LIGHT.z += d3;
        if(Constants.AMB_LIGHT.x >= 1.0f || Constants.AMB_LIGHT.x <= 0.0f)
            d1 = -d1;
        if(Constants.AMB_LIGHT.y >= 1.0f || Constants.AMB_LIGHT.y <= 0.0f)
            d2 = -d2;
        if(Constants.AMB_LIGHT.z >= 1.0f || Constants.AMB_LIGHT.z <= 0.0f)
            d3 = -d3;
		
		r += d1;
		g += d2;
		b += d3;
		if(r >= 0.2f)
			g += d2;
		if(g >= 0.2f)
			b += d3;
        if(r >= 1.0f || r <= 0.0f)
            d1 = -d1;
        if(g >= 1.0f || g <= 0.0f)
            d2 = -d2;
        if(b >= 1.0f || b <= 0.0f)
            d3 = -d3;
        */

        /*
		if(entity.getPos().x < -1.5f)
			dir = -dir;
		if(entity.getPos().x > 1.5f) {
			entity.getPos().x = 1.5f;
			dir = -dir;
		}
		entity.getPos().x -= 0.004f * dir;
		entity.getPos().y -= 0.004f * dir;
		*/

		
		/*
		Random random = new Random();
		Random random2 = new Random();
		Random random3 = new Random();
		
		r = (random.nextFloat() * 2.0f) - 1.0f;
		g = (random2.nextFloat() * 2.0f) - 1.0f;
		b = (random3.nextFloat() * 2.0f) - 1.0f;
		*/
		
		
		
		
	}

	@Override
	public void render() {
		if(window.isResize()) {
			GL11.glViewport(0, 0, 5, 5);
			window.setResize(true);
		}
		window.setClearColour(r, g, b, a);
		renderer.renderEntity(allEntities, cam);
		
		//renderer.render(entity2);
	}

	@Override
	public void cleanup() {
		renderer.clear();
		loader.cleanup();
		window.cleanup();
	}

}

/* cube
float[] vertices = new float[] {
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
};
float[] texCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.0f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
};
int[] indices = new int[]{
            0, 1, 3, 3, 1, 2,
            8, 10, 11, 9, 8, 11,
            12, 13, 7, 5, 12, 7,
            14, 15, 6, 4, 14, 6,
            16, 18, 19, 17, 16, 19,
            4, 6, 7, 5, 4, 7,
};

*/
