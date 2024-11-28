package render;

import java.util.ArrayList;

import core.Camera;
import core.entity.Entity;
import core.entity.Texture;
import core.utils.Constants;
import core.utils.Transformation;
import core.utils.Utils;

public class EntityRenderer extends Renderer {

	@Override
	public void init() throws Exception {
		super.init();
		shader.createVertShader(Utils.loadResource("/shaders/entity_render_vert.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/entity_render_frag.fsh"));
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
	}
	
	@Override
	protected void renderEntity(Entity ent, Camera cam) {
		Texture tex = ent.getModel().getTexture();
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));
		shader.setUniform("projMatrix", window.updateProjMatrix());
		shader.setUniform("viewMatrix", Transformation.getViewMatrix(cam));
		shader.setUniform("ambientLight", Constants.AMB_LIGHT);
		shader.setUniform("lightAffected", ent.getModel().getMaterial().isLightAffected());
		shader.setUniform("shineDamper", tex.getShineDamper());
		super.renderEntity(ent, cam);
	}
	
	public void renderEntity(ArrayList<Entity> entList, Camera cam) {
		clear();
		shader.bind();
		for (Entity entity : entList) {
			renderEntity(entity, cam);
		}
	}
}
