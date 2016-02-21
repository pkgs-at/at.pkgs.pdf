/*
 * Copyright (c) 2009-2016, Architector Inc., Japan
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.pkgs.barcode;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;

public class Code39 implements OneDimensionalBarcode {

	public static enum Symbol {

		NARROW_SPACE(false, true),

		NARROW_BAR(true, true),

		WIDE_SPACE(false, false),

		WIDE_BAR(true, false);

		private final boolean bar;

		private final boolean narrow;

		private Symbol(boolean bar, boolean narrow) {
			this.bar = bar;
			this.narrow = narrow;
		}

		public boolean isSpace() {
			return !this.bar;
		}

		public boolean isBar() {
			return this.bar;
		}

		public boolean isNarrow() {
			return this.narrow;
		}

		public boolean isWide() {
			return !this.narrow;
		}

	}

	public static interface PatternBuilder {

		public void build(Symbol[] symbols);

	}

	public static enum BarPattern implements PatternBuilder {

		BAR_1(0, 4),

		BAR_2(1, 4),

		BAR_3(1, 0),

		BAR_4(2, 4),

		BAR_5(2, 0),

		BAR_6(2, 1),

		BAR_7(3, 4),

		BAR_8(3, 0),

		BAR_9(3, 1),

		BAR_A(3, 2),

		BAR_B();

		private final int[] marks;

		private BarPattern(int... marks) {
			this.marks = marks;
		}

		@Override
		public void build(Symbol[] symbols) {
			for (int mark : marks) symbols[mark * 2] = Symbol.WIDE_BAR;
		}

	}

	public static enum SpacePattern implements PatternBuilder {

		SPACE_0(1),

		SPACE_1(2),

		SPACE_2(3),

		SPACE_3(0),

		SPACE_A(2, 3, 0),

		SPACE_B(3, 0, 1),

		SPACE_C(0, 1, 2),

		SPACE_D(1, 2, 3);

		private final int[] marks;

		private SpacePattern(int... marks) {
			this.marks = marks;
		}

		@Override
		public void build(Symbol[] symbols) {
			for (int mark : marks) symbols[mark * 2 + 1] = Symbol.WIDE_SPACE;
		}

	}

	public static enum Character {

		DIGIT_0('0', BarPattern.BAR_A, SpacePattern.SPACE_0),

		DIGIT_1('1', BarPattern.BAR_1, SpacePattern.SPACE_0),

		DIGIT_2('2', BarPattern.BAR_2, SpacePattern.SPACE_0),

		DIGIT_3('3', BarPattern.BAR_3, SpacePattern.SPACE_0),

		DIGIT_4('4', BarPattern.BAR_4, SpacePattern.SPACE_0),

		DIGIT_5('5', BarPattern.BAR_5, SpacePattern.SPACE_0),

		DIGIT_6('6', BarPattern.BAR_6, SpacePattern.SPACE_0),

		DIGIT_7('7', BarPattern.BAR_7, SpacePattern.SPACE_0),

		DIGIT_8('8', BarPattern.BAR_8, SpacePattern.SPACE_0),

		DIGIT_9('9', BarPattern.BAR_9, SpacePattern.SPACE_0),

		LETTER_A('A', BarPattern.BAR_1, SpacePattern.SPACE_1),

		LETTER_B('B', BarPattern.BAR_2, SpacePattern.SPACE_1),

		LETTER_C('C', BarPattern.BAR_3, SpacePattern.SPACE_1),

		LETTER_D('D', BarPattern.BAR_4, SpacePattern.SPACE_1),

		LETTER_E('E', BarPattern.BAR_5, SpacePattern.SPACE_1),

		LETTER_F('F', BarPattern.BAR_6, SpacePattern.SPACE_1),

		LETTER_G('G', BarPattern.BAR_7, SpacePattern.SPACE_1),

		LETTER_H('H', BarPattern.BAR_8, SpacePattern.SPACE_1),

		LETTER_I('I', BarPattern.BAR_9, SpacePattern.SPACE_1),

		LETTER_J('J', BarPattern.BAR_A, SpacePattern.SPACE_1),

		LETTER_K('K', BarPattern.BAR_1, SpacePattern.SPACE_2),

		LETTER_L('L', BarPattern.BAR_2, SpacePattern.SPACE_2),

		LETTER_M('M', BarPattern.BAR_3, SpacePattern.SPACE_2),

		LETTER_N('N', BarPattern.BAR_4, SpacePattern.SPACE_2),

		LETTER_O('O', BarPattern.BAR_5, SpacePattern.SPACE_2),

		LETTER_P('P', BarPattern.BAR_6, SpacePattern.SPACE_2),

		LETTER_Q('Q', BarPattern.BAR_7, SpacePattern.SPACE_2),

		LETTER_R('R', BarPattern.BAR_8, SpacePattern.SPACE_2),

		LETTER_S('S', BarPattern.BAR_9, SpacePattern.SPACE_2),

		LETTER_T('T', BarPattern.BAR_A, SpacePattern.SPACE_2),

		LETTER_U('U', BarPattern.BAR_1, SpacePattern.SPACE_3),

		LETTER_V('V', BarPattern.BAR_2, SpacePattern.SPACE_3),

		LETTER_W('W', BarPattern.BAR_3, SpacePattern.SPACE_3),

		LETTER_X('X', BarPattern.BAR_4, SpacePattern.SPACE_3),

		LETTER_Y('Y', BarPattern.BAR_5, SpacePattern.SPACE_3),

		LETTER_Z('Z', BarPattern.BAR_6, SpacePattern.SPACE_3),

		HYPHEN_MINUS('-', BarPattern.BAR_7, SpacePattern.SPACE_3),

		FULL_STOP('.', BarPattern.BAR_8, SpacePattern.SPACE_3),

		SPACE(' ', BarPattern.BAR_9, SpacePattern.SPACE_3),

		DOLLAR_SIGN('$', BarPattern.BAR_B, SpacePattern.SPACE_C),

		SLASH('/', BarPattern.BAR_B, SpacePattern.SPACE_B),

		PLUS_SIGN('+', BarPattern.BAR_B, SpacePattern.SPACE_A),

		PERCENT_SIGN('%', BarPattern.BAR_B, SpacePattern.SPACE_D),

		ASTERISK('*', BarPattern.BAR_A, SpacePattern.SPACE_3);

		private final char character;

		private final List<Symbol> pattern;

		private Character(char character, PatternBuilder... builders) {
			Symbol[] symbols;

			this.character = character;
			symbols = new Symbol[9];
			for (int index = 0; index < symbols.length; index ++)
				symbols[index] = (index % 2 == 0 ? Symbol.NARROW_BAR : Symbol.NARROW_SPACE);
			for (PatternBuilder builder : builders)
				builder.build(symbols);
			this.pattern = Collections.unmodifiableList(Arrays.asList(symbols));
		}

		public int getNumber() {
			return this.ordinal();
		}

		public char getCharacter() {
			return this.character;
		}

		public List<Symbol> getPattern() {
			return this.pattern;
		}

		public void draw(Drawer drawer) {
			for (Symbol symbol : this.getPattern()) {
				if (symbol.isSpace())
					drawer.space(symbol.isWide());
				else
					drawer.bar(symbol.isWide());
			}
			drawer.text(this.getCharacter());
		}

		private static final Map<Integer, Character> NUMBER_INDEX;

		static {
			Map<Integer, Character> map;

			map = new HashMap<Integer, Character>();
			for (Character value : Character.values())
				map.put(value.getNumber(), value);
			NUMBER_INDEX = Collections.unmodifiableMap(map);
		}

		private static final Map<java.lang.Character, Character> CHARACTER_INDEX;

		static {
			Map<java.lang.Character, Character> map;

			map = new HashMap<java.lang.Character, Character>();
			for (Character value : Character.values())
				map.put(value.getCharacter(), value);
			CHARACTER_INDEX = Collections.unmodifiableMap(map);
		}

		public static Character valueOf(int number) {
			Character value;

			value = Character.NUMBER_INDEX.get(number);
			if (value == null)
				throw new IllegalArgumentException(String.format("Invalid CODE39 number: %d", number));
			return value;
		}

		public static Character valueOf(char character) {
			Character value;

			value = Character.CHARACTER_INDEX.get(java.lang.Character.toUpperCase(character));
			if (value == null)
				throw new IllegalArgumentException(String.format("Invalid CODE39 character: %c", character));
			return value;
		}

	}

	public class Drawer {

		private final OneDimensionalImage image;

		public Drawer() {
			this.image = new OneDimensionalImage();
		}

		public OneDimensionalImage getImage() {
			return this.image;
		}

		public Drawer margin() {
			this.image.draw(false, Code39.this.getMargin());
			return this;
		}

		public Drawer characterGap() {
			this.image.draw(false, Code39.this.getCharacterGap());
			return this;
		}

		public Drawer space(boolean isWide) {
			this.image.draw(false, isWide ? Code39.this.getWide() : 1);
			return this;
		}

		public Drawer bar(boolean isWide) {
			this.image.draw(true, isWide ? Code39.this.getWide() : 1);
			return this;
		}

		public Drawer text(char character) {
			this.image.text(character);
			return this;
		}

		public Drawer character(Character character) {
			character.draw(this);
			return this;
		}

	}

	private boolean checkDigit;

	private double wide;

	private double characterGap;

	private double margin;

	public Code39(boolean checkDigit) {
		this.checkDigit = checkDigit;
		this.margin = 10.0D;
		this.characterGap = 1.00D;
		this.wide = 2.25D;
	}

	public Code39() {
		this(true);
	}

	public Code39 checkDigit(boolean value) {
		this.checkDigit = value;
		return this;
	}

	public Code39 margin(double value) {
		this.margin = value;
		return this;
	}

	public Code39 characterGap(double value) {
		this.characterGap = value;
		return this;
	}

	public Code39 wide(double value) {
		this.wide = value;
		return this;
	}

	public boolean getCheckDigit() {
		return this.checkDigit;
	}

	public double getWide() {
		return this.wide;
	}

	public double getCharacterGap() {
		return this.characterGap;
	}

	public double getMargin() {
		return this.margin;
	}

	@Override
	public String getName() {
		return "CODE-39";
	}

	@Override
	public OneDimensionalImage encode(String value) {
		Drawer drawer;
		int checkDigit;

		drawer = new Drawer();
		checkDigit = 0;
		drawer.margin().character(Character.ASTERISK);
		for (char item : value.toCharArray()) {
			Character character;

			character = Character.valueOf(item);
			drawer.characterGap().character(character);
			checkDigit += character.getNumber();
			checkDigit %= 43;
		}
		if (this.getCheckDigit())
			drawer.characterGap().character(Character.valueOf(checkDigit));
		drawer.characterGap().character(Character.ASTERISK).margin();
		return drawer.getImage();
	}

}
