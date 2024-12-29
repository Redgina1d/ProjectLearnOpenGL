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

public class MagicRenderer implements IRenderer {

	private ShaderManager shader;
	
	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/magic.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/magic.fsh"));
		shader.link();
		shader.createUniform("transformationMatrix");
		shader.createUniform("projMatrix");
		shader.createUniform("viewMatrix");
		shader.createUniform("ambientLight");
		shader.bind();
		shader.setUniform("projMatrix", WindowManager.PROJ_MAT);
		shader.setUniform("ambientLight", fog);
		shader.unbind();
	}
	

	Vector3f fog = new Vector3f(0.34f, 0.3f, 0.5f);
	
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
	
	public ShaderManager getShader() {
		return shader;
	}
}
