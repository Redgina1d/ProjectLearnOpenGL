package core.entity;

import org.joml.Vector3f;

public class Light {
	
	private Vector3f pos;
	private Vector3f color;
	
	public Light(Vector3f pos, Vector3f color) {
		this.color = color;
		this.pos = pos;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	

}
