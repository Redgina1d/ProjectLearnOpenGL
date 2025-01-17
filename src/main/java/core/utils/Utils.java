package core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.system.MemoryUtil;

public abstract class Utils {
	
	public static FloatBuffer storeDataInFloatBuff(float[] data) {
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static IntBuffer storeDataInIntBuff(int[] data) {
		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static String loadResource(String filename) throws Exception {
		String result;
		
		try(InputStream in = Utils.class.getResourceAsStream(filename);
			Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
			result = scanner.useDelimiter("\\A").next();
			
		}
		return result;
	}
	
	public static List<String> readAllLines(String fileName) {
		List<String> lis = new ArrayList<>();
		FileReader fr = null;
		try {
			fr = new FileReader(new File(Constants.DIR + "/src/main/resources/models/" + fileName + ".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("no file =(");
			e.printStackTrace();
		}
		try(BufferedReader br = new BufferedReader(fr)) {
			String line;
			while ((line = br.readLine()) != null) {
				lis.add(line);
				
			}
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return lis;
	}
}
