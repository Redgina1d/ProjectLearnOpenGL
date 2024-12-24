package core.entity;

public class Material {

	private int albedoMap;
	private int normalMap;
	private int heightMap;
	private int metallicMap;
	private int roughnessMap;
	
	
	public Material(int albedoMap, int heightMap, int normalMap, int metallicMap, int roughnessMap) {
		this.albedoMap = albedoMap;
		this.normalMap = normalMap;
		this.heightMap = heightMap;
		this.metallicMap = metallicMap;
		this.roughnessMap = roughnessMap;
	}
	public Material(int albedoMap) {
		this.albedoMap = albedoMap;
	}


	public int getAlbedoMap() {
		return albedoMap;
	}


	public void setAlbedoMap(int albedoMap) {
		this.albedoMap = albedoMap;
	}


	public int getNormalMap() {
		return normalMap;
	}


	public void setNormalMap(int normalMap) {
		this.normalMap = normalMap;
	}


	public int getHeightMap() {
		return heightMap;
	}


	public void setHeightMap(int heightMap) {
		this.heightMap = heightMap;
	}


	public int getMetallicMap() {
		return metallicMap;
	}


	public void setMetallicMap(int metallicMap) {
		this.metallicMap = metallicMap;
	}


	public int getRoughnessMap() {
		return roughnessMap;
	}


	public void setRoughnessMap(int roughnessMap) {
		this.roughnessMap = roughnessMap;
	}

	

}
