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

import at.pkgs.pdf.builder.DocumentModel;

public abstract class FieldProvider implements Iterable<String>, NamedValueProvider<Field> {

	public void merge(
			float offsetLeft,
			float offsetTop,
			DocumentModel model,
			NamedValueProvider<Object> values,
			FieldVisitor visitor) {
		for (String name : this) {
			Object value;
			Field field;

			value = values.get(name);
			field = visitor.apply(name, this.get(name), value);
			model.add(new DocumentModel.Text(
					field.getPage(),
					field.getLeft() + offsetLeft,
					field.getTop() + offsetTop,
					field.getWidth(),
					field.getHeight(),
					field.getTextStyle().getLineHeight(),
					field.getHorizontalAlign(),
					field.getTextStyle().getFont(),
					field.getTextStyle().getSize(),
					field.getTextStyle().getColor(),
					field.format(value)));
		}
	}

	public void merge(
			float offsetLeft,
			float offsetTop,
			DocumentModel model,
			NamedValueProvider<Object> values) {
		this.merge(offsetLeft, offsetTop, model, values, new FieldVisitor() {

			@Override
			public Field apply(String name, Field field, Object value) {
				return field;
			}

		});
	}

	public void merge(
			DocumentModel model,
			NamedValueProvider<Object> values,
			FieldVisitor visitor) {
		this.merge(0F, 0F, model, values, visitor);
	}

	public void merge(
			DocumentModel model,
			NamedValueProvider<Object> values) {
		this.merge(0F, 0F, model, values);
	}

}
