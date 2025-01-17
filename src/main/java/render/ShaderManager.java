package render;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public class ShaderManager {
	
	public final int progID;
	public int vertShaderID, fragShaderID;
	
	private	final Map<String, Integer> uniforms;

	public ShaderManager() throws Exception {
		progID = GL20.glCreateProgram();
		if(progID == 0)
			throw new Exception("Couldn't create shader.");
		
		uniforms = new HashMap<>();
	}
	
	
	
	
	public void createUniform(String unifName) throws Exception {
		int unifLoc = GL20.glGetUniformLocation(progID, unifName);
		if(unifLoc < 0)
			throw new Exception("Can't find unif " + unifName);
		uniforms.put(unifName, unifLoc);
	}
	

	// mat4f
	public void setUniform(String unifname, Matrix4f val) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			GL20.glUniformMatrix4fv(uniforms.get(unifname), false,
					val.get(stack.mallocFloat(16)));
		}
	}
	// vec4f
	public void setUniform(String unifname, Vector4f value) {
		GL20.glUniform4f(uniforms.get(unifname), value.x, value.y, value.z, value.w);
	}
	// vec3f
	public void setUniform(String unifname, Vector3f value) {
		GL20.glUniform3f(uniforms.get(unifname), value.x, value.y, value.z);
	}
	// int
	public void setUniform(String unifname, int val) {
		GL20.glUniform1i(uniforms.get(unifname), val);
	}
	// bool
	public void setUniform(String unifname, boolean value) {
		float res = 0;
		if(value)
			res = 1;
		GL20.glUniform1f(uniforms.get(unifname), res);
	}
	// float
	public void setUniform(String unifname, float val) {
		GL20.glUniform1f(uniforms.get(unifname), val);
		
	}
	// vec3f arr
	public void setUniform(String unifname, Vector3f[] vals) {
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vals.length * 3);
		for (int i = 0; i < vals.length; i++) {
			floatBuffer.put(new float[] {vals[i].x, vals[i].y, vals[i].z}).flip();
		}
		GL20.glUniform3fv(uniforms.get(unifname), floatBuffer);
		
	}
	
	public void createVertShader(String shaderCode) throws Exception {
		vertShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
	}
	public void createFragShader(String shaderCode) throws Exception {
		fragShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
	}
	
	public int createShader(String shaderCode, int shaderType) throws Exception {
		int shaderID = GL20.glCreateShader(shaderType);
		if(shaderID == 0)
			throw new Exception("Err create shader. Type: " + shaderType);
		
		GL20.glShaderSource(shaderID, shaderCode);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0)
			throw new Exception("Error comp shader code: TYPE: " + shaderType
			+ " Info " + GL20.glGetShaderInfoLog(shaderID, 1024));
			
		GL20.glAttachShader(progID, shaderID);
		
		return shaderID;
	}
	
	public void link() throws Exception {
		GL20.glLinkProgram(progID);
		if(GL20.glGetProgrami(progID, GL20.GL_LINK_STATUS) == 0)
			throw new Exception("Err linkin' shader code. "
					+ "Info " + GL20.glGetProgramInfoLog(progID, 1024));
		
		if(vertShaderID != 0)
			GL20.glDetachShader(progID, vertShaderID);
		if(fragShaderID != 0)
			GL20.glDetachShader(progID, fragShaderID);
		
		GL20.glValidateProgram(progID);
		if(GL20.glGetProgrami(progID, GL20.GL_VALIDATE_STATUS) == 0)
			throw new Exception("Err validate shader code: " + GL20.glGetProgramInfoLog(progID, 1024));
	}
	
	public void bind() {
		GL20.glUseProgram(progID);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void cleanup() {
		unbind();
		if(progID != 0)
			GL20.glDeleteProgram(progID);
	}
	
	public int getProgId() {
		return progID;
	}
}
