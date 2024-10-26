package core.entity;


public class Texture {
	
	private int id;
	
	private int[] ids;
	
	private float shineDamper = 1.0f;
	private float reflectivity = 1.0f;
	
	public Texture(int id) {
		this.id = id;
	}
	
	public Texture(int[] ids) {
		this.ids = ids;
	}

	public int getId() {
		return id;
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
