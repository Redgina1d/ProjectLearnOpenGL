package test;



import org.lwjgl.opengl.GL11;


import core.ILogic;
import core.ObjectLoader;
import core.RenderManager;
import core.WindowManager;
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
	
	private final RenderManager renderer;
	private final ObjectLoader loader;
	private final WindowManager window;
	
	private Model model;
	
	
	
	TestGame() {
		renderer = new RenderManager();
		window = Launcher.getWindow();
		loader = new ObjectLoader();
	}
	


	@Override
	public void init() throws Exception {
		renderer.init();
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f,
				-0.5f, 0.5f, 0f,
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
		model = loader.loadModel(vertices, texCoords, indices);
		model.setTexture(new Texture(loader.loadTexture("C:/Users/VICTUS/eclipse-workspace/ProjectLearnOpenGL/src/main/resources/textures/touchgrass.png")));
	}

	@Override
	public void input() {
	}

	@Override
	public void update() {
		
		// Schaderpiece
		//float time = (float) GLFW.glfwGetTime();

	    // Передаём значение времени в униформу
	    //GL20.glUniform1f(RenderManager.timeLocation, time);
		
		//unoptimized dumbass piece
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
		renderer.render(model);
	}

	@Override
	public void cleanup() {
		renderer.clear();
		loader.cleanup();
		window.cleanup();
	}

}
