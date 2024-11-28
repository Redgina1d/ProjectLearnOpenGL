package encryptor;

import java.io.File;
import java.util.List;

import core.entity.Model;
import core.utils.Constants;
import core.utils.Utils;

public class Encryptor {
	
	public static final String OUTPUT_DIR = Constants.DIR + "/src/main/resources/entites";
	
	public void encrypt(String folderName) {
		List<String> finalFile;
		
		String[] albedoTrsp;
		String[] normals;
		String[] metalic_Smooth_Height;
		String[] emission;
		String[] additional_1;
		String[] additional_2;
		String[] additional_3;
		
		List<String> rawestModel;
		List<String> rawestBody;
		List<String> additional_model;
		
		String[] containmentData = new String[10];
		
		rawestModel = Utils.readAllLines(Constants.DIR + "/src/main/encryptor/processingDomain/" + folderName + "/" + folderName + "_model.obj");
		rawestBody = Utils.readAllLines(Constants.DIR + "/src/main/encryptor/processingDomain/" + folderName + "/" + folderName + "_body.obj");
		additional_model = Utils.readAllLines(Constants.DIR + "/src/main/encryptor/processingDomain/" + folderName + "/" + folderName + "_addit.obj");
		
		List<String> modelStruct;
		
		
		
	}
	
	
	
}
