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

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.io.File;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;

public class DocumentBuilder {

	private final PdfReader reader;

	private final PdfStamper stamper;

	private final Map<String, BaseFont> fonts;

	private int page;

	private Rectangle size;

	private PdfContentByte content;

	public DocumentBuilder(
			InputStream template,
			OutputStream destination)
					throws IOException, DocumentException {
		this.reader = new PdfReader(template);
		this.stamper = new PdfStamper(this.reader, destination);
		this.fonts = new HashMap<String, BaseFont>();
		this.page = 0;
		this.content = null;
	}

	public void font(DocumentModel.Font model)
			throws IOException, DocumentException {
		this.fonts.put(
				model.getName(),
				BaseFont.createFont(
						model.getFile(),
						model.getEncoding(),
						model.getEmbeded()));
	}

	public Font buildFont(String name, float size, String color) {
		Font font;

		font = new Font(this.fonts.get(name), size);
		if (color != null && color.length() == 3) {
			int value;

			value = Integer.parseInt(color, 16);
			font.setColor(
					(value & 0xF00) >> 4 | (value & 0xF00) >> 8,
					(value & 0x0F0) | (value & 0x0F0) >> 4,
					(value & 0x00F) << 4 | (value & 0x00F));
		}
		if (color != null && color.length() == 6) {
			int value;

			value = Integer.parseInt(color, 16);
			font.setColor(
					(value & 0xFF0000) >> 16,
					(value & 0x00FF00) >> 8,
					(value & 0x0000FF));
		}
		return font;
	}

	protected void setPage(int page) {
		if (this.page == page) return;
		this.size = this.reader.getCropBox(page);
		this.content = this.stamper.getOverContent(page);
		this.page = page;
	}

	protected Rectangle getSize(int page) {
		this.setPage(page);
		return this.size;
	}

	protected PdfContentByte getContent(int page) {
		this.setPage(page);
		return this.content;
	}

	public void text(DocumentModel.Text model)
			throws IOException, DocumentException {
		float height;
		Font font;
		ColumnText column;

		height = this.getSize(model.getPage()).getHeight();
		font = this.buildFont(
				model.getFont(),
				model.getSize(),
				model.getColor());
		column = new ColumnText(this.getContent(model.getPage()));
		column.setSimpleColumn(
				model.getLeft(),
				height - model.getTop() - model.getHeight(),
				model.getLeft() + model.getWidth(),
				height - model.getTop());
		column.setLeading(model.getLeading());
		if (model.getHorizontal() != null) {
			switch (model.getHorizontal()) {
			case LEFT :
				column.setAlignment(Phrase.ALIGN_LEFT);
				break;
			case CENTER :
				column.setAlignment(Phrase.ALIGN_CENTER);
				break;
			case RIGHT :
				column.setAlignment(Phrase.ALIGN_RIGHT);
				break;
			}
		}
		column.addText(new Phrase(model.getValue(), font));
		column.go();
	}

	public void value(DocumentModel.Value value)
			throws IOException, DocumentException {
		if (value instanceof DocumentModel.Text) {
			this.text((DocumentModel.Text)value);
			return;
		}
		throw new UnsupportedOperationException();
	}

	public void merge(DocumentModel model)
			throws IOException, DocumentException {
		if (model.getFonts() != null) {
			for (DocumentModel.Font font : model.getFonts()) this.font(font);
		}
		if (model.getValues() != null) {
			List<DocumentModel.Value> values;

			values = new ArrayList<DocumentModel.Value>(model.getValues());
			Collections.sort(values, new Comparator<DocumentModel.Value>() {

				@Override
				public int compare(
						DocumentModel.Value left,
						DocumentModel.Value right) {
					return left.getPage() - right.getPage();
				}

			});
			for (DocumentModel.Value value : values) this.value(value);
		}
	}

	public void close()
			throws IOException, DocumentException {
		this.stamper.close();
	}

	public static void merge(
			InputStream template,
			OutputStream destination,
			DocumentModel... models)
					throws IOException, DocumentException {
		DocumentBuilder builder;

		builder = new DocumentBuilder(template, destination);
		for (DocumentModel model : models) builder.merge(model);
		builder.close();
	}

	public static void merge(
			File template,
			File destination,
			DocumentModel... models)
					throws IOException, DocumentException {
		try (
				InputStream input = new BufferedInputStream(
						new FileInputStream(
								template));
				OutputStream output = new BufferedOutputStream(
						new FileOutputStream(
								destination))) {
			DocumentBuilder.merge(input, output, models);
		}
	}

	public static void main(String... arguments) {
		if (arguments.length != 3) {
			System.err.println("arguments: template destination model");
			System.exit(1);
			return;
		}
		try {
			DocumentBuilder.merge(
					new File(arguments[0]),
					new File(arguments[1]),
					DocumentModel.parse(new File(arguments[2])));
			System.exit(0);
			return;
		}
		catch (Exception cause) {
			System.err.println("failed on build document");
			cause.printStackTrace(System.err);
			System.exit(2);
			return;
		}
	}

}
