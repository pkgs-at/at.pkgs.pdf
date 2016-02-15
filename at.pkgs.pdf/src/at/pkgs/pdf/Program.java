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

package at.pkgs.pdf;

import java.util.Arrays;
import java.io.File;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.file.Files;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.tools.ConcatPdf;
import com.lowagie.tools.SplitPdf;
import at.pkgs.pdf.builder.DocumentBuilder;

public class Program {

	public static class NullPrintStream extends PrintStream {

		public NullPrintStream() {
			super(new OutputStream() {

				@Override
				public void write(int value) {
					// do nothing
				}

			});
		}

	}

	private static final PrintStream NULL_PRINT_WRITER = new PrintStream(
			new OutputStream() {

				@Override
				public void write(int value) {
					// do nothing
				}

			});

	public static void usage(PrintStream output) {
		output.println("usage:");
		output.println("[-build] template destination model");
		output.println("-concatenate files... destination");
		output.println("-split source file1 file2 page");
		output.println("-extract source destination page length");
	}

	public static void copy(String source, String destination) throws IOException {
		try (OutputStream output = new BufferedOutputStream(new FileOutputStream(destination))) {
			Files.copy(new File(source).toPath(), output);
		}
	}

	public static void build(String... arguments) {
		if (arguments.length != 3) {
			Program.usage(System.err);
			System.exit(1);
			return;
		}
		DocumentBuilder.main(arguments);
	}

	public static void concatenate(String... arguments) {
		PrintStream out;

		if (arguments.length < 2) {
			Program.usage(System.err);
			System.exit(1);
			return;
		}
		out = System.out;
		System.setOut(Program.NULL_PRINT_WRITER);
		ConcatPdf.main(arguments);
		System.setOut(out);
	}

	public static void split(String... arguments) {
		PrintStream out;

		if (arguments.length != 4) {
			Program.usage(System.err);
			System.exit(1);
			return;
		}
		out = System.out;
		System.setOut(Program.NULL_PRINT_WRITER);
		SplitPdf.main(arguments);
		System.setOut(out);
	}

	public static void extract(String... arguments) throws IOException {
		String source;
		String destination;
		int page;
		int length;

		if (arguments.length != 4) {
			Program.usage(System.err);
			System.exit(1);
			return;
		}
		source = arguments[0];
		destination = arguments[1];
		page = Integer.parseInt(arguments[2], 10);
		length = Integer.parseInt(arguments[3], 10);
		try {
			PdfReader reader;
			PdfWriter writer;
			Document document;
			PdfContentByte content;
			int index;

			reader = new PdfReader(source);
			document = new Document(reader.getPageSizeWithRotation(page));
			writer = PdfWriter.getInstance(document, new BufferedOutputStream(new FileOutputStream(destination)));
			document.open();
			content = writer.getDirectContent();
			for (index = 0; index < length; index ++) {
				document.setPageSize(reader.getPageSizeWithRotation(page + index));
				document.newPage();
				switch (reader.getPageRotation(page + index)) {
				case 90 :
				case 270 :
					content.addTemplate(
							writer.getImportedPage(reader, page + index),
							0F,
							-1F,
							1F,
							0F,
							0F,
							reader.getPageSizeWithRotation(page + index).getHeight());
					break;
				default :
					content.addTemplate(
							writer.getImportedPage(reader, page + index),
							1F,
							0F,
							0F,
							1F,
							0F,
							0F);
					break;
				}
			}
			document.close();
			reader.close();
		}
		catch (DocumentException cause) {
			throw new IOException(cause);
		}
	}

	public static void main(String... arguments) throws IOException {
		String[] parameters;

		if (arguments.length <= 0) {
			Program.usage(System.err);
			System.exit(1);
			return;
		}
		if (arguments[0].charAt(0) != '-') {
			Program.build(arguments);
			System.exit(0);
			return;
		}
		parameters = Arrays.asList(arguments)
				.subList(1, arguments.length)
				.toArray(new String[arguments.length - 1]);
		switch (arguments[0].substring(1)) {
		case "build" :
			Program.build(parameters);
			break;
		case "concatenate" :
			Program.concatenate(parameters);
			break;
		case "split" :
			Program.split(parameters);
			break;
		case "extract" :
			Program.extract(parameters);
			break;
		default :
			Program.usage(System.err);
			System.exit(1);
			return;
		}
		System.exit(0);
	}

}
