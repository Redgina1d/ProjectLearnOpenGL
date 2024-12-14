package render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL31;
import org.lwjgl.system.MemoryStack;

import core.Camera;
import core.WindowManager;
import core.entity.Entity;
import core.entity.Light;
import core.utils.Constants;
import core.utils.Transformation;

public class UBO {
	
	private final int ID;
	private final int capacity;
	private final int bindPoint;
	
	private ByteBuffer contents;
	
	
	public UBO(int capacity, int bindPoint, String blockName, ShaderManager shader) {
		this.ID = GL31.glGenBuffers();
		this.capacity = capacity;
		this.bindPoint = bindPoint;
		GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
		GL31.glBufferData(GL31.GL_UNIFORM_BUFFER, capacity, GL31.GL_DYNAMIC_DRAW);
		GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
		GL31.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, bindPoint, ID);
		bindUnifBlock(shader.getProgId(), blockName);
	}
	
	/*
	 *  BYTE VALUES FOR DATA TYPES
	 * 	float = 4
	 * 	int = 4
	 * 	Vec2f = 8
	 * 	Vec3f = 12
	 * 	Vec4f = 16
	 * 	Mat4f = 64
	 */
	
	/*
	public static void updateUbo_3d(Entity ent, Camera cam, UBO ubo, WindowManager window, Light lux) {
		ubo.update(Transformation.createTransformMatrix(ent), 0);
		ubo.update(window.updateProjMatrix(), Float.BYTES * 16);
		ubo.update(Transformation.getViewMatrix(cam), 128);
		ubo.update(lux.getPos(), 192);
		ubo.update(ent.getModel().getMaterial().getTexture().getShineDamper(), 252);
		ubo.update(Constants.ambLight, 208);
		ubo.update(ent.getModel().getMaterial().isLightAffected(), 220);
		ubo.update(Constants.skyColour, 224);
		ubo.update(false, 236);
		ubo.update(lux.getPos(), 240);
		
		//  END
	}
	*/
	
	
	public void bindUnifBlock(int shader, String blockName) {
		GL31.glUniformBlockBinding(shader, GL31.glGetUniformBlockIndex(shader, blockName), bindPoint);
	}
	
	// float array
	public void update(float[] val, int offset) {
        GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
        GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, val);
        GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
    }

	
	// mat4f
    public void update(Matrix4f matrix, int offset) {
    	float[] rawMat = new float[16];
    	rawMat[0] = matrix.m00();
    	rawMat[1] = matrix.m01();
    	rawMat[2] = matrix.m02();
    	rawMat[3] = matrix.m03();
    	rawMat[4] = matrix.m10();
    	rawMat[5] = matrix.m11();
    	rawMat[6] = matrix.m12();
    	rawMat[7] = matrix.m13();
    	rawMat[8] = matrix.m20();
    	rawMat[9] = matrix.m21();
    	rawMat[10] = matrix.m22();
    	rawMat[11] = matrix.m23();
    	rawMat[12] = matrix.m30();
    	rawMat[13] = matrix.m31();
    	rawMat[14] = matrix.m32();
    	rawMat[15] = matrix.m33();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buf = stack.mallocFloat(16).put(rawMat).flip();
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
            GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, buf);
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    // single float
    public void update(float val, int offset) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buf = stack.mallocFloat(1).put(val).flip();
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
            GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, buf);
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    // vec4f
    public void update(Vector4f vec, int offset) {
    	float[] rawVec = new float[4];
    	rawVec[0] = vec.x;
    	rawVec[1] = vec.y;
    	rawVec[2] = vec.z;
    	rawVec[3] = vec.w;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buf = stack.mallocFloat(4).put(rawVec).flip();
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
            GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, buf);
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    // vec3f
    public void update(Vector3f vec, int offset) {
    	float[] rawVec = new float[3];
    	rawVec[0] = vec.x;
    	rawVec[1] = vec.y;
    	rawVec[2] = vec.z;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buf = stack.mallocFloat(3).put(rawVec).flip();
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
            GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, buf);
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    // vec2f
    public void update(Vector2f vec, int offset) {
    	float[] rawVec = new float[2];
    	rawVec[0] = vec.x;
    	rawVec[1] = vec.y;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buf = stack.mallocFloat(2).put(rawVec).flip();
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
            GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, buf);
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    // single int
    public void update(int val, int offset) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer buf = stack.mallocInt(1).put(val).flip();
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
            GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, buf);
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    // bool (float)
    public void update(boolean val, int offset) {
    	float b = 0.0f;
    	if (val) b = 1.0f;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buf = stack.mallocFloat(1).put(b).flip();
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ID);
            GL31.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, buf);
            GL31.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    

    public void cleanup() {
        GL31.glDeleteBuffers(ID);
    }
    
}
