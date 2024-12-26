package render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.entity.Entity;
import core.entity.GUI2D;

public class Rendertype {

	private UBO ubo;

	private String vSh;
	private String gSh;
	private String fSh;
	
	private ShaderManager shader;
	
	private String dim;
	private int vaoAttr;
	
	public Rendertype(String vSh, String fSh) {
		this.vSh = vSh;
		this.fSh = fSh;
	}
	
	public Rendertype(String vSh, String gSh, String fSh) {
		this.vSh = vSh;
		this.gSh = gSh;
		this.fSh = fSh;
	}

	
	
	
	
	public static void renderOperations(Entity ent) {
		GL30.glBindVertexArray(ent.getModel().getId());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_R, GL20.GL_REPEAT);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture());
		GL11.glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}
	
	public static void renderOperations(GUI2D ent) {
		GL30.glBindVertexArray(ent.getModel().getId());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_R, GL20.GL_REPEAT);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture());
		GL11.glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}
	
	
	
	
	
	public String getvSh() {
		return vSh;
	}

	public void setvSh(String vSh) {
		this.vSh = vSh;
	}

	public String getgSh() {
		return gSh;
	}

	public void setgSh(String gSh) {
		this.gSh = gSh;
	}

	public String getfSh() {
		return fSh;
	}

	public void setfSh(String fSh) {
		this.fSh = fSh;
	}

	public String getDim() {
		return dim;
	}

	public void setDim(String dim) {
		this.dim = dim;
	}

	public int getVaoAttr() {
		return vaoAttr;
	}

	public void setVaoAttr(int vaoAttr) {
		this.vaoAttr = vaoAttr;
	}
}
