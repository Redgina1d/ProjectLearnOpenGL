package core.entity;

public class Model {
	
	public int ID;
	private int vertexCount;
	private int texID;
	
	public Model(int ID, int vertexCount, int texID) {
		this.ID = ID;
		this.vertexCount = vertexCount;
		this.texID = texID;
	}
	
	public Model(Model model, int texID) {
		this.ID = model.getId();
		this.vertexCount = model.getVertexCount();
		this.texID = texID;
	}
	public Model(int id, int vertexCount) {
		this.ID = id;
		this.vertexCount = vertexCount;
	}
	

	public int getId() {
		return ID;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public int getTexture() {
		return texID;
	}

	public void setTexture(int texID) {
		this.texID = texID;
	}
	
}
