package core.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;


import core.Camera;
import core.entity.Entity;

public class Transformation {
	
	public static Matrix4f createTransformationMatrix(Entity entity) {
		Matrix4f mtrx = new Matrix4f();
		mtrx.identity().translate(entity.getPos()).
			rotateX((float) Math.toRadians(Entity.getRotation().x)).
			rotateY((float) Math.toRadians(Entity.getRotation().y)).
			rotateZ((float) Math.toRadians(Entity.getRotation().z)).
			scale(entity.getScale());
		return mtrx;
	}
	
	// i didnt got this piece
	public static Matrix4f getViewMatrix(Camera cam) {
		Vector3f pos = cam.getPos();
		Vector3f rot = cam.getRotation();
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1, 0, 0))
				.rotate((float) Math.toRadians(rot.y), new Vector3f(0, 1, 0))
				.rotate((float) Math.toRadians(rot.z), new Vector3f(1, 0, 1));
		matrix.translate(-pos.x, -pos.y, -pos.z);
		return matrix;
	}

}
