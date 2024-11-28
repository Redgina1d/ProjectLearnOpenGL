package render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.Camera;
import core.ShaderManager;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.entity.Texture;
import test.Launcher;

public abstract class Renderer {
	
	protected final WindowManager window;
	protected ShaderManager shader;
	
	public Renderer() {
		window = Launcher.getWindow();
	}
	
	
	
	/*
	 * shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/" + vs + ".vs"));
		shader.createFragShader(Utils.loadResource("/shaders/" + fs + ".fs"));
	 */
	/*
	 * shader.link();
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");
		shader.createUniform("projMatrix");
		shader.createUniform("viewMatrix");
		shader.createUniform("ambientLight");
		//shader.createMaterialUniform("material");
		shader.createUniform("lightPosition");
		shader.createUniform("lightColour");
		//shader.createUniform("reflectivity");
		shader.createUniform("shineDamper");
		shader.createUniform("lightAffected");
	 */

	
	public void init() throws Exception {
		shader = new ShaderManager();
	}
	
	public void loadLight(Light light) {
		shader.setUniform("lightPosition", light.getPos());
		shader.setUniform("lightColour", light.getColor());
	}
	
	public void loadShineVars(float damper, float reflect) {
		shader.setUniform("shineDamper", damper);
		shader.setUniform("reflectivity", reflect);
	}
	

	
	protected void renderEntity(Entity ent, Camera cam) {
		Texture tex = ent.getModel().getTexture();

		GL30.glBindVertexArray(ent.getModel().getId());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_R, GL20.GL_REPEAT);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getIds().get(0));
		GL11.glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
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
