package core.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import core.Camera;
import core.entity.Entity;

public abstract class Transformation {
	
	
	/*
	 * Matrix multiplication rule
	 * 
	 * You can only multiply two matrices if the number of
	 * columns on the left-hand side matrix is equal to the 
	 * number of rows on the right-hand side matrix.
	 * 
	 * [ 1 2 3 ]   [ 2 7 5 ]
	 * [ 5 8 7 ] * [ 4 5 6 ]
	 * [ 4 9 0 ]   [ 9 2 3 ]
	 * 
	 * We must multiply values of the ROW of first mat by the values
	 * of the COLUMN of second mat, and write their sum into the cell
	 * of resulting mat in which these row and column are crossing.
	 * 
	 *  [ 1*2+2*4+3*9=37  1*7+2*5+3*2=23 1*5+2*6+3*3=26 ]   [ 37  23 26 ]
	 *  [ 5*2+8*4+7*9=105 5*7+8*5+7*2=89 5*5+8*6+7*3=94 ] = [ 105 89 94 ]
	 *  [ 4*2+9*4+0*9=44  4*7+9*7+0*2=91 4*5+9*6+0*3=74 ]   [ 44  91 74 ]
	 * 
	 * The rule is same for matrices of different structures:
	 * 
	 * [ 5 8 3 ]   [ 7 4 ]   [ 5*7+8*0+3*1=38 5*4+8*9+3*3=101 ]
	 * [ 1 4 6 ] * [ 0 9 ] = [ 1*7+4*0+6*1=13  1*4+4*9+6*3=58 ]
	 * 			   [ 1 3 ]   
	 * 
	*/
	
	
	
	/*
	 * * * * * * * * * * * * * * * * * * * * * *
	 * About 4f matrix
	 * 
	 * [ m00 m01 m02 m03 ]
	 * [ m10 m11 m12 m13 ]
	 * [ m20 m21 m22 m23 ]
	 * [ m30 m31 m32 m33 ]
	 * 
	 * [ sX m01 m02 tX ]
	 * [ m10 sY m12 tY ]
	 * [ m20 m21 sZ tZ ]
	 * [ p0 p0 p0 p1 ]
	 * 
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
