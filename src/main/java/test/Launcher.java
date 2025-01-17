package test;

import core.EngineManager;
import core.WindowManager;
import core.utils.Constants;

public abstract class Launcher {
	
	private static WindowManager window;
	private static TestGame game;
	private static EngineManager engine;
	
	public static void main(String[] args) {
		
		/*
		 * eleven to decimal system
		 * 11/2 = 5 ->  1
		 * 5/2 = 2  ->  1
		 * 2/2 = 1  ->  0
		 * 1/2 = 0  ->  1
		 * reverse this ↑
		 * you'll get 1011
		 * write 0b before it
		 * byte b = (byte) 0b1011;
		*/
		
		
		
	
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
