package test;

import java.io.File;

import core.EngineManager;
import core.WindowManager;
import core.Text.IngameChar;
import core.utils.Constants;
import core.utils.Utils;

public class Launcher {
	
	private static WindowManager window;
	private static TestGame game;
	private static EngineManager engine;
	
	public static void main(String[] args) {
		window = new WindowManager(Constants.TITLE, 1600, 900, true);
		game = new TestGame();
		engine = new EngineManager();
		try {
			engine.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static WindowManager getWindow() {
		return window;
	}
	
	public static TestGame getTestGame() {
		return game;
	}
}
