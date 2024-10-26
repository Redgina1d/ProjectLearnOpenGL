package core.entity;

import org.joml.Vector3f;

public class GUI {
	
	private Vector3f pos;
	private static Vector3f rot;
	private float scale;
	private Texture tex;
	
	public GUI(Texture tex, Vector3f pos, Vector3f rot, float scale) {
		this.tex = tex;
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
		
	}

}
