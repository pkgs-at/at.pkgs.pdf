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

package at.pkgs.swing;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionDispatcher<ComponentType extends Component> implements ActionListener {

	public static interface Handler<ComponentType extends Component> {

		public void action(ComponentType component, ActionEvent event);

	}

	public static interface Entity<ComponentType extends Component> {

		public String name();

		public Handler<ComponentType> handler();

	}

	private final ComponentType component;

	private final Map<String, Handler<ComponentType>> map;

	public ActionDispatcher(ComponentType component, Entity<ComponentType>[] values) {
		Map<String, Handler<ComponentType>> map;

		this.component = component;
		map = new HashMap<String, Handler<ComponentType>>();
		for (Entity<ComponentType> entity : values) map.put(entity.name(), entity.handler());
		this.map = Collections.unmodifiableMap(map);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Handler<ComponentType> handler;

		handler = this.map.get(event.getActionCommand());
		if (handler == null) return;
		handler.action(this.component, event);
	}

}
