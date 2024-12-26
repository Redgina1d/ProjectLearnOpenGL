package render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import core.Camera;
import core.entity.Entity;
import core.entity.GUI2D;
import core.utils.Transformation;
import core.utils.Utils;

public class SSRenderer {
	
	public ShaderManager shader;

	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/gui2d.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/gui2d.fsh"));
		shader.link();
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");
	}
	
	public void render(GUI2D ent, Camera cam) {
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createSSTransformMatrix(ent));

		Rendertype.renderOperations(ent);

	}
	

	public void renderList(ArrayList<GUI2D> entList, Camera cam) {
		shader.bind();
		for (GUI2D entity : entList) {
			render(entity, cam);
		}
		shader.unbind();
	}

	public void cleanup() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.cleanup();
	}
}
