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

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import com.lowagie.tools.ConcatPdf;
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
	}

	public static void build(String... arguments) {
		DocumentBuilder.main(arguments);
	}

	public static void concatenate(String... arguments) {
		PrintStream out;

		out = System.out;
		System.setOut(Program.NULL_PRINT_WRITER);
		ConcatPdf.main(arguments);
		System.setOut(out);
	}

	public static void main(String... arguments) {
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
			Program.build(parameters);
			break;
		default :
			Program.usage(System.err);
			System.exit(1);
			return;
		}
		System.exit(0);
	}

}
