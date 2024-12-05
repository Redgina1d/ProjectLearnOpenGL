package core;

import java.util.Map;
import org.joml.Vector3f;

import core.entity.Entity;
import core.utils.Constants;

public class Area {

	// cuboid
	public Vector3f p1;
	public Vector3f p8;
	
	private Physics physics;
	private Ambient ambient;
	private Map<String, Integer> tags;
	
	
	public Area(Vector3f p1, Vector3f p8) {
		this.p1 = p1;
		this.p8 = p8;
	}
	public Area(Vector3f p1, Vector3f p8, Physics physics) {
		this.p1 = p1;
		this.p8 = p8;
		this.physics = physics;
	}
	public Area(Vector3f p1, Vector3f p8, Map<String, Integer> tags) {
		this.p1 = p1;
		this.p8 = p8;
		this.tags = tags;
	}
	public Area(Vector3f p1, Vector3f p8, Map<String, Integer> tags, Physics physics) {
		this.p1 = p1;
		this.p8 = p8;
		this.tags = tags;
		this.physics = physics;
	}
	
	public Vector3f getPointByNumber(int number) {
		Vector3f pP = new Vector3f();
		switch (number) {
		case 2:
			pP.x = p8.x;
			pP.y = p1.y;
			pP.z = p1.z;
			break;
		case 3:
			pP.x = p8.x;
			pP.y = p8.y;
			pP.z = p1.z;
			break;
		case 4:
			pP.x = p1.x;
			pP.y = p8.y;
			pP.z = p1.z;
			break;
		case 5:
			pP.x = p1.x;
			pP.y = p8.y;
			pP.z = p8.z;
			break;
		case 6:
			pP.x = p1.x;
			pP.y = p1.y;
			pP.z = p8.z;
			break;
		case 7:
			pP.x = p8.x;
			pP.y = p1.y;
			pP.z = p8.z;
			break;
		default:
			System.err.println("Invalid input in getPointByNumber.");
			break;
		}
		return pP;
	}
	
	public boolean isInArea(Vector3f entPos) {
		boolean x, y, z;
		if ((this.p1.x < entPos.x && this.p8.x > entPos.x) || (this.p1.x > entPos.x && this.p8.x < entPos.x)) {
			x = true;
		} else {
			x = false;
		}
		if ((this.p1.y < entPos.y && this.p8.y > entPos.y) || (this.p1.y > entPos.y && this.p8.y < entPos.y)) {
			y = true;
		} else {
			y = false;
		}
		if ((this.p1.z < entPos.z && this.p8.z > entPos.z) || (this.p1.z > entPos.z && this.p8.z < entPos.z)) {
			z = true;
		} else {
			z = false;
		}
		
		if (x && y && z) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
