package render;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import core.Camera;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.utils.Constants;
import core.utils.Transformation;
import core.utils.Utils;

public class CoolRenderer implements IRenderer {
	
	public  ShaderManager shader;
	
	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/default.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/default.fsh"));
		shader.link();
		shader.createUniform("albedoMap");
		shader.createUniform("trsfMat");
		shader.createUniform("projMat");
		shader.createUniform("viewMat");
		shader.createUniform("ambLight");
		shader.createUniform("lightPosition");
		shader.createUniform("lightColour");
		//shader.createUniform("reflectivity");
		shader.createUniform("shineDamper");
		shader.createUniform("lightAffected");
		shader.bind();
		shader.setUniform("textureSampler", 0);
		shader.setUniform("projMatrix", WindowManager.PROJ_MAT);
		shader.setUniform("ambientLight", Constants.FOG);
		shader.setUniform("lightAffected", true);
		shader.setUniform("shineDamper", -1.0f);
		shader.unbind();
	}
	
	public void loadLight(Light light) {
		shader.setUniform("lightPosition", light.getPos());
		shader.setUniform("lightColour", light.getColor());
	}
	
	
	
	public void render(Entity ent, Camera cam) {	
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));
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