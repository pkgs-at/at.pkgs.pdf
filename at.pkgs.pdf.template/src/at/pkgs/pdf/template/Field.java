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
import at.pkgs.pdf.builder.DocumentModel;

public class Field {

	private static final Pattern SECTION_SEPARATOR = Pattern.compile("\\s*\\|\\s*");

	private static final Pattern PART_SEPARATOR = Pattern.compile("\\s*:\\s*");

	private static final Pattern VALUE_SEPARATOR = Pattern.compile("\\s*,\\s*");

	private final int page;

	private final float left;

	private final float top;

	private final float width;

	private final float height;

	private final DocumentModel.Horizontal horizontalAlign;

	private final TextStyle textStyle;

	private final ValueFormatter valueFormatter;

	public Field(
			int page,
			float left,
			float top,
			float width,
			float height,
			DocumentModel.Horizontal horizontalAlign,
			TextStyle textStyle,
			ValueFormatter valueFormatter) {
		this.page = page;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.horizontalAlign = horizontalAlign;
		this.textStyle = textStyle;
		this.valueFormatter = valueFormatter;
	}

	public int getPage() {
		return this.page;
	}

	public float getLeft() {
		return this.left;
	}

	public float getTop() {
		return this.top;
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	public DocumentModel.Horizontal getHorizontalAlign() {
		return this.horizontalAlign;
	}

	public TextStyle getTextStyle() {
		return this.textStyle;
	}

	public ValueFormatter getValueFormatter() {
		return this.valueFormatter;
	}

	public String format(Object value) {
		return this.getValueFormatter().format(value);
	}

	/*
	 * FOTMAT:
	 * page: left, top: width, height: horizontalAlign: textStyleName
	 * page: left, top: width, height: horizontalAlign: textStyleName | format
	 * page: left, top: width, height: horizontalAlign: textStyleName | format | ifNull
	 * page: left, top: width, height: horizontalAlign: font, size, lineHeight, color
	 * page: left, top: width, height: horizontalAlign: font, size, lineHeight, color | format
	 * page: left, top: width, height: horizontalAlign: font, size, lineHeight, color | format | ifNull
	 */
	public static Field parse(
			FieldProviderFactory factory,
			String field) {
		try {
			String[] sections;
			String[] parts;
			String[] position;
			String[] size;

			sections = Field.SECTION_SEPARATOR.split(field.trim(), 3);
			parts = Field.PART_SEPARATOR.split(sections[0]);
			if (parts.length != 5) throw new IllegalArgumentException("Invalid part count");
			position = Field.VALUE_SEPARATOR.split(parts[1]);
			if (position.length != 2) throw new IllegalArgumentException("Invalid position value count");
			size = Field.VALUE_SEPARATOR.split(parts[2]);
			if (size.length != 2) throw new IllegalArgumentException("Invalid size value count");
			return new Field(
					Integer.valueOf(parts[0]),
					Float.valueOf(position[0]),
					Float.valueOf(position[1]),
					Float.valueOf(size[0]),
					Float.valueOf(size[1]),
					parts[3].isEmpty() ?
							null :
							DocumentModel.Horizontal.valueOf(parts[3].toUpperCase()),
					parts[4].indexOf(',') < 0 ?
							factory.getTextStyle(parts[4]) :
							TextStyle.parse(parts[4]),
					factory.createValueFormatter(
							sections.length < 2 ? null : sections[1],
							sections.length < 3 ? null : sections[2]));
		}
		catch (IllegalArgumentException cause) {
			throw new IllegalArgumentException("Invalid field format", cause);
		}
	}

}
