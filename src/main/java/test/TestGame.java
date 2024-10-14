package test;

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
import core.entity.Model;
import core.entity.Texture;
import core.utils.Constants;

public class TestGame implements ILogic {
	
	private float r = 0.0f;
	private float g = 0.0f;
	private float b = 0.0f;
	private float a = 0.0f;
	
	private float d1 = 0.0003f;
	private float d2 = 0.0003f;
	private float d3 = 0.0003f;
	
	//private int dir = 1;
	
	private final RenderManager renderer;
	private final ObjectLoader loader;
	private final WindowManager window;
	
	private Entity entity;
	private Camera cam;
	
	Vector3f camInc;
	//Vector3f camRot;
	
	TestGame() {
		renderer = new RenderManager();
		window = Launcher.getWindow();
		loader = new ObjectLoader();
		cam = new Camera();
		camInc = new Vector3f(0, 0, 0);
		//camRot = new Vector3f(0, 0, 0);
	}
	


	@Override
	public void init() throws Exception {
		renderer.init();

		Model model = loader.loadOBJModel("uv_sphere");
		
		model.setTexture(new Texture(loader.loadTexture("C:/Users/VICTUS/eclipse-workspace/ProjectLearnOpenGL/src/main/resources/textures/sky.png")), 1f);
		entity = new Entity(model, new Vector3f(1, 0, -1), new Vector3f(0, 0, 0), 1);
	}

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
		cam.movePos(camInc.x * Constants.CAM_STEP, camInc.y * Constants.CAM_STEP, camInc.z * Constants.CAM_STEP);
		
		if(mouseInput.isRightButtonPress()) {
			Vector2f rotVec = mouseInput.getDisVec();
			cam.moveRotation(rotVec.x * Constants.MOUSE_SENSITIVITY, rotVec.y * Constants.MOUSE_SENSITIVITY, 0);
		}
		
		entity.incRotation(0.0f, 0.05f, 0.0f);
		
		/*
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
		renderer.render(entity, cam);
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
