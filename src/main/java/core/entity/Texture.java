package core.entity;

import java.util.ArrayList;

// can be common image or GIF
public class Texture {

	private ArrayList<Integer> ids = new ArrayList<Integer>();
	
	private float shineDamper = 1.0f;
	private float reflectivity = 1.0f;
	
	public Texture(int[] data) {
		for (int i = 0; i < data.length; i++) {
			ids.add(data[i]);
		}
	}
	
	public Texture(int id) {
		ids.add(id);
	}

	public ArrayList<Integer> getIds() {
		return ids;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
}
