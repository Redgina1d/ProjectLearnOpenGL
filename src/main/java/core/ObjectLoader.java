package core;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import java.util.List;

import org.lwjgl.opengl.GL11;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import core.entity.Model;
import core.utils.Utils;

public class ObjectLoader {

	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();
	private List<Integer> texs = new ArrayList<>();
	
	public Model loadModel(float[] vertices, float[] textureCoords, int[] indices) {
		int id = createVAO();
		storeIndicesBuffer(indices);
		storeDataInAttrList(0, 3, vertices);
		storeDataInAttrList(1, 2, textureCoords);
		unbind();
		return new Model(id, indices.length);
	}
	
	public int loadTexture(String filename) throws Exception {
		int width, height;
		ByteBuffer buffer;
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer c = stack.mallocInt(1);
			
			buffer = STBImage.stbi_load(filename, w, h, c, 4);
			if(buffer == null)
				throw new Exception("Image File " + filename + " canst not beload'd. Cause: " + STBImage.stbi_failure_reason());
			
			width = w.get();
			height = h.get();
		}
		int id = GL11.glGenTextures();
		texs.add(id);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		STBImage.stbi_image_free(buffer);
		return id;
	}
	
	private int createVAO() {
		int id = GL30.glGenVertexArrays();
		vaos.add(id);
		GL30.glBindVertexArray(id);
		return id;
	}
	
	private void storeIndicesBuffer(int[] indices) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer buffer = Utils.storeDataInIntBuff(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private void storeDataInAttrList(int attrNo, int vertCount, float[] data) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = Utils.storeDataInFloatBuff(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrNo, vertCount, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glEnableVertexAttribArray(attrNo);
		
	}
	

	private void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	public void cleanup() {
		for(int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		for(int vbo : vbos)
			GL30.glDeleteBuffers(vbo);
		for(int tex : texs)
			GL30.glDeleteTextures(tex);
	}
	
}
