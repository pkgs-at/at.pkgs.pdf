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

package at.pkgs.barcode;

import java.util.List;
import java.util.ArrayList;

public class OneDimensionalImage {

	public class Bar {

		private final double position;

		private final double length;

		public Bar(double position, double length) {
			this.position = position;
			this.length = length;
		}

		public double getPosition() {
			return this.position;
		}

		public double getLength() {
			return this.length;
		}

	}

	private double size;

	private final List<Bar> bars;

	private final StringBuilder text;

	public OneDimensionalImage() {
		this.size = 0D;
		this.bars = new ArrayList<Bar>();
		this.text = new StringBuilder();
	}

	public double getSize() {
		return this.size;
	}

	public List<Bar> getBars() {
		return this.bars;
	}

	public String getText() {
		return this.text.toString();
	}

	public OneDimensionalImage draw(boolean isBar, double length) {
		if (isBar) this.bars.add(new Bar(this.size, length));
		this.size += length;
		return this;
	}

	public OneDimensionalImage text(char character) {
		this.text.append(character);
		return this;
	}

}
