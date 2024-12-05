package core;

import org.joml.Vector3f;

import core.entity.Entity;
import core.entity.Model;

public class Ambient {
	
	private Vector3f light;
	private Vector3f fog;
	private Vector3f skyLight;
	
	private Model skybox;
	
	
	public Ambient(Vector3f light, Vector3f fog, Vector3f skyLight, Model skybox) {
		this.light = light;
		this.fog = fog;
		this.skyLight = skyLight;
		this.skybox = skybox;
	}
	
	public Ambient(Vector3f light, Vector3f fog, Vector3f skyLight) {
		this.light = light;
		this.fog = fog;
		this.skyLight = skyLight;
	}
	
	public Ambient(Vector3f light, Vector3f fog) {
		this.light = light;
		this.fog = fog;
	}

}
