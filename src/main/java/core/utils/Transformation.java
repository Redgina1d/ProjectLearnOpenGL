package core.utils;

import org.joml.Matrix4f;

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


}
