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

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXB;
import at.pkgs.pdf.builder.DocumentModel;

public class BuildLauncher {

	private static final String[] STRINGS = new String[0];

	private final File java;

	private final File binary;

	public BuildLauncher(
			File binary) {
		this.java = new File(System.getProperty("java.home"), "bin/java");
		this.binary = binary;
	}

	protected int launch(
			String... arguments)
					throws IOException {
		List<String> parameters;
		ProcessBuilder builder;
		Process process;
		Integer result;

		parameters = new ArrayList<String>();
		parameters.add(this.java.getAbsolutePath());
		parameters.add("-jar");
		parameters.add(this.binary.getAbsolutePath());
		for (String argument : arguments) parameters.add(argument);
		builder = new ProcessBuilder(parameters.toArray(BuildLauncher.STRINGS));
		builder.inheritIO();
		process = builder.start();
		result = null;
		while (result == null) {
			try {
				result = process.waitFor();
			}
			catch (InterruptedException ignored) {
				// do nothing
			}
		}
		return result;
	}

	protected int launch(
			List<String> arguments)
					throws IOException {
		return this.launch(arguments.toArray(BuildLauncher.STRINGS));
	}

	public void build(
			File output,
			File template,
			DocumentModel model)
					throws IOException {
		File temporal;

		temporal = File.createTempFile(this.getClass().getName() + '.', ".xml");
		try {
			int result;

			JAXB.marshal(model, temporal);
			result = this.launch(
					"-build",
					template.getAbsolutePath(),
					output.getAbsolutePath(),
					temporal.getAbsolutePath());
			if (result != 0)
				throw new IOException(
						String.format(
								"failed on build document" +
								" (process exit with %d)",
								result));
		}
		finally {
			temporal.delete();
		}
	}

	public void concatenate(
			File output,
			Collection<File> sources)
					throws IOException {
		List<String> arguments;
		int result;

		arguments = new ArrayList<String>();
		arguments.add("-concatenate");
		for (File file : sources) arguments.add(file.getAbsolutePath());
		arguments.add(output.getAbsolutePath());
		result = this.launch(arguments);
		if (result != 0)
			throw new IOException(
					String.format(
							"failed on concatenate documents" +
							" (process exit with %d)",
							result));
	}

	public void concatenate(
			File output,
			File... sources)
					throws IOException {
		this.concatenate(output, Arrays.asList(sources));
	}

	public void split(
			File firstHalf,
			File lastHalf,
			File source,
			int page)
					throws IOException {
		File temporal;

		if (firstHalf == null && lastHalf == null)
			throw new IllegalArgumentException("firstHalf and lastHalf are null");
		temporal = null;
		if (firstHalf == null) firstHalf = temporal = File.createTempFile(this.getClass().getName() + '.', ".pdf");
		if (lastHalf == null) lastHalf = temporal = File.createTempFile(this.getClass().getName() + '.', ".pdf");
		try {
			int result;

			result = this.launch(
					"-split",
					source.getAbsolutePath(),
					firstHalf.getAbsolutePath(),
					lastHalf.getAbsolutePath(),
					Integer.toString(page, 10));
			if (result != 0)
				throw new IOException(
						String.format(
								"failed on split documents" +
								" (process exit with %d)",
								result));
		}
		finally {
			if (temporal != null) temporal.delete();
		}
	}

	public void extract(
			File output,
			File source,
			int page,
			int length)
					throws IOException {
		int result;

		result = this.launch(
				"-extract",
				source.getAbsolutePath(),
				output.getAbsolutePath(),
				Integer.toString(page, 10),
				Integer.toString(length, 10));
		if (result != 0)
			throw new IOException(
					String.format(
							"failed on extract documents" +
							" (process exit with %d)",
							result));
	}

}
