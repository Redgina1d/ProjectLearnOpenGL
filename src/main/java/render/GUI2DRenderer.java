package render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.Camera;
import core.entity.Entity;
import core.entity.Texture;
import core.utils.Transformation;
import core.utils.Utils;

public class GUI2DRenderer {
	
	private ShaderManager shader;

	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/gui2d.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/gui2d.fsh"));
		shader.link();
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");
	}
	
	public void render(Entity ent, Camera cam) {
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));

		Rendertype.renderOperations2D(ent, 0);

	}
	

	public void renderList(ArrayList<Entity> entList, Camera cam) {
		shader.bind();
		for (Entity entity : entList) {
			render(entity, cam);
		}
		shader.unbind();
	}

	public void cleanup() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.cleanup();
	}
}
