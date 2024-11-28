package render;

import core.utils.Utils;

public class TerrainRenderer extends Renderer {


	@Override
	public void init() throws Exception {
		super.init();
		shader.createVertShader(Utils.loadResource("/shaders/terrainVert.vsh"));
		shader.createFragShader(Utils.loadResource("/shaders/terrainFrag.fsh"));
		
		shader.createUniform("lightPosition");
		shader.createUniform("lightColour");
	}
}
