package render;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import core.Camera;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.utils.Transformation;
import core.utils.Utils;

public class EntityRenderer implements IRenderer {
	
	public ShaderManager shader;
	
	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/entity.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/entity.fsh"));
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
		shader.bind();
		shader.setUniform("ambientLight", fog);
		shader.setUniform("lightAffected", true);
		shader.setUniform("shineDamper", -1.0f);
		shader.unbind();
	}
	
	public void loadLight(Light light) {
		shader.setUniform("lightPosition", light.getPos());
		shader.setUniform("lightColour", light.getColor());
	}
	
	Vector3f fog = new Vector3f(0.34f, 0.3f, 0.5f);
	
	public void render(Entity ent, Camera cam) {
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));
		shader.setUniform("projMatrix", WindowManager.PROJ_MAT);
		shader.setUniform("viewMatrix", Transformation.getViewMatrix(cam));

		
		IRenderer.renderOperations(ent);
	
	}
	


	public void cleanup() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.cleanup();
	}
	

	public void renderList(ArrayList<Entity> entList, Camera cam) {
		shader.bind();
		for (Entity entity : entList) {
			render(entity, cam);
		}
		shader.unbind();
	}
	
}
