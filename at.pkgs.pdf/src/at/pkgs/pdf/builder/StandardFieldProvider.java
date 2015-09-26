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

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;

public class StandardFieldProvider extends FieldProvider {

	private final Map<String, Field> map;

	public StandardFieldProvider(Map<String, Field> map) {
		this.map = Collections.unmodifiableMap(map);
	}

	@Override
	public Iterator<String> iterator() {
		return this.map.keySet().iterator();
	}

	@Override
	public Field get(String name) {
		return this.map.get(name);
	}

	public static StandardFieldProvider parse(
			FieldProviderFactory factory,
			Properties properties,
			String prefix,
			String... excludes) {
		Set<String> ignored;
		Map<String, Field> map;

		prefix = (prefix == null) ? "" : prefix + '.';
		ignored = new HashSet<String>(Arrays.asList(excludes));
		map = new HashMap<String, Field>();
		for (Object key : properties.keySet()) {
			String name;
			String value;

			if (!(key instanceof String)) continue;
			name = (String)key;
			if (!name.startsWith(prefix)) continue;
			if (ignored.contains(name)) continue;
			value = properties.getProperty(name);
			if (value == null) continue;
			map.put(name.substring(prefix.length()), Field.parse(factory, value));
		}
		return new StandardFieldProvider(map);
	}

}
