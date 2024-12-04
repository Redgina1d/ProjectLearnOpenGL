package render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.Camera;
import core.ShaderManager;
import core.entity.Entity;
import core.entity.Texture;
import core.utils.Transformation;
import core.utils.Utils;

public class GUI2DRenderer {

	public void init(ShaderManager shader) throws Exception {
		shader.createVertShader(Utils.loadResource("/shaders/gui2d_render_vert.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/gui2d_render_frag.fsh"));
		shader.link();
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");

	}
	
	public void render(Entity ent, Camera cam, ShaderManager shader) {
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));

		GL30.glBindVertexArray(ent.getModel().getId());
		
		GL20.glEnableVertexAttribArray(0);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture().getIds().get(0));
		GL11.glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		
		GL30.glBindVertexArray(0);

	}
	

	public void renderList(ArrayList<Entity> entList, Camera cam, ShaderManager shader) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		for (Entity entity : entList) {
			render(entity, cam, shader);
		}
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		
	}

	public void cleanup(ShaderManager shader) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.cleanup();
	}
}
