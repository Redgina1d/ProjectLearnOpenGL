package render;

import java.util.ArrayList;

import core.Camera;
import core.WindowManager;
import core.entity.Entity;
import core.utils.Constants;
import core.utils.Transformation;
import core.utils.Utils;

public class TerrainRenderer implements IRenderer {

	public  ShaderManager shader;
	
	@Override
	public void init() throws Exception {
		shader = new ShaderManager();
		shader.createVertShader(Utils.loadResource("/shaders/terrain.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/terrain.fsh"));
		shader.link();
		shader.createUniform("albedoMap_1");
		shader.createUniform("albedoMap_2");
		shader.createUniform("albedoMap_3");
		shader.createUniform("blendMap");
		//shader.createUniform("metalMap");
		//shader.createUniform("roughMap");
		//shader.createUniform("normalMap");
		shader.createUniform("trsfMat");
		shader.createUniform("projMat");
		shader.createUniform("viewMat");
		shader.createUniform("ambLight");
		shader.createUniform("lightPos");
		shader.createUniform("lightColor");
		shader.bind();
		shader.setUniform("albedoMap_1", 0);
		shader.setUniform("albedoMap_2", 1);
		shader.setUniform("albedoMap_3", 2);
		//shader.setUniform("blendMap", 3);
		//shader.setUniform("metalMap", 4);
		//shader.setUniform("roughMap", 5);
		//shader.setUniform("normalMap", 6);
		shader.setUniform("ambLight", Constants.FOG);
		shader.setUniform("projMatrix", WindowManager.PROJ_MAT);
		shader.unbind();
		
	}

	@Override
	public void render(Entity ent, Camera cam) {
		shader.setUniform("transformationMatrix", Transformation.createTransformMatrix(ent));
		shader.setUniform("viewMatrix", Transformation.getViewMatrix(cam));
		
		IRenderer.renderOperations(ent);
	}

	@Override
	public void renderList(ArrayList<Entity> entList, Camera cam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
