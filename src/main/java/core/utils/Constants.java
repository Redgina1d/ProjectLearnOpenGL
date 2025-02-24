package core.utils;


import java.nio.file.Paths;

import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class Constants {
	
	public static final float FOV = (float) Math.toRadians(60);
	public static final float Z_NEAR = 0.01f;
	public static final float Z_FAR = 1000f;
	
	public static final String TITLE = "Window to hell";
	
	public static final String DIR = Paths.get("").toAbsolutePath().toString();
	
	public static final float MOUSE_SENSITIVITY = 0.5f;
	public static final float CAM_STEP = 0.05f;
    
	public static final Vector3f FOG = new Vector3f(0.34f, 0.3f, 0.5f);
	
	public static final Vector4f DEF_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	
	public static Vector3f ambLight = new Vector3f(0.34f, 0.3f, 0.5f);
	public static Vector3f skyColour = new Vector3f(0.34f, 0.3f, 0.5f);

}
