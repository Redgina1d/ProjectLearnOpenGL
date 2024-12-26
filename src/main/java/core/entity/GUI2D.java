package core.entity;

import org.joml.Vector2f;
import org.joml.Vector3f;

import render.ShaderManager;

public class GUI2D {
	
	private Model model;
	private Vector3f pos;
	private float rotation;
	private Vector2f scale;
	
	
	public GUI2D(Model model, Vector3f pos, float rotation, float scale) {
		this.model = model;
		this.pos = pos;
		this.rotation = rotation;
		this.scale = new Vector2f(scale, scale);
	}
	public GUI2D(Model model, Vector3f pos, float rotation, Vector2f scale) {
		this.model = model;
		this.pos = pos;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	
	public void incPos(float x, float y, float z) {
		this.pos.x += x;
		this.pos.y += y;
	}
	
	public void setPos(float x, float y, float z) {
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void incRotation(float z) {
		this.rotation += z;
	}
	
	public void setRotation(float z) {
		this.rotation = z;
	}
	
	public void incScale(float x, float y) {
		this.scale.x += x;
		this.scale.y += y;
	}
	public void setScale(float x, float y) {
		this.scale.x = x;
		this.scale.y = y;
	}
	
	
	public Model getModel() {
		return model;
	}

	public Vector3f getPos() {
		return pos;
	}

	public float getRotation() {
		return rotation;
	}

	public Vector2f getScale() {
		return scale;
	}

	
}
