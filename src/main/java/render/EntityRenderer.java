package render;

import java.util.ArrayList;

import org.joml.Vector3f;
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
import core.utils.Constants;
import core.utils.Transformation;
import core.utils.Utils;
import test.Launcher;

public class EntityRenderer {
	
	
	
	public void init(ShaderManager shader) throws Exception {
		shader.createVertShader(Utils.loadResource("/shaders/vertex.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/fragment.fsh"));
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
		shader.createUniform("skyColour");
	}
	
	public void loadLight(Light light, ShaderManager shader) {
		shader.setUniform("lightPosition", light.getPos());
		shader.setUniform("lightColour", light.getColor());
	}
	
	public void loadShineVars(float damper, float reflect, ShaderManager shader) {
		shader.setUniform("shineDamper", damper);
		shader.setUniform("reflectivity", reflect);
	}
	
	Vector3f f = new Vector3f(0.34f, 0.3f, 0.5f);

	public void render(Entity ent, Camera cam, ShaderManager shader, WindowManager window) {
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));
		shader.setUniform("projMatrix", window.updateProjMatrix());
		shader.setUniform("viewMatrix", Transformation.getViewMatrix(cam));
		shader.setUniform("ambientLight", Constants.AMB_LIGHT);
		shader.setUniform("lightAffected", ent.getModel().getMaterial().isLightAffected());
		shader.setUniform("shineDamper", ent.getModel().getTexture().getShineDamper());
		shader.setUniform("skyColour", f);
		
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
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture().getIds().get(0));
		GL11.glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
		
	}
	

	public void cleanup(ShaderManager shader) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.cleanup();
	}
	

	public void renderList(ArrayList<Entity> entList, Camera cam, ShaderManager shader, WindowManager window) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		for (Entity entity : entList) {
			render(entity, cam, shader, window);
		}
	}
	
}
