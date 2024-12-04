package render;

import java.util.ArrayList;
import java.util.List;

import core.Camera;
import core.WindowManager;
import core.entity.Entity;
import test.Launcher;

public interface RendererLogic {

	void init() throws Exception;
	
	void render(Entity ent, Camera cam);
	
	void renderList(ArrayList<Entity> entList, Camera cam);
	
	void cleanup();
	
}
