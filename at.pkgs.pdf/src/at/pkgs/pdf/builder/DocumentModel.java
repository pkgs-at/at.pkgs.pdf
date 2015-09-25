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

package at.pkgs.pdf.builder;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlType(propOrder = {
		"version",
		"fonts",
		"values",
})
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "DocumentModel")
public class DocumentModel implements Serializable {

	@XmlType(propOrder = {
			"name",
			"file",
			"encoding",
			"embeded",
	})
	@XmlAccessorType(XmlAccessType.NONE)
	public static class Font implements Serializable {

		private static final long serialVersionUID = 1L;

		private String name;

		private String file;

		private String encoding;

		private boolean embeded;

		public Font(
				String name,
				String file,
				String encoding,
				boolean embeded) {
			this.name = name;
			this.file = file;
			this.encoding = encoding;
			this.embeded = embeded;
		}

		public Font() {
			this(null, null, null, false);
		}

		@XmlAttribute(name = "name")
		public String getName() {
			return this.name;
		}

		public void setName(String value) {
			this.name = value;
		}

		@XmlValue()
		public String getFile() {
			return this.file;
		}

		public void setFile(String value) {
			this.file = value;
		}

		@XmlAttribute(name = "encoding")
		public String getEncoding() {
			return this.encoding;
		}

		public void setEncoding(String value) {
			this.encoding = value;
		}

		@XmlAttribute(name = "embeded")
		public boolean getEmbeded() {
			return this.embeded;
		}

		public void setEmbeded(boolean value) {
			this.embeded = value;
		}

	}

	public static enum Horizontal {

		@XmlEnumValue("left")
		LEFT,

		@XmlEnumValue("center")
		CENTER,

		@XmlEnumValue("right")
		RIGHT,

	}

	public static interface Value extends Serializable {

		public int getPage();

	}

	@XmlType(propOrder = {
			"page",
			"left",
			"top",
			"width",
			"height",
			"leading",
			"horizontal",
			"font",
			"size",
			"color",
			"value",
	})
	@XmlAccessorType(XmlAccessType.NONE)
	public static class Text implements Value {

		private static final long serialVersionUID = 1L;

		private int page;

		private float left;

		private float top;

		private float width;

		private float height;

		private float leading;

		private Horizontal horizontal;

		private String font;

		private float size;

		private String color;

		private String value;

		public Text(
				int page,
				float left,
				float top,
				float width,
				float height,
				float leading,
				Horizontal horizontal,
				String font,
				float size,
				String color,
				String value) {
			this.page = page;
			this.left = left;
			this.top = top;
			this.width = width;
			this.height = height;
			this.leading = leading;
			this.horizontal = horizontal;
			this.font = font;
			this.size = size;
			this.color = color;
			this.value = value;
		}

		public Text() {
			this(
					0,
					0F,
					0F,
					0F,
					0F,
					0F,
					null,
					null,
					0F,
					null,
					null);
		}

		@XmlAttribute(name = "page")
		public int getPage() {
			return this.page;
		}

		public void setPage(int value) {
			this.page = value;
		}

		@XmlAttribute(name = "left")
		public float getLeft() {
			return this.left;
		}

		public void setLeft(float value) {
			this.left = value;
		}

		@XmlAttribute(name = "top")
		public float getTop() {
			return this.top;
		}

		public void setTop(float value) {
			this.top = value;
		}

		@XmlAttribute(name = "width")
		public float getWidth() {
			return this.width;
		}

		public void setWidth(float value) {
			this.width = value;
		}

		@XmlAttribute(name = "height")
		public float getHeight() {
			return this.height;
		}

		public void setHeight(float value) {
			this.height = value;
		}

		@XmlAttribute(name = "leading")
		public float getLeading() {
			return this.leading;
		}

		public void setLeading(float value) {
			this.leading = value;
		}

		@XmlAttribute(name = "horizontal")
		public Horizontal getHorizontal() {
			return this.horizontal;
		}

		public void setHorizontal(Horizontal value) {
			this.horizontal = value;
		}

		@XmlAttribute(name = "font")
		public String getFont() {
			return this.font;
		}

		public void setFont(String value) {
			this.font = value;
		}

		@XmlAttribute(name = "size")
		public float getSize() {
			return this.size;
		}

		public void setSize(float value) {
			this.size = value;
		}

		@XmlAttribute()
		public String getColor() {
			return this.color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		@XmlValue()
		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	private static final long serialVersionUID = 1L;

	private int version;

	private List<Font> fonts;

	private List<Value> values;

	public DocumentModel() {
		this.version = 1;
	}

	@XmlAttribute(name = "version")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int value) {
		this.version = value;
	}

	@XmlElementWrapper(name = "Fonts")
	@XmlElement(name = "Font")
	public List<Font> getFonts() {
		return this.fonts;
	}

	public void setFonts(List<Font> value) {
		this.fonts = value;
	}

	public void add(Font value) {
		if (this.fonts == null)
			this.fonts = new ArrayList<Font>();
		this.fonts.add(value);
	}

	@XmlElementWrapper(name = "Values")
	@XmlElements({
		@XmlElement(name = "Text", type = Text.class)
	})
	public List<Value> getValues() {
		return this.values;
	}

	public void setValues(List<Value> value) {
		this.values = value;
	}

	public void add(Value value) {
		if (this.values == null)
			this.values = new ArrayList<Value>();
		this.values.add(value);
	}

	public static DocumentModel parse(InputStream source) {
		return JAXB.unmarshal(source, DocumentModel.class);
	}

	public static DocumentModel parse(File source) {
		return JAXB.unmarshal(source, DocumentModel.class);
	}

	public static void main(String[] arguments) {
		DocumentModel model;

		model = new DocumentModel();
		model.add(
				new Font(
						"monospaced.regular",
						"/at/pkgs/pdf/builder/font/migmix-1m-regular.ttf",
						"Identity-H",
						true));
		model.add(
				new Font(
						"monospaced.bold",
						"/at/pkgs/pdf/builder/font/migmix-1m-bold.ttf",
						"Identity-H",
						true));
		model.add(
				new Text(
						1,
						10F,
						20F,
						300F,
						80F,
						0F,
						null,
						"monospaced.regular",
						10F,
						"F00",
						"日本語\n" +
						"This is a text.\n" +
						"This is a text." +
						"This is a text.\n" +
						"This is a text.\n" +
						"This is a text.\n" +
						"This is a text." +
						"This is a text.\n" +
						"This is a text.\n"));
		JAXB.marshal(model, System.out);
	}

}
