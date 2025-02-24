package core.entity;

public class ModelData {

	private float[] vertices;
	private float[] textureCoords;
	private float[] normals;
	private int[] indices;
	private float furthestPoint;


	public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
	}


	public float[] getVertices() {
		return vertices;
	}


	public float[] getTextureCoords() {
		return textureCoords;
	}


	public float[] getNormals() {
		return normals;
	}


	public int[] getIndices() {
		return indices;
	}


	public float getFurthestPoint() {
		return furthestPoint;
	}


}

