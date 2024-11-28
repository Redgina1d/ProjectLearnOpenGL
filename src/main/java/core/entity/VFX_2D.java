package core.entity;

import org.joml.Vector3f;

public class VFX_2D {
	
	private Vector3f pos;
	private Vector3f rot;
	private float scale;
	private Texture tex;
	
	public VFX_2D(Texture tex, Vector3f pos, Vector3f rot, float scale) {
		this.tex = tex;
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
	
	public void addPos(Vector3f pos) {
		this.pos.x += pos.x;
		this.pos.y += pos.y;
		this.pos.z += pos.z;
	}

	public Vector3f getRot() {
		return rot;
	}

	public void setRot(Vector3f rot) {
		this.rot = rot;
	}
	
	public void addRot(Vector3f rot) {
		this.rot.x += rot.x;
		this.rot.y += rot.y;
		this.rot.z += rot.z;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void addScale(float scale) {
		this.scale += scale;
	}

	public Texture getTex() {
		return tex;
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}
	
	

}
