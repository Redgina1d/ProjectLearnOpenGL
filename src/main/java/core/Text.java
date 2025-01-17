package core;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import core.entity.Entity;
import core.entity.Model;
import core.utils.Constants;
import readers.ObjectLoader;

public class Text {
	
	private ArrayList<Entity> chars;
	private float scale;
	private byte maxCharsInX, maxCharsInY;
	
	private Vector2f xyStart, xyEnd;
	private float zLoc;

	public static final float CHAR_RATIO = 0.6f;
	public static final float CHAR_W = 1.2f;
	public static final float CHAR_H = 2;
	
	private ObjectLoader loader;
	
	
	public Text(ArrayList<Entity> chars, float scale, Vector2f xyStart, Vector2f xyEnd, float zLoc) throws Exception {
		this.chars = chars;
		this.scale = scale;
		this.xyStart = xyStart;
		this.xyEnd = xyEnd;
		this.zLoc = zLoc;
		loader = new ObjectLoader();
		maxCharsInX = (byte) Math.floor((xyEnd.x - xyStart.x) / (CHAR_W * scale));
		maxCharsInY = (byte) Math.floor((xyEnd.y - xyStart.y) / (CHAR_H * scale));
		if (maxCharsInX < 1 || maxCharsInY < 1) {
			throw new Exception("Type start and end points are too close - can't display any character.");
		}
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
		
		if (getFirst() == null) {
			pos = xyStart;
		} else {
			pos.x = (getLast().getPos().x + (CHAR_W * scale));
			if (pos.x > xyEnd.x) {
				pos.x = getFirst().getPos().x;
				pos.y = getLast().getPos().y - (CHAR_H * scale);
			} else {
				pos.y = getLast().getPos().y;
				if (pos.y > xyEnd.y) {
					throw new Exception("Character is out of Y bounds.");
				}
			}
		}
		
		Model model = loader.loadOBJModel("char", Constants.DIR + "/src/main/resources/textures/characters/" + filename);
		
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
