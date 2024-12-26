package core.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import core.Camera;
import core.entity.Entity;
import core.entity.GUI2D;

public class Transformation {
	/*
	 * * * * * * * * * * * * * * * * * * * * * *
	 * About 4f matrix
	 * [ m00 m01 m02 m03 ]
	 * [ m10 m11 m12 m13 ]
	 * [ m20 m21 m22 m23 ]
	 * [ m30 m31 m32 m33 ]
	 * m00, m11, m22 — used for XYZ scale. If all of them == 1, object will not be scaled.
	 * m03, m13, m23 — movement (translation) by XYZ.
	 * m30, m31, m32, m33 — used for homogeneous coordinates, and must be:
	 * 						m33 - usually equal 1, if perspective projection is ignored.
	 * 						m30, m31, m32 - affect perspective too, but, usually, they're == 0.
	 * m01, m02, m10, m12, m20, m21 — used for rotation.
	 * * * * * * * * * * * * * * * * * * * * *
	 * Multiplication of 4f matrix by homogeneous vector (x, y, z, w)
	 * 
	 * NewX = m00 * x + m01 * y + m02 * z + m03 * w
	 * NewY = m10 * x + m11 * y + m12 * z + m13 * w
	 * NewZ = m20 * x + m21 * y + m22 * z + m23 * w
	 * NewW = m30 * x + m31 * y + m32 * z + m33 * w
	 * 
	 * (Fourth line affects homogeneous coordinates and perspective)
	 * * * * * * * * * * * * * * * * * * * * *
	 * Scaling
	 * [ Sx 0  0  0 ]
	 * [ 0  Sy 0  0 ]
	 * [ 0  0  Sz 0 ]
	 * [ 0  0  0  1 ]
	 * * * * * * * * * * * * * * * * * * * * *
	 * Rotation
	 * [ cos(θ) -sin(θ) 0 0 ]
	 * [ sin(θ) cos(θ)  0 0 ]
	 * [ 0      0       1 0 ]
	 * [ 0      0       0 1 ]
	 * * * * * * * * * * * * * * * * * * * * *
	 * Projection matrix
	 * used for projection of 3d scene to 2d monitor
	 * [ f/aspectRatio 0     0                        0  ]
	 * [ 0             f     0                        0  ]
	 * [ 0             0     (far+near)/(near-far)   -1  ]
	 * [ 0             0     (2*far*near)/(near-far)  0  ]
	 * f - focal length coefficient
	 * near - Z_NEAR
	 * far - Z_FAR
	 * * * * * * * * * * * * * * * * * * * * *
	 */
	
	public static Matrix4f createTransformMatrix(Entity entity) {
		Matrix4f mtrx = new Matrix4f();
		mtrx.identity().translate(entity.getPos()).
			rotateX((float) Math.toRadians(entity.getRotation().x)).
			rotateY((float) Math.toRadians(entity.getRotation().y)).
			rotateZ((float) Math.toRadians(entity.getRotation().z)).
			scale(entity.getScale().x, entity.getScale().y, entity.getScale().z);
		return mtrx;
	}
	
	public static Matrix4f createSSTransformMatrix(GUI2D entity) {
		Matrix4f mtrx = new Matrix4f();
		mtrx.identity().translate(entity.getPos()).
			rotateZ((float) Math.toRadians(entity.getRotation())).
			scaleXY(entity.getScale().x * 0.5625f, entity.getScale().y);
		return mtrx;
	}
	
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
