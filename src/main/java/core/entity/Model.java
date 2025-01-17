package core.entity;

public class Model {
	
	public int ID;
	private int vertexCount;
	private int[] texID;
	
	public Model(int ID, int vertexCount, int texID, byte texQuantity) {
		this.texID = new int[texQuantity];
		this.ID = ID;
		this.vertexCount = vertexCount;
		this.texID[0] = texID;
	}
	
	public Model(Model model, int texID, byte texQuantity) {
		this.texID = new int[texQuantity];
		this.ID = model.getId();
		this.vertexCount = model.getVertexCount();
		this.texID[0] = texID;
	}
	public Model(int id, int vertexCount, byte texQuantity) {
		this.texID = new int[texQuantity];
		this.ID = id;
		this.vertexCount = vertexCount;
	}
	

	public int getId() {
		return ID;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	@Deprecated
	public int getTexture() {
		return texID[0];
	}
	
	public int getTexture(int index) {
		return texID[index];
	}
	
	public int[] getTextures() {
		return texID;
	}

	@Deprecated
	public void setTexture(int texID) {
		this.texID[0] = texID;
	}
	
	public void setTexture(int texID, int index) {
		this.texID[index] = texID;
	}
	
}
