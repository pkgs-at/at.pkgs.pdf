/*
 * Copyright (c) 2009-2015, Architector Inc., Japan
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

package at.pkgs.pdf.template;

import java.util.regex.Pattern;

public class TextStyle {

	private static final Pattern VALUE_SEPARATOR = Pattern.compile("\\s*,\\s*");

	private final String font;

	private final float size;

	private final float lineHeight;

	private final String color;

	public TextStyle(
			String font,
			float size,
			float lineHeight,
			String color) {
		this.font = font;
		this.size = size;
		this.lineHeight = lineHeight;
		this.color = color;
	}

	public String getFont() {
		return this.font;
	}

	public float getSize() {
		return this.size;
	}

	public float getLineHeight() {
		return this.lineHeight;
	}

	public String getColor() {
		return this.color;
	}

	/*
	 * FOTMAT:
	 * font, size, lineHeight, color
	 */
	public static TextStyle parse(
			String style) {
		try {
			String[] values;

			values = TextStyle.VALUE_SEPARATOR.split(style.trim());
			if (values.length != 4) throw new IllegalArgumentException("Invalid value count");
			return new TextStyle(
					values[0],
					Float.valueOf(values[1]),
					Float.valueOf(values[2]),
					values[3]);
		}
		catch (IllegalArgumentException cause) {
			throw new IllegalArgumentException("Invalid text style format", cause);
		}
	}

}
