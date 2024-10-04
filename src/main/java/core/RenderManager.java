package core;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.entity.Entity;
import core.entity.Model;
import core.utils.Transformation;
import core.utils.Utils;
import test.Launcher;

public class RenderManager {
	
	private final WindowManager window;
	public ShaderManager shader;
	
	public RenderManager() {
		window = Launcher.getWindow();
	}
	
	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/vertex.vs"));
		shader.createFragShader(Utils.loadResource("/shaders/fragment.fs"));
		shader.link();
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");
	}
	
	public void render(Entity entity) {
		clear();
		shader.bind();
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
		GL30.glBindVertexArray(entity.getModel().getId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.unbind();
	}
	
	public void cleanup() {
		shader.cleanup();
	}
}
