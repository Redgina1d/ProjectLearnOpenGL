package test;



import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;


import core.ILogic;
import core.ObjectLoader;
import core.RenderManager;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Model;
import core.entity.Texture;


public class TestGame implements ILogic {
	
	private float r = 0.0f;
	private float g = 0.0f;
	private float b = 0.0f;
	private float a = 0.0f;
	
	private float d1 = 0.0003f;
	private float d2 = 0.0003f;
	private float d3 = 0.0003f;
	
	private int dir = 1;
	
	private final RenderManager renderer;
	private final ObjectLoader loader;
	private final WindowManager window;
	
	private Entity entity;
	
	
	
	TestGame() {
		renderer = new RenderManager();
		window = Launcher.getWindow();
		loader = new ObjectLoader();
	}
	


	@Override
	public void init() throws Exception {
		renderer.init();
		
		float[] vertices = {
				// left_top
				-0.5f, 0.5f, 0f,
				// left_bottom
				-0.5f, -0.5f, 0f,
				// right_bottom
				0.2f, -0.5f, 0f,
				// right_top
				0.2f, 0.5f, 0f,
		};
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};
		
		float[] texCoords =  {
				0, 0,
				0, 1,
				1, 1,
				1, 0
		};
		
		
		// C:/Users/VICTUS/eclipse-workspace/ProjectLearnOpenGL/src/main/resources
		Model model = loader.loadModel(vertices, texCoords, indices);
		model.setTexture(new Texture(loader.loadTexture("C:/Users/VICTUS/eclipse-workspace/ProjectLearnOpenGL/src/main/resources/textures/the_kot.gif")));
		entity = new Entity(model, new Vector3f(1, 0, 0), new Vector3f(0, 0, 0), 1);
	}

	@Override
	public void input() {
	}

	@Override
	public void update() {
		
		// Shaderpiece
		//float time = (float) GLFW.glfwGetTime();

	    // transfer value to uniform
	    //GL20.glUniform1f(RenderManager.timeLocation, time);
		
		//unoptimized piece
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

        
		if(entity.getPos().x < -1.5f)
			dir = -dir;
		if(entity.getPos().x > 1.5f) {
			entity.getPos().x = 0;
			dir = -dir;
		}
		entity.getPos().x -= 0.004f * dir;
		
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
			GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResize(true);
		}
		window.setClearColour(r, g, b, a);
		renderer.render(entity);
	}

	@Override
	public void cleanup() {
		renderer.clear();
		loader.cleanup();
		window.cleanup();
	}

}
