package core;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import core.entity.Entity;
import core.entity.Model;
import readers.ObjectLoader;

public class Text {
	
	private ArrayList<Entity> chars;
	private float scale;
	private int maxCharsInX, maxCharsInY;
	
	private Vector2f xyStart, xyEnd;
	private float zLoc;
	private float xSpacing;
	private float ySpacing;

	public static final float CHAR_RATIO = 0.6f;
	
	private ObjectLoader loader;
	
	
	public Text(CharSequence chars, float scale, Vector2f xyStart, Vector2f xyEnd, float zLoc, float xSpacing, float ySpacing) throws Exception {
		this.scale = scale;
		this.xyStart = xyStart;
		this.xyEnd = xyEnd;
		this.zLoc = zLoc;
		this.xSpacing = xSpacing;
		this.ySpacing = ySpacing;
		this.chars = new ArrayList<Entity>();
		if (xyStart.x > xyEnd.x || xyStart.y < xyEnd.y) {
			throw new Exception("Start point must be to the left and above of the end point.");
		}
		loader = new ObjectLoader();
		float chW = scale * CHAR_RATIO;
		float chH = scale;
		float spaceWidth = Math.abs(xyEnd.x - xyStart.x);
		float spaceHeight = Math.abs(xyEnd.y - xyStart.y);
		maxCharsInX = (int) Math.floor(spaceWidth / (chW + xSpacing));
		maxCharsInY = (int) Math.floor(spaceHeight / (chH + ySpacing));
		System.out.println("Max chars in X line: " + maxCharsInX + 
				" char width is: " + chW + " space width is: " + spaceWidth + " xSpacing is: " + xSpacing);
		System.out.println("Max chars in Y line: " + maxCharsInY + 
				" char height is: " + chH + " space height is: " + spaceHeight + " ySpacing is: " + ySpacing);
		if (maxCharsInX == 0 || maxCharsInY == 0) {
			throw new Exception("Type start and end points are too close - can't display any character.");
		}
		for (int i = 0; i < chars.length(); i++) {
			type(chars.charAt(i));
			System.out.println(this.chars.get(i).getPos());
		};
	}
	
	
	public Entity type(char c) throws Exception {
		String filename = null;
		for (IngameChar character : IngameChar.values()) {
            if (character.symbol == c) {
                filename = character.name();
            }
        }
		if (filename == null) {
			throw new Exception("Can't type character: " + c + ". Character is not supported.");
		}
		
		Vector2f pos = new Vector2f();
		
		if (chars.size() == 0) {
			pos.x = xyStart.x + ((scale * CHAR_RATIO) / 2);
			pos.y = xyStart.y - (scale / 2);
		} else {
			pos.x = (getLast().getPos().x + (scale * CHAR_RATIO) + xSpacing);
			if (pos.x > xyEnd.x) {
				pos.x = getFirst().getPos().x;
				pos.y = getLast().getPos().y - scale - ySpacing;
			} else {
				pos.y = getLast().getPos().y;
				if (pos.y < xyEnd.y) {
					throw new Exception("Character is out of Y bounds.");
				}
			}
		}

		Model model = loader.loadOBJModel("char", "characters/" + filename);
		
		Entity typed = new Entity(model, new Vector3f(pos.x, pos.y, zLoc), 0, scale);
		chars.add(typed);
		return typed;
	}
	
	public Entity getFirst() {
		return chars.getFirst();
	}
	public Entity getLast() {
		return chars.getLast();
	}
	
	public ArrayList<Entity> getChars() {
		return chars;
	}
	
	
	
	public float getScale() {
		return scale;
	}



	public int getMaxCharsInX() {
		return maxCharsInX;
	}



	public int getMaxCharsInY() {
		return maxCharsInY;
	}



	public Vector2f getXyStart() {
		return xyStart;
	}



	public Vector2f getXyEnd() {
		return xyEnd;
	}



	public float getzLoc() {
		return zLoc;
	}



	public float getxSpacing() {
		return xSpacing;
	}



	public float getySpacing() {
		return ySpacing;
	}



	public static float getCharRatio() {
		return CHAR_RATIO;
	}





	public ObjectLoader getLoader() {
		return loader;
	}



	public static enum IngameChar {
		_0('0'),
		_1('1'),
		_2('2'),
		_3('3'),
		_4('4'),
		_5('5'),
		_6('6'),
		_7('7'),
		_8('8'),
		_9('9'),
		
		A('A'),
		B('B'),
		C('C'),
		D('D'),
		E('E'),
		F('F'),
		G('G'),
		H('H'),
		I('I'),
		J('J'),
		K('K'),
		L('L'),
		M('M'),
		N('N'),
		O('O'),
		P('P'),
		Q('Q'),
		R('R'),
		S('S'),
		T('T'),
		U('U'),
		V('V'),
		W('W'),
		X('X'),
		Y('Y'),
		Z('Z'),
		
		_a('a'),
		_b('b'),
		_c('c'),
		_d('d'),
		_e('e'),
		_f('f'),
		_g('g'),
		_h('h'),
		_i('i'),
		_j('j'),
		_k('k'),
		_l('l'),
		_m('m'),
		_n('n'),
		_o('o'),
		_p('p'),
		_q('q'),
		_r('r'),
		_s('s'),
		_t('t'),
		_u('u'),
		_v('v'),
		_w('w'),
		_x('x'),
		_y('y'),
		_z('z'),
		
		exclamation('!'),
		quotation('"'),
		number('#'),
		dollar('$'),
		percent('%'),
		ampersand('&'),
		apostrophe('\''),
		lParenthesis('('),
		rParenthesis(')'),
		asterisk('*'),
		plus('+'),
		comma(','),
		minus('-'),
		dot('.'),
		slash('/'),
		rSlash('\\'),
		colon(':'),
		semicolon(';'),
		less('<'),
		equal('='),
		greater('>'),
		question('?'),
		mail('@'),
		lSquareBracket('['),
		rSquareBracket(']'),
		degree('^'),
		lowLine('_'),
		graveAccent('`'),
		lCurlyBracket('{'),
		rCurlyBracket('}'),
		vertLine('|'),
		tilde('~'),
		space(' '),
		unidentified('□'),
		;

		IngameChar(char symbol) {
			this.symbol = symbol;
		}

	    public char getChar() {
	        return symbol;
	    }
	    
	    public String getFilename(char symbol) {
	    	String o = "";
	    	for (int i = 0; i < IngameChar.values().length; i++) {
				if (IngameChar.values()[i].symbol == symbol) {
					o = IngameChar.values()[i].name();
					o = o + ".png";
				}
			}
	    	return o;
	    }

	    private char symbol;
		
	}
}
