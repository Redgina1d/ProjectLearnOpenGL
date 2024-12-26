package core;

import org.joml.Vector3f;
import org.joml.Vector4f;

import core.entity.GUI2D;
import core.entity.Model;
import core.utils.Constants;

public class Text {
	
	private int charsInCurrLine = 0;

	public static final float CHAR_RATIO = 0.6f;
	
	private ObjectLoader loader = new ObjectLoader();
	
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
	    
	    public String getName(char symbol) {
	    	
	    }

	    private char symbol;
		
	}
	
	public GUI2D type(Vector3f typeStart, Vector3f typeEnd, char c, Vector4f color, float charScaling) {
		String filename = null;
		for (IngameChar character : IngameChar.values()) {
            if (character.symbol == c) {
                filename = character.name();
            }
        }
		if (filename == null) {
			System.err.println("Can't type character: " + c);
			return null;
		}
		Model model = loader.loadOBJModel("char", Constants.DIR + "/src/main/resources/textures/" + filename + ".png");
		
		Vector3f pos = new Vector3f(typeStart + );
		
		charsInCurrLine++;
		return new GUI2D(model, typeEnd, 0, charScaling);
		
	}
}
