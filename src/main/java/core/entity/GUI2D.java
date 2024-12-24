package core.entity;

import org.joml.Vector2f;
import org.joml.Vector3f;

import render.ShaderManager;

public class GUI2D {
	
	private Model model;
	private Vector2f pos;
	private Vector2f rotation;
	private Vector2f scale;
	
	public GUI2D(Model model, Vector2f pos, Vector2f rotation, float scale) {
		this.model = model;
		this.pos = pos;
		this.rotation = rotation;
		this.scale.x = scale;
		this.scale.y = scale;
	}
	public GUI2D(Model model, Vector2f pos, Vector2f rotation, Vector2f scale) {
		this.model = model;
		this.pos = pos;
		this.rotation = rotation;
		this.scale.x = scale.x;
		this.scale.y = scale.y;
	}
	
	public void incPos(float x, float y) {
		this.pos.x += x;
		this.pos.y += y;
	}
	
	public void setPos(float x, float y) {
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void incRotation(float x, float y) {
		this.rotation.x += x;
		this.rotation.y += y;
	}
	public void incScale(float x, float y) {
		this.scale.x += x;
		this.scale.y += y;
	}
	public void setScale(float x, float y) {
		this.scale.x = x;
		this.scale.y = y;
	}
	
	public void setRotation(float x, float y) {
		this.rotation.x = x;
		this.rotation.y = y;
	}

	public Model getModel() {
		return model;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Vector2f getRotation() {
		return rotation;
	}

	public Vector2f getScale() {
		return scale;
	}

	
}
