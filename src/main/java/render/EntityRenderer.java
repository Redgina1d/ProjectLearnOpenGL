package render;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.Area;
import core.Camera;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.entity.Texture;
import core.utils.Constants;
import core.utils.Transformation;
import core.utils.Utils;
import test.Launcher;

public class EntityRenderer {
	
	ShaderManager shader;
	
	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource(Constants.DIR + "/src/main/resources/shaders/entity.vsh"));
		shader.createFragShader(Utils.loadResource(Constants.DIR + "/src/main/resources/shaders/entity.fsh"));
		shader.link();
		
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");
		shader.createUniform("projMatrix");
		shader.createUniform("viewMatrix");
		shader.createUniform("ambientLight");
		shader.createUniform("lightPosition");
		shader.createUniform("lightColour");
		//shader.createUniform("reflectivity");
		shader.createUniform("shineDamper");
		shader.createUniform("lightAffected");
		shader.createUniform("skyColour");
		
	}
	
	public void loadLight(Light light) {
		shader.setUniform("lightPosition", light.getPos());
		shader.setUniform("lightColour", light.getColor());
	}
	
	public void loadShineVars(float damper, float reflect) {
		shader.setUniform("shineDamper", damper);
		//shader.setUniform("reflectivity", reflect);
	}
	
	public void loadAmbLight(Area area) {
		shader.setUniform("ambientLight", area.getAmbient().getLight());
	}
	
	public void loadTex() {
		shader.setUniform("textureSampler", 0);
	}
	
	Vector3f f = new Vector3f(0.34f, 0.3f, 0.5f);

	public void initRender(Entity ent, Camera cam, WindowManager window, Light light) {
		shader.setUniform("ambientLight", f);
		loadTex();
		loadShineVars(ent.getModel().getTexture().getShineDamper(), 0);
		loadLight(light);
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));
		shader.setUniform("projMatrix", window.updateProjMatrix());
		shader.setUniform("viewMatrix", Transformation.getViewMatrix(cam));
		
		shader.setUniform("lightAffected", ent.getModel().getMaterial().isLightAffected());
		shader.setUniform("skyColour", f);
		
		Rendertype.renderOperations3D(ent, 0);
	}
	
	public void render(Entity ent, Camera cam, WindowManager window) {
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));
		shader.setUniform("projMatrix", window.updateProjMatrix());
		shader.setUniform("viewMatrix", Transformation.getViewMatrix(cam));
		
		Rendertype.renderOperations3D(ent, 0);
	}
	


	public void cleanup() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.cleanup();
	}
	

	public void renderList(ArrayList<Entity> entList, Camera cam, WindowManager window) {
		for (Entity entity : entList) {
			render(entity, cam, window);
		}
	}
	
	public void initRenderList(ArrayList<Entity> entList, Camera cam, WindowManager window, Light light) {
		for (Entity entity : entList) {
			initRender(entity, cam, window, light);
		}
	}
	
}
