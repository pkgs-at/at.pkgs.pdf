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
		int result;

		temporal = File.createTempFile(this.getClass().getName() + '.', ".xml");
		try {
			JAXB.marshal(model, temporal);
			result = this.launch(
					"-build",
					template.getAbsolutePath(),
					output.getAbsolutePath(),
					temporal.getAbsolutePath());
		}
		finally {
			temporal.delete();
		}
		if (result != 0)
			throw new IOException(
					String.format(
							"failed on build document" +
							" (process exit with %d)",
							result));
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

}
