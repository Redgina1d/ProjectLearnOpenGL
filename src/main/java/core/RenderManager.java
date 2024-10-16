package core;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.entity.Entity;
import core.entity.Light;
import core.utils.Constants;
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
		//shader.createVertShader(Utils.loadResource("/shaders/color_render.vs"));
		//shader.createFragShader(Utils.loadResource("/shaders/color_render.fs"));
		shader.link();
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");
		shader.createUniform("projMatrix");
		shader.createUniform("viewMatrix");
		shader.createUniform("ambientLight");
		shader.createMaterialUniform("material");
		shader.createUniform("lightPosition");
		shader.createUniform("lightColour");
	}
	
	public void loadLight(Light light) {
		shader.setUniform("lightPosition", light.getPos());
		shader.setUniform("lightColour", light.getColor());
	}
	
	public void render(Entity entity, Camera cam) {
		clear();
		shader.bind();
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
		shader.setUniform("projMatrix", window.updateProjMatrix());
		shader.setUniform("viewMatrix", Transformation.getViewMatrix(cam));
		shader.setUniform("material", entity.getModel().getMaterial());
		shader.setUniform("ambientLight", Constants.AMB_LIGHT);
		
		GL30.glBindVertexArray(entity.getModel().getId());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		// Setup tex filters
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        
        // Setup "wrap" parameters, to make texture render correctly on repeating objects (idk what they did mean by this)
        //GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        //GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
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
