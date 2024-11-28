package core;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import core.entity.Material;

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
	
	public void setUniform(String unifname, Matrix4f val) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			GL20.glUniformMatrix4fv(uniforms.get(unifname), false,
					val.get(stack.mallocFloat(16)));
		}
	}
	public void setUniform(String unifname, Vector4f value) {
		GL20.glUniform4f(uniforms.get(unifname), value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String unifname, Vector3f value) {
		GL20.glUniform3f(uniforms.get(unifname), value.x, value.y, value.z);
	}
	
	public void setUniform(String unifname, int val) {
		GL20.glUniform1i(uniforms.get(unifname), val);
	}
	
	public void setUniform(String unifname, boolean value) {
		float res = 0;
		if(value)
			res = 1;
		GL20.glUniform1f(uniforms.get(unifname), res);
	}
	
	public void setUniform(String unifname, float val) {
		if (uniforms.get(unifname) == null) {
			System.err.println("blah");
		}
		GL20.glUniform1f(uniforms.get(unifname), val);
		
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
}
