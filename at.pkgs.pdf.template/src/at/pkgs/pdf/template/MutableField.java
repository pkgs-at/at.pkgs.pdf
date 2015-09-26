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

import java.util.Map;
import java.util.HashMap;
import at.pkgs.pdf.builder.DocumentModel;

public class MutableField extends Field {

	private static enum Property {

		PAGE,

		LEFT,

		TOP,

		WIDTH,

		HEIGHT,

		HORIZONTAL_ALIGN,

		TEXT_STYLE,

		VALUE_FORMATTER;

	}

	private final Map<Property, Object> properties;

	public MutableField(Field field) {
		super(
				field.getPage(),
				field.getLeft(),
				field.getTop(),
				field.getWidth(),
				field.getHeight(),
				field.getHorizontalAlign(),
				new MutableTextStyle(field.getTextStyle()),
				field.getValueFormatter());
		this.properties = new HashMap<Property, Object>();
	}

	@Override
	public int getPage() {
		if (this.properties.containsKey(Property.PAGE))
			return (int)this.properties.get(Property.PAGE);
		else
			return super.getPage();
	}

	public void setPage(int value) {
		this.properties.put(Property.PAGE, value);
	}

	@Override
	public float getLeft() {
		if (this.properties.containsKey(Property.LEFT))
			return (float)this.properties.get(Property.LEFT);
		else
			return super.getLeft();
	}

	public void setLeft(float value) {
		this.properties.put(Property.LEFT, value);
	}

	@Override
	public float getTop() {
		if (this.properties.containsKey(Property.TOP))
			return (float)this.properties.get(Property.TOP);
		else
			return super.getTop();
	}

	public void setTop(float value) {
		this.properties.put(Property.TOP, value);
	}

	@Override
	public float getWidth() {
		if (this.properties.containsKey(Property.WIDTH))
			return (float)this.properties.get(Property.WIDTH);
		else
			return super.getWidth();
	}

	public void setWidth(float value) {
		this.properties.put(Property.WIDTH, value);
	}

	@Override
	public float getHeight() {
		if (this.properties.containsKey(Property.HEIGHT))
			return (float)this.properties.get(Property.HEIGHT);
		else
			return super.getHeight();
	}

	public void setHeight(float value) {
		this.properties.put(Property.HEIGHT, value);
	}

	@Override
	public DocumentModel.Horizontal getHorizontalAlign() {
		if (this.properties.containsKey(Property.HORIZONTAL_ALIGN))
			return (DocumentModel.Horizontal)this.properties.get(Property.HORIZONTAL_ALIGN);
		else
			return super.getHorizontalAlign();
	}

	public void setHorizontalAlign(DocumentModel.Horizontal value) {
		this.properties.put(Property.HORIZONTAL_ALIGN, value);
	}

	@Override
	public MutableTextStyle getTextStyle() {
		if (this.properties.containsKey(Property.TEXT_STYLE))
			return (MutableTextStyle)this.properties.get(Property.TEXT_STYLE);
		else
			return (MutableTextStyle)super.getTextStyle();
	}

	public void setTextStyle(TextStyle value) {
		this.properties.put(Property.TEXT_STYLE, (value instanceof MutableTextStyle) ? value : new MutableTextStyle(value));
	}

	@Override
	public ValueFormatter getValueFormatter() {
		if (this.properties.containsKey(Property.VALUE_FORMATTER))
			return (ValueFormatter)this.properties.get(Property.VALUE_FORMATTER);
		else
			return super.getValueFormatter();
	}

	public void setValueFormatter(ValueFormatter value) {
		this.properties.put(Property.VALUE_FORMATTER, value);
	}

}
