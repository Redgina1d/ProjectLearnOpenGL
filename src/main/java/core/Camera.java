package core;

import org.joml.Vector3f;

public class Camera {
	
	private Vector3f pos, rotation;
	
	public Camera() {
		pos = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}

	public Camera(Vector3f pos, Vector3f rotation) {
		this.pos = pos;
		this.rotation = rotation;
	}

	// // // // //
	public void movePos(float x, float y, float z) {
		if (z != 0) {
			
			pos.z += (float) Math.sin(Math.toRadians(rotation.y + 90)) * 1.0f * z;
			pos.x += (float) Math.sin(Math.toRadians(rotation.y + 90)) * x;
		}
		
		if (x != 0) {
			
			pos.z += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * z;
			pos.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * x;
		}
		
		pos.y += y;
	}
	
	public void setPos(float x, float y, float z) {
		this.pos.x = x;
		this.pos.y = y;
		this.pos.z = z;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}
	
	public void moveRotation(float x, float y, float z) {
		this.rotation.x += x;
		this.rotation.y += y;
		this.rotation.z += z;
	}

	public Vector3f getPos() {
		return pos;
	}

	public Vector3f getRotation() {
		return rotation;
	}
}
