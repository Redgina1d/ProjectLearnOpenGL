package core.entity;

import org.joml.Vector3f;

public class Entity {
	
	private Model model;
	private Vector3f pos;
	private Vector3f rotation;
	private Vector3f scale;
	
	public Entity(Model model, Vector3f pos, Vector3f rotation, float scale) {
		this.model = model;
		this.pos = pos;
		this.rotation = rotation;
		this.scale = new Vector3f(scale, scale, scale);
	}
	public Entity(Model model, Vector3f pos, Vector3f rotation, Vector3f scale) {
		this.model = model;
		this.pos = pos;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void incPos(float x, float y, float z) {
		this.pos.x += x;
		this.pos.y += y;
		this.pos.z += z;
	}
	
	public void setPos(float x, float y, float z) {
		this.pos.x = x;
		this.pos.y = y;
		this.pos.z = z;
	}
	
	public void incRotation(float x, float y, float z) {
		this.rotation.x += x;
		this.rotation.y += y;
		this.rotation.z += z;
	}
	
	public void incScale(float m) {
		this.scale.x += m;
		this.scale.y += m;
		this.scale.z += m;
	}
	
	public void setScale(float m) {
		this.scale.x = m;
		this.scale.y = m;
		this.scale.z = m;
	}
	
	public void incScale(Vector3f m) {
		this.scale.x += m.x;
		this.scale.y += m.y;
		this.scale.z += m.z;
	}
	
	public void setScale(Vector3f m) {
		this.scale = m;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}

	public Model getModel() {
		return model;
	}

	public Vector3f getPos() {
		return pos;
	}
	

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	
}
