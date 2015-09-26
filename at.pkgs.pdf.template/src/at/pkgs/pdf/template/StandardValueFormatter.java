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

import java.util.List;
import java.text.MessageFormat;

public class StandardValueFormatter implements ValueFormatter {

	private final MessageFormat format;

	private final String ifNull;

	public StandardValueFormatter(String format, String ifNull) {
		this.format = new MessageFormat((format != null) ? format : "{0}");
		this.ifNull = (ifNull != null) ? ifNull : "";
	}

	@Override
	public String format(Object value) {
		if (value == null) {
			return this.ifNull;
		}
		else if (value instanceof Object[]) {
			synchronized (this) {
				return this.format.format((Object[])value);
			}
		}
		else if (value instanceof List<?>) {
			synchronized (this) {
				return this.format.format(((List<?>)value).toArray());
			}
		}
		else {
			synchronized (this) {
				return this.format.format(new Object[] { value });
			}
		}
	}

}
