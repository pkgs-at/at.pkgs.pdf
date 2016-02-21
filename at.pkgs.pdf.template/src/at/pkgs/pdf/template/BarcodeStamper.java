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

package at.pkgs.pdf.template;

import java.util.regex.Pattern;
import at.pkgs.barcode.Barcode;
import at.pkgs.barcode.OneDimensionalBarcode;
import at.pkgs.barcode.OneDimensionalImage;
import at.pkgs.pdf.builder.DocumentModel;

public abstract class BarcodeStamper {

	private static final Pattern PART_SEPARATOR = Pattern.compile("\\s*:\\s*");

	private static final Pattern VALUE_SEPARATOR = Pattern.compile("\\s*,\\s*");

	public static class OneDimensional extends BarcodeStamper {

		private final OneDimensionalBarcode barcode;

		private final DocumentModel.Horizontal horizontalAlign;

		private final float size;

		private final String color;

		public OneDimensional(
				OneDimensionalBarcode barcode,
				int page,
				float left,
				float top,
				float width,
				float height,
				DocumentModel.Horizontal horizontalAlign,
				float size,
				String color) {
			super(page, left, top, width, height);
			this.barcode = barcode;
			this.horizontalAlign = horizontalAlign;
			this.size = size;
			this.color = color;
		}

		public OneDimensional(
				OneDimensionalBarcode barcode,
				int page,
				float left,
				float top,
				float width,
				float height,
				String color) {
			this(barcode, page, left, top, width, height, null, 0F, color);
		}

		public OneDimensionalBarcode getBarcode() {
			return this.barcode;
		}

		@Override
		public void merge(float offsetLeft, float offsetTop, DocumentModel model, String value) {
			OneDimensionalImage image;
			float scale;
			float left;

			if (value == null) return;
			image = this.barcode.encode(value);
			scale = this.size;
			left = this.getLeft();
			if (this.horizontalAlign == null) {
				scale = this.getWidth() / (float)image.getSize();
			}
			else {
				float margin;

				margin = this.getWidth() - ((float)image.getSize() * scale);
				switch (this.horizontalAlign) {
				case CENTER :
					left += margin / 2F;
					break;
				case LEFT :
					break;
				case RIGHT :
					left += margin;
					break;
				}
			}
			for (OneDimensionalImage.Bar bar : image.getBars()) {
				model.add(new DocumentModel.Rectangle(
						this.getPage(),
						offsetLeft + left + ((float)bar.getPosition() * scale),
						offsetTop + this.getTop(),
						(float)bar.getLength() * scale,
						this.getHeight(),
						new DocumentModel.Fill(this.color)));
			}
		}

		/*
		 * FOTMAT:
		 * page: left, top: width, height: fill: color
		 * page: left, top: width, height: horizontalAlign: size
		 * page: left, top: width, height: horizontalAlign: size, color
		 */
		public static OneDimensional parse(OneDimensionalBarcode barcode, String layout) {
			try {
				String[] parts;
				String[] position;
				String[] size;

				parts = BarcodeStamper.PART_SEPARATOR.split(layout.trim());
				if (parts.length != 5) throw new IllegalArgumentException("Invalid part count");
				position = BarcodeStamper.VALUE_SEPARATOR.split(parts[1]);
				if (position.length != 2) throw new IllegalArgumentException("Invalid position value count");
				size = BarcodeStamper.VALUE_SEPARATOR.split(parts[2]);
				if (size.length != 2) throw new IllegalArgumentException("Invalid size value count");
				if (parts[3].equalsIgnoreCase("fill")) {
					return new OneDimensional(
							barcode,
							Integer.valueOf(parts[0]),
							Float.valueOf(position[0]),
							Float.valueOf(position[1]),
							Float.valueOf(size[0]),
							Float.valueOf(size[1]),
							parts[4]);
				}
				else {
					String[] style;

					style = BarcodeStamper.VALUE_SEPARATOR.split(parts[4]);
					if (style.length > 2) throw new IllegalArgumentException("Invalid size value count");
					return new OneDimensional(
							barcode,
							Integer.valueOf(parts[0]),
							Float.valueOf(position[0]),
							Float.valueOf(position[1]),
							Float.valueOf(size[0]),
							Float.valueOf(size[1]),
							DocumentModel.Horizontal.valueOf(parts[3].toUpperCase()),
							Float.valueOf(style[0]),
							style.length > 1 ? style[1] : "000000");
				}
			}
			catch (IllegalArgumentException cause) {
				throw new IllegalArgumentException("Invalid barcode format", cause);
			}
		}

	}

	private final int page;

	private final float left;

	private final float top;

	private final float width;

	private final float height;

	public BarcodeStamper(
			int page,
			float left,
			float top,
			float width,
			float height) {
		this.page = page;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	public int getPage() {
		return this.page;
	}

	public float getLeft() {
		return this.left;
	}

	public float getTop() {
		return this.top;
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	public abstract void merge(float offsetLeft, float offsetTop, DocumentModel model, String value);

	public void merge(DocumentModel model, String value) {
		this.merge(0F, 0F, model, value);
	}

	public static BarcodeStamper parse(Barcode barcode, String layout) {
		if (barcode instanceof OneDimensionalBarcode)
			return OneDimensional.parse((OneDimensionalBarcode)barcode, layout);
		throw new IllegalArgumentException("Invalid barcode object type");
	}

}
