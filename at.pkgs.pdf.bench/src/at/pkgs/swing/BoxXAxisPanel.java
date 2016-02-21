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

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class BoxXAxisPanel extends LayoutedPanel<BoxLayout> {

	private static final long serialVersionUID = 1L;

	public BoxXAxisPanel() {
		super.initialize(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	public BoxXAxisPanel glue() {
		this.add(Box.createHorizontalGlue());
		return this;
	}

	public BoxXAxisPanel space(int width) {
		this.add(Box.createRigidArea(new Dimension(width, 0)));
		return this;
	}

}
