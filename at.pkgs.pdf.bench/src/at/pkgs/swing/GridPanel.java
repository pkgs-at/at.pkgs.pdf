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

import java.awt.Insets;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.Box;

public class GridPanel extends LayoutedPanel<GridBagLayout> {

	public static enum Anchor {

		CENTER(GridBagConstraints.CENTER),

		NORTHWEST(GridBagConstraints.NORTHWEST),

		NORTH(GridBagConstraints.NORTH),

		NORTHEAST(GridBagConstraints.NORTHEAST),

		WEST(GridBagConstraints.WEST),

		EAST(GridBagConstraints.EAST),

		SOUTHWEST(GridBagConstraints.SOUTHWEST),

		SOUTH(GridBagConstraints.SOUTH),

		SOUTHEAST(GridBagConstraints.SOUTHEAST),

		FIRST_LINE_START(GridBagConstraints.FIRST_LINE_START),

		PAGE_START(GridBagConstraints.PAGE_START),

		FIRST_LINE_END(GridBagConstraints.FIRST_LINE_END),

		LINE_START(GridBagConstraints.LINE_START),

		LINE_END(GridBagConstraints.LINE_END),

		LAST_LINE_START(GridBagConstraints.LAST_LINE_START),

		PAGE_END(GridBagConstraints.PAGE_END),

		LAST_LINE_END(GridBagConstraints.LAST_LINE_END),

		BASELINE(GridBagConstraints.BASELINE),

		BASELINE_LEADING(GridBagConstraints.BASELINE_LEADING),

		BASELINE_TRAILING(GridBagConstraints.BASELINE_TRAILING),

		ABOVE_BASELINE(GridBagConstraints.ABOVE_BASELINE),

		ABOVE_BASELINE_LEADING(GridBagConstraints.ABOVE_BASELINE_LEADING),

		ABOVE_BASELINE_TRAILING(GridBagConstraints.ABOVE_BASELINE_TRAILING),

		BELOW_BASELINE(GridBagConstraints.BELOW_BASELINE),

		BELOW_BASELINE_LEADING(GridBagConstraints.BELOW_BASELINE_LEADING),

		BELOW_BASELINE_TRAILING(GridBagConstraints.BELOW_BASELINE_TRAILING);

		private final int value;

		private Anchor(int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}

	}

	public static enum Fill {

		NONE(GridBagConstraints.NONE),

		BOTH(GridBagConstraints.BOTH),

		HORIZONTAL(GridBagConstraints.HORIZONTAL),

		VERTICAL(GridBagConstraints.VERTICAL);

		private final int value;

		private Fill(int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}

	}

	public class ConstraintsBuilder {

		private final GridBagConstraints constraints;

		public ConstraintsBuilder(int x, int y) {
			this.constraints = new GridBagConstraints();
			this.constraints.gridx = x;
			this.constraints.gridy = y;
		}

		public ConstraintsBuilder size(int width, int height) {
			this.constraints.gridwidth = width;
			this.constraints.gridheight = height;
			return this;
		}

		public ConstraintsBuilder width(int width) {
			this.constraints.gridwidth = width;
			return this;
		}

		public ConstraintsBuilder height(int height) {
			this.constraints.gridheight = height;
			return this;
		}

		public ConstraintsBuilder weight(float x, float y) {
			this.constraints.weightx = x;
			this.constraints.weighty = y;
			return this;
		}

		public ConstraintsBuilder weightHorizontal(float x) {
			this.constraints.weightx = x;
			return this;
		}

		public ConstraintsBuilder weightVertical(float y) {
			this.constraints.weighty = y;
			return this;
		}

		public ConstraintsBuilder insets(Insets value) {
			this.constraints.insets = value;
			return this;
		}

		public ConstraintsBuilder insets(int left, int right, int top, int bottom) {
			return this.insets(new Insets(left, right, top, bottom));
		}

		public ConstraintsBuilder insets(int horizontal, int vertical) {
			return this.insets(horizontal, horizontal, vertical, vertical);
		}

		public ConstraintsBuilder insets(int around) {
			return this.insets(around, around);
		}

		public ConstraintsBuilder insetLeft(int value) {
			this.constraints.insets.left = value;
			return this;
		}

		public ConstraintsBuilder insetRight(int value) {
			this.constraints.insets.right = value;
			return this;
		}

		public ConstraintsBuilder insetTop(int value) {
			this.constraints.insets.top = value;
			return this;
		}

		public ConstraintsBuilder insetBottom(int value) {
			this.constraints.insets.bottom = value;
			return this;
		}

		public ConstraintsBuilder insetHorizontal(int value) {
			this.constraints.insets.left = value;
			this.constraints.insets.right = value;
			return this;
		}

		public ConstraintsBuilder insetVertical(int value) {
			this.constraints.insets.top = value;
			this.constraints.insets.bottom = value;
			return this;
		}

		public ConstraintsBuilder insetAround(int value) {
			this.constraints.insets.left = value;
			this.constraints.insets.right = value;
			this.constraints.insets.top = value;
			this.constraints.insets.bottom = value;
			return this;
		}

		public ConstraintsBuilder anchor(int value) {
			this.constraints.anchor = value;
			return this;
		}

		public ConstraintsBuilder anchor(Anchor value) {
			return this.anchor(value.toValue());
		}

		public ConstraintsBuilder anchorCenter() {
			return this.anchor(Anchor.CENTER);
		}

		public ConstraintsBuilder anchorNorthwest() {
			return this.anchor(Anchor.NORTHWEST);
		}

		public ConstraintsBuilder anchorNorth() {
			return this.anchor(Anchor.NORTH);
		}

		public ConstraintsBuilder anchorNorthEast() {
			return this.anchor(Anchor.NORTHEAST);
		}

		public ConstraintsBuilder anchorWest() {
			return this.anchor(Anchor.WEST);
		}

		public ConstraintsBuilder anchorEast() {
			return this.anchor(Anchor.EAST);
		}

		public ConstraintsBuilder anchorSouthwest() {
			return this.anchor(Anchor.SOUTHWEST);
		}

		public ConstraintsBuilder anchorSouth() {
			return this.anchor(Anchor.SOUTH);
		}

		public ConstraintsBuilder anchorSoutheast() {
			return this.anchor(Anchor.SOUTHEAST);
		}

		public ConstraintsBuilder anchorFirstLineStart() {
			return this.anchor(Anchor.FIRST_LINE_START);
		}

		public ConstraintsBuilder anchorPageStart() {
			return this.anchor(Anchor.PAGE_START);
		}

		public ConstraintsBuilder anchorFirstLineEnd() {
			return this.anchor(Anchor.FIRST_LINE_END);
		}

		public ConstraintsBuilder anchorLineStart() {
			return this.anchor(Anchor.LINE_START);
		}

		public ConstraintsBuilder anchorLineEnd() {
			return this.anchor(Anchor.LINE_END);
		}

		public ConstraintsBuilder anchorLastLineStart() {
			return this.anchor(Anchor.LAST_LINE_START);
		}

		public ConstraintsBuilder anchorPageEnd() {
			return this.anchor(Anchor.PAGE_END);
		}

		public ConstraintsBuilder anchorLastLineEnd() {
			return this.anchor(Anchor.LAST_LINE_END);
		}

		public ConstraintsBuilder anchorBaseline() {
			return this.anchor(Anchor.BASELINE);
		}

		public ConstraintsBuilder anchorBaselineLeading() {
			return this.anchor(Anchor.BASELINE_LEADING);
		}

		public ConstraintsBuilder anchorBaselineTrailing() {
			return this.anchor(Anchor.BASELINE_TRAILING);
		}

		public ConstraintsBuilder anchorAboveBaseline() {
			return this.anchor(Anchor.ABOVE_BASELINE);
		}

		public ConstraintsBuilder anchorAboveBaselineLeading() {
			return this.anchor(Anchor.ABOVE_BASELINE_LEADING);
		}

		public ConstraintsBuilder anchorAboveBaselineTrailing() {
			return this.anchor(Anchor.ABOVE_BASELINE_TRAILING);
		}

		public ConstraintsBuilder anchorBelowBaseline() {
			return this.anchor(Anchor.BELOW_BASELINE);
		}

		public ConstraintsBuilder anchorBelowBaselineLeading() {
			return this.anchor(Anchor.BELOW_BASELINE_LEADING);
		}

		public ConstraintsBuilder anchorBelowBaselineTrailing() {
			return this.anchor(Anchor.BELOW_BASELINE_TRAILING);
		}

		public ConstraintsBuilder fill(int value) {
			this.constraints.fill = value;
			return this;
		}

		public ConstraintsBuilder fill(Fill value) {
			return this.fill(value.toValue());
		}

		public ConstraintsBuilder fillNone() {
			return this.fill(Fill.NONE);
		}

		public ConstraintsBuilder fillBoth() {
			return this.fill(Fill.BOTH);
		}

		public ConstraintsBuilder fillHorizontal() {
			return this.fill(Fill.HORIZONTAL);
		}

		public ConstraintsBuilder fillVertical() {
			return this.fill(Fill.VERTICAL);
		}

		public <ComponentType extends Component> ComponentType add(
				ComponentType component) {
			GridPanel.this.add(component, this.constraints);
			return component;
		}

		public <ComponentType extends Component> ComponentType add(
				ComponentBuilder<ComponentType> builder) {
			return this.add(builder.get());
		}

	}

	private static final long serialVersionUID = 1L;

	public GridPanel() {
		super.initialize(new GridBagLayout());
	}

	@Override
	public Component add(
			Component component) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Component add(
			Component component,
			int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(
			Component component,
			Object constraints) {
		throw new UnsupportedOperationException();
	}

	public Component add(
			Component component,
			GridBagConstraints constraints) {
		this.getLayout().setConstraints(component, constraints);
		return super.add(component);
	}

	public Component add(
			Component component,
			int x,
			int y,
			int width,
			int height) {
		GridBagConstraints constraints;

		constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		return this.add(component, constraints);
	}

	public Component add(
			Component component,
			int x,
			int y) {
		return this.add(component, x, y, 1, 1);
	}

	public <ComponentType extends Component> ComponentType add(
			ComponentBuilder<ComponentType> builder,
			GridBagConstraints constraints) {
		ComponentType component;

		component = builder.get();
		this.add(component, constraints);
		return component;
	}

	public <ComponentType extends Component> ComponentType add(
			ComponentBuilder<ComponentType> builder,
			int x,
			int y,
			int width,
			int height) {
		GridBagConstraints constraints;

		constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		return this.add(builder, constraints);
	}

	public <ComponentType extends Component> ComponentType add(
			ComponentBuilder<ComponentType> builder,
			int x,
			int y) {
		return this.add(builder, x, y, 1, 1);
	}

	public ConstraintsBuilder grid(int x, int y) {
		return new ConstraintsBuilder(x, y);
	}

	public GridPanel glueColumn(int x, float weight) {
		this.grid(x, 0).weightHorizontal(0F).add(Box.createVerticalGlue());
		return this;
	}

	public GridPanel glueColumn(int x) {
		return this.glueColumn(x, 1.0F);
	}

	public GridPanel glueRow(int y, float weight) {
		this.grid(0, y).weightVertical(weight).add(Box.createHorizontalGlue());
		return this;
	}

	public GridPanel glueRow(int y) {
		return this.glueRow(y, 1.0F);
	}

}
