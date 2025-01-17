package core;

import org.joml.Vector3f;

@Deprecated
public abstract class Physics {

	private float g = -9.81f;
	private float airResistance = 0.1f;

	public float getNormalForce(float mass, float g, Vector3f suppNormal) {
		float root = (float) Math.sqrt((double) ((suppNormal.x * suppNormal.x) + (suppNormal.y * suppNormal.y) + (suppNormal.z * suppNormal.z)));
		float angle = (float) Math.acos((double) (suppNormal.z / root));
		return mass * g * angle;
	}

	public float getFrictionForce(float u, float normalForce) {
		return u * normalForce;
	}
}
