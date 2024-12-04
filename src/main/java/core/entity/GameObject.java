package core.entity;

import org.joml.Vector3f;

public class GameObject {
	
	private Entity entity;
	private Entity collider;
	
	private Vector3f speed;
	private Vector3f acceleration;
	
	private float mass;
	private float frictionCoefficent;

	public GameObject(Entity entity) {
		this.entity = entity;
		this.speed = new Vector3f(0,0,0);
		this.acceleration = new Vector3f(0,0,0);
	}
	
}
