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

public class MutableTextStyle extends TextStyle {

	private static enum Property {

		FONT,

		SIZE,

		LINE_HEIGHT,

		COLOR;

	}

	private final Map<Property, Object> properties;

	public MutableTextStyle(TextStyle textStyle) {
		super(
				textStyle.getFont(),
				textStyle.getSize(),
				textStyle.getLineHeight(),
				textStyle.getColor());
		this.properties = new HashMap<Property, Object>();
	}


	@Override
	public String getFont() {
		if (this.properties.containsKey(Property.FONT))
			return (String)this.properties.get(Property.FONT);
		else
			return super.getFont();
	}

	public void setFont(String value) {
		this.properties.put(Property.FONT, value);
	}

	@Override
	public float getSize() {
		if (this.properties.containsKey(Property.SIZE))
			return (float)this.properties.get(Property.SIZE);
		else
			return super.getSize();
	}

	public void setSize(float value) {
		this.properties.put(Property.SIZE, value);
	}

	@Override
	public float getLineHeight() {
		if (this.properties.containsKey(Property.LINE_HEIGHT))
			return (float)this.properties.get(Property.LINE_HEIGHT);
		else
			return super.getLineHeight();
	}

	public void setLineHeight(float value) {
		this.properties.put(Property.LINE_HEIGHT, value);
	}

	@Override
	public String getColor() {
		if (this.properties.containsKey(Property.COLOR))
			return (String)this.properties.get(Property.class);
		else
			return super.getColor();
	}

	public void setColor(String value) {
		this.properties.put(Property.COLOR, value);
	}

}
