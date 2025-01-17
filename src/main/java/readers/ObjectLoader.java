package readers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import core.Vertex;
import core.entity.Model;
import core.entity.ModelData;
import core.utils.Constants;
import core.utils.Utils;

public class ObjectLoader {

	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();
	private List<Integer> texs = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public Model loadOBJModel (String modelF, String texF){
		Model mod = loadOBJModel(loadOBJModel(modelF));
		try {
			if (texF.toLowerCase().endsWith(".gif")) {
				System.err.println("cant load gifs yet");
				//mod.setTexture(new Texture(loadGIF(texF)));
			} else {
				mod.setTexture(loadImg(texF));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mod;
	}
	
	public Model loadOBJModel(ModelData data) {
		int id = createVAO();
		storeIndicesBuffer(data.getIndices());
		storeDataInAttrList(0, 3, data.getVertices());
		storeDataInAttrList(1, 2, data.getTextureCoords());
		storeDataInAttrList(2, 3, data.getNormals());
		unbind();
		return new Model(id, data.getIndices().length, (byte) 1);
	}

	public ModelData loadOBJModel(String fileName) {
		
		List<String> lines = Utils.readAllLines(fileName);
		
		List<Vertex> vertices = new ArrayList<Vertex>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<>();
		
		for(String line : lines) {
			String[] tokens = line.split("\\s+");
			switch (tokens[0]) {
			case "v":
				// VERTICES
				Vector3f vertxs = new Vector3f(
						Float.parseFloat(tokens[1]),
						Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3])
				);
				Vertex newVert = new Vertex(vertices.size(), vertxs);
				vertices.add(newVert);
				break;
			case "vt":
				// VERTEX TEXTURES
				Vector2f texs = new Vector2f(
						Float.parseFloat(tokens[1]),
						Float.parseFloat(tokens[2])
				);
				
				textures.add(texs);
				break;
			case "vn":
				// VERTEX NORMALS
				Vector3f normlsx = new Vector3f(
						Float.parseFloat(tokens[1]),
						Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3])
				);
				normals.add(normlsx);
				break;
			case "f":
				// FACES
				for(int i = 1; i < tokens.length; i++){
					String[] currentLine = line.split(" ");
					String[] vertex1 = currentLine[1].split("/");
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					processVertex(vertex1, vertices, indices);
					processVertex(vertex2, vertices, indices);
					processVertex(vertex3, vertices, indices);
				}
				break;
			default:
				break;
			}
		}
		removeUnusedVertices(vertices);
		float[] verticesArr = new float[vertices.size() * 3];
		float[] texCoordArr = new float[vertices.size() * 2];
		float[] normalArr = new float[vertices.size() * 3];
		float furthest = convertDataToArrays(vertices, textures, normals, verticesArr,
				texCoordArr, normalArr);
		int[] indicesArr = convertIndicesListToArray(indices);
		
		return new ModelData(verticesArr, texCoordArr, normalArr, indicesArr);
	}


	private static int[] convertIndicesListToArray(List<Integer> indices) {
		int[] indicesArray = new int[indices.size()];
		for (int i = 0; i < indicesArray.length; i++) {
			indicesArray[i] = indices.get(i);
		}
		return indicesArray;
	}

	
	private static float convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures,
			List<Vector3f> normals, float[] verticesArray, float[] texturesArray,
			float[] normalsArray) {
		float furthestPoint = 0;
		for (int i = 0; i < vertices.size(); i++) {
			Vertex currentVertex = vertices.get(i);
			if (currentVertex.getLength() > furthestPoint) {
				furthestPoint = currentVertex.getLength();
			}
			Vector3f position = currentVertex.getPosition();
			Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
			Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
			
			verticesArray[i * 3] = position.x;
			verticesArray[i * 3 + 1] = position.y;
			verticesArray[i * 3 + 2] = position.z;
			
			texturesArray[i * 2] = textureCoord.x;
			texturesArray[i * 2 + 1] = 1 - textureCoord.y;
			
			normalsArray[i * 3] = normalVector.x;
			normalsArray[i * 3 + 1] = normalVector.y;
			normalsArray[i * 3 + 2] = normalVector.z;
		}
		return furthestPoint;
	}

	
	
	private static void processVertex(String[] vertex, 
			List<Vertex> vertices, List<Integer> indices) {
		int index = Integer.parseInt(vertex[0]) - 1;
		Vertex currVert = vertices.get(index);
		int textureIndex = Integer.parseInt(vertex[1]) - 1;
		int normalIndex = Integer.parseInt(vertex[2]) - 1;
		if (!currVert.isSet()) {
			currVert.setTextureIndex(textureIndex);
			currVert.setNormalIndex(normalIndex);
			indices.add(index);
		} else {
			dealWithAlreadyProcessedVertex(currVert, textureIndex, normalIndex, indices, vertices);
		}

	}
	
	private static void removeUnusedVertices(List<Vertex> vertices){
		for(Vertex vertex:vertices){
			if(!vertex.isSet()){
				vertex.setTextureIndex(0);
				vertex.setNormalIndex(0);
			}
		}
	}

	
	private static void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
			int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
		if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
			indices.add(previousVertex.getIndex());
		} else {
			Vertex anotherVertex = previousVertex.getDuplicateVertex();
			if (anotherVertex != null) {
				dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
						indices, vertices);
			} else {
				Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
				duplicateVertex.setTextureIndex(newTextureIndex);
				duplicateVertex.setNormalIndex(newNormalIndex);
				previousVertex.setDuplicateVertex(duplicateVertex);
				vertices.add(duplicateVertex);
				indices.add(duplicateVertex.getIndex());
			}
		}
	}

	
	
	/*
	private static void processVertex(int pos, int texCoord, int normal, List<Vector2f> texCoordList,
									List<Vector3f> normalList, List<Integer> indicesList,
									float[] texCoordArr, float[] normalArr) {
		indicesList.add(pos);
		
		if(texCoord >= 0) {
			Vector2f texCoordVec = texCoordList.get(texCoord);
			texCoordArr[pos * 2] = texCoordVec.x;
			texCoordArr[pos * 2 + 1] = 1 - texCoordVec.y;
		}
		
		if(normal >= 0) {
			Vector3f normalVec = normalList.get(normal);
			normalArr[pos * 3] = normalVec.x;
			normalArr[pos * 3 + 1] = normalVec.y;
			normalArr[pos * 3 + 2] = normalVec.z;
		}
	}
	*/
			
	
	private static void processFace(String token, List<Vector3i> faces) {
		String[] lineToken = token.split("/");
		int length = lineToken.length;
		int pos = -1, coords = -1, normal = -1;
		pos = Integer.parseInt(lineToken[0]) - 1;
		if(length > 1) {
			String textCoord = lineToken[1];
			coords = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : -1;
			if(length > 2)
				normal = Integer.parseInt(lineToken[2]) - 1;
			
		}
		Vector3i facesVec = new Vector3i(pos, coords, normal);
		faces.add(facesVec);
	}
	
	
	
    private static ByteBuffer readFileToBuffer(String filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        ByteBuffer buffer = BufferUtils.createByteBuffer(fileBytes.length);
        buffer.put(fileBytes).flip();
        return buffer;
    }

	//
	public int loadImg(String way) throws Exception {
		way = Constants.DIR + "/src/main/resources/textures/" + way + ".png";
		int width, height;
		ByteBuffer buffer;
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer c = stack.mallocInt(1);
			buffer = STBImage.stbi_load(way, w, h, c, 4);
			if(buffer == null)
				throw new Exception("Image File " + way + " can't be loaded. Cause: " + STBImage.stbi_failure_reason());
			
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

	//mad shit
	public ArrayList<Integer> loadGIF(String way) throws Exception {
		int frameCount = 0;
		int width = 0, height = 0, channels;
		ByteBuffer buffer = null;
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer x = stack.mallocInt(1); // w
            IntBuffer y = stack.mallocInt(1); // h
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer d = stack.mallocInt(1); // dubious piece
            PointerBuffer delays = MemoryUtil.memAllocPointer(d.capacity());
            for (int i = 0; i < delays.capacity(); i++) {
                delays.put(i, delays.get(i));
            }
            IntBuffer channelsBuf = stack.mallocInt(1);
            ByteBuffer gifBuffer = readFileToBuffer(way);
            buffer = STBImage.stbi_load_gif_from_memory(gifBuffer, delays, x, y, comp, channelsBuf, 4);
			if(buffer == null)
				throw new Exception("GIF " + way + " can't be loaded. Cause: " + STBImage.stbi_failure_reason());
			width = x.get();
			height = y.get();
			channels = channelsBuf.get();
			frameCount = buffer.limit() / (width * height * channels);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < frameCount; i++) {
			ids.add(GL11.glGenTextures());
			texs.add(ids.get(i));
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ids.get(i));
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		}
		STBImage.stbi_image_free(buffer);
		return ids;
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
