package render;

import java.util.ArrayList;

import core.Camera;
import core.ShaderManager;
import core.entity.Entity;
import core.entity.Texture;
import core.utils.Constants;
import core.utils.Transformation;
import core.utils.Utils;

public class GUI2DRenderer extends Renderer {


	@Override
	public void init() throws Exception {
		super.init();
		shader.createVertShader(Utils.loadResource("/shaders/gui2d_render_vert.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/gui2d_render_frag.fsh"));
		shader.link();
		shader.createUniform("textureSampler");
		shader.createUniform("transformationMatrix");

	}
	
	@Override
	public void renderEntity(Entity ent, Camera cam) {
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));

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
