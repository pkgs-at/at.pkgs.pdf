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

import java.util.Properties;

public class FieldProviderFactory {

	private NamedValueProvider<TextStyle> textStyles;

	public ValueFormatter createValueFormatter(
			String format,
			String ifNull) {
		return new StandardValueFormatter(format, ifNull);
	}

	public NamedValueProvider<TextStyle> getTextStyleProvider() {
		return this.textStyles;
	}

	public TextStyle getTextStyle(String name) {
		TextStyle style;

		if (this.textStyles == null)
			throw new IllegalStateException("TextStyle provider is null");
		style = this.textStyles.get(name);
		if (style == null)
			throw new IllegalArgumentException("TextStyle not found: " + name);
		return style;
	}

	public void setTextStyleProvider(NamedValueProvider<TextStyle> textStyles) {
		this.textStyles = textStyles;
	}

	public void loadTextStyleProvider(
			Properties properties,
			String prefix,
			String... excludes) {
		this.textStyles = StandardTextStyleProvider.parse(properties, prefix, excludes);
	}

	public FieldProvider createFieldProvider(
			Properties properties,
			String prefix,
			String... excludes) {
		return StandardFieldProvider.parse(this, properties, prefix, excludes);
	}

}
