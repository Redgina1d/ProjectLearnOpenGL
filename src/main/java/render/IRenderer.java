package render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import core.Camera;
import core.entity.Entity;

public interface IRenderer {
	
	public void init() throws Exception;
	
	public void render(Entity ent, Camera cam);
	
	public void renderList(ArrayList<Entity> entList, Camera cam);
	
	public void cleanup();
	
	/*
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
	*/
	
	public static void renderOperations(Entity ent) {
		GL30.glBindVertexArray(ent.getModel().getId());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		// first texture is always an albedo map
		if (ent.getModel().getTextures().length > 0) {
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			texParam3D();
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(0));
			
			if (ent.getModel().getTextures().length > 1) {
				
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				texParam3D();
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(1));
		        
				if (ent.getModel().getTextures().length > 2) {
					
					GL13.glActiveTexture(GL13.GL_TEXTURE2);
					texParam3D();
			        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(2));
			        
					if (ent.getModel().getTextures().length > 3) {
						
						GL13.glActiveTexture(GL13.GL_TEXTURE3);
						texParam3D();
				        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(3));
				        
						if (ent.getModel().getTextures().length > 4) {
							
							GL13.glActiveTexture(GL13.GL_TEXTURE4);
							texParam3D();
					        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(4));
					        
					        if (ent.getModel().getTextures().length > 5) {
								
								GL13.glActiveTexture(GL13.GL_TEXTURE5);
								texParam3D();
						        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(5));
						        
						        if (ent.getModel().getTextures().length > 6) {
									
									GL13.glActiveTexture(GL13.GL_TEXTURE6);
									texParam3D();
							        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(6));
							        
							        if (ent.getModel().getTextures().length > 7) {
										
										GL13.glActiveTexture(GL13.GL_TEXTURE7);
										texParam3D();
								        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(7));
								        
								        if (ent.getModel().getTextures().length > 8) {
											
											GL13.glActiveTexture(GL13.GL_TEXTURE8);
											texParam3D();
									        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(8));
									        
									        if (ent.getModel().getTextures().length > 9) {
												
												GL13.glActiveTexture(GL13.GL_TEXTURE9);
												texParam3D();
										        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(9));
										        
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}
	
	public static void renderLines(Entity ent) {
		GL30.glBindVertexArray(ent.getModel().getId());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		// first texture is always an albedo map
		if (ent.getModel().getTextures().length > 0) {
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			texParam3D();
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(0));
			
			if (ent.getModel().getTextures().length > 1) {
				
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				texParam3D();
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(1));
		        
				if (ent.getModel().getTextures().length > 2) {
					
					GL13.glActiveTexture(GL13.GL_TEXTURE2);
					texParam3D();
			        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(2));
			        
					if (ent.getModel().getTextures().length > 3) {
						
						GL13.glActiveTexture(GL13.GL_TEXTURE3);
						texParam3D();
				        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(3));
				        
						if (ent.getModel().getTextures().length > 4) {
							
							GL13.glActiveTexture(GL13.GL_TEXTURE4);
							texParam3D();
					        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(4));
					        
					        if (ent.getModel().getTextures().length > 5) {
								
								GL13.glActiveTexture(GL13.GL_TEXTURE5);
								texParam3D();
						        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(5));
						        
						        if (ent.getModel().getTextures().length > 6) {
									
									GL13.glActiveTexture(GL13.GL_TEXTURE6);
									texParam3D();
							        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(6));
							        
							        if (ent.getModel().getTextures().length > 7) {
										
										GL13.glActiveTexture(GL13.GL_TEXTURE7);
										texParam3D();
								        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(7));
								        
								        if (ent.getModel().getTextures().length > 8) {
											
											GL13.glActiveTexture(GL13.GL_TEXTURE8);
											texParam3D();
									        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(8));
									        
									        if (ent.getModel().getTextures().length > 9) {
												
												GL13.glActiveTexture(GL13.GL_TEXTURE9);
												texParam3D();
										        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ent.getModel().getTexture(9));
										        
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}
	
	private static void texParam3D() {
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_R, GL20.GL_REPEAT);
	}
	
}
