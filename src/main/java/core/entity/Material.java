package core.entity;

import org.joml.Vector4f;

import core.utils.Constants;

public class Material {
	
	private Vector4f ambCol, difCol, specCol;
	private float reflect;
	private Texture texture;
	private boolean lightAffected;
	
	public Material() {
		this.ambCol = Constants.DEF_COLOR;
		this.difCol = Constants.DEF_COLOR;
		this.specCol = Constants.DEF_COLOR;
		this.texture = null;
		this.reflect = 0;
		this.lightAffected = true;
	}
	
	public Material(Vector4f color, float reflect) {
		this(color,color,color, reflect, null);
	}
	
	public Material(Vector4f color, float reflect, Texture texture) {
		this(color,color,color, reflect, texture);
	}
	
	public Material(Texture texture) {
		this(Constants.DEF_COLOR,Constants.DEF_COLOR,Constants.DEF_COLOR, 0, texture);
	}

	public Material(Vector4f ambCol, Vector4f difCol, Vector4f specCol, float reflectance, Texture texture) {
		this.ambCol = ambCol;
		this.difCol = difCol;
		this.specCol = specCol;
		this.reflect = reflectance;
		this.texture = texture;
	}

	public Vector4f getAmbCol() {
		return ambCol;
	}

	public void setAmbCol(Vector4f ambCol) {
		this.ambCol = ambCol;
	}

	public Vector4f getDifCol() {
		return difCol;
	}

	public void setDifCol(Vector4f difCol) {
		this.difCol = difCol;
	}

	public Vector4f getSpecCol() {
		return specCol;
	}

	public void setSpecCol(Vector4f specCol) {
		this.specCol = specCol;
	}

	public float getReflect() {
		return reflect;
	}

	public void setReflect(float reflect) {
		this.reflect = reflect;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setLightAffected(boolean m) {
		this.lightAffected = m;
	}
	
	public boolean isLightAffected() {
		return lightAffected;
	}
	
	public boolean hasTexture() {
		return texture != null;
	}

}
