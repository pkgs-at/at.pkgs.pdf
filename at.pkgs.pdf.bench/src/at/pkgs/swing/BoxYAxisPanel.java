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

public class BoxYAxisPanel extends LayoutedPanel<BoxLayout> {

	private static final long serialVersionUID = 1L;

	public BoxYAxisPanel() {
		super.initialize(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public BoxYAxisPanel glue() {
		this.add(Box.createVerticalGlue());
		return this;
	}

	public BoxYAxisPanel space(int height) {
		this.add(Box.createRigidArea(new Dimension(0, height)));
		return this;
	}

}
