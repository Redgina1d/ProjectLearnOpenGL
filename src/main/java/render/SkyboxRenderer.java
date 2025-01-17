package render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import core.Camera;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.utils.Constants;
import core.utils.Transformation;
import core.utils.Utils;

public class SkyboxRenderer implements IRenderer {

	public ShaderManager shader;
	
	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/skybox.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/skybox.fsh"));
		shader.link();
		shader.createUniform("morningTex");
		shader.createUniform("dayTex");
		shader.createUniform("duskTex");
		shader.createUniform("nightTex");
		shader.createUniform("trsfMat");
		shader.createUniform("projMat");
		shader.createUniform("viewMat");
		shader.createUniform("ambLight");
		shader.createUniform("time");
		shader.bind();
		shader.setUniform("morningTex", 0);
		shader.setUniform("dayTex", 1);
		shader.setUniform("duskTex", 2);
		shader.setUniform("nightTex", 3);
		shader.setUniform("projMat", WindowManager.PROJ_MAT);
		shader.setUniform("ambLight", Constants.FOG);
		shader.unbind();
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
