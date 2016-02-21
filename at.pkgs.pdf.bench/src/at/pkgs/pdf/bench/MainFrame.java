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

package at.pkgs.pdf.bench;

import java.util.Properties;
import java.io.File;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import at.pkgs.swing.ActionDispatcher;
import at.pkgs.swing.ComponentBuilder;
import at.pkgs.swing.BoxXAxisPanel;
import at.pkgs.swing.GridPanel;
import at.pkgs.swing.FileInputControl;
import at.pkgs.pdf.BuildLauncher;
import at.pkgs.pdf.builder.DocumentModel;

public class MainFrame extends JFrame {

	public static enum Command implements ActionDispatcher.Entity<MainFrame> {

		SETTING_LOAD(new ActionDispatcher.Handler<MainFrame>() {

			@Override
			public void action(MainFrame component, ActionEvent event) {
				component.actSettingLoad();
			}

		}),

		SETTING_SAVE(new ActionDispatcher.Handler<MainFrame>() {

			@Override
			public void action(MainFrame component, ActionEvent event) {
				component.actSettingSave();
			}

		}),

		ACTION_MERGE(new ActionDispatcher.Handler<MainFrame>() {

			@Override
			public void action(MainFrame component, ActionEvent event) {
				component.actActionMerge();
			}

		});

		private final ActionDispatcher.Handler<MainFrame> handler;

		private Command(ActionDispatcher.Handler<MainFrame> handler) {
			this.handler = handler;
		}

		@Override
		public ActionDispatcher.Handler<MainFrame> handler() {
			return this.handler;
		}

	}

	private static final long serialVersionUID = 1L;

	private final FileInputControl binaryFileInput;

	private final FileInputControl templateFileInput;

	private final FileInputControl fieldFileInput;

	private final FileInputControl scriptFileInput;

	private final FileInputControl outputFileInput;

	private final JTextField commandTextField;

	private final JFileChooser settingFileChooser;

	public MainFrame() {
		ActionDispatcher<MainFrame> actions;
		File base;
		GridPanel main;
		BoxXAxisPanel footer;

		actions = new ActionDispatcher<MainFrame>(this, Command.values());
		base = new File(".");
		this.binaryFileInput = new FileInputControl(this, FileInputControl.Action.OPEN) { {
			this.getFileChooser().setCurrentDirectory(base);
		} };
		this.templateFileInput = new FileInputControl(this, FileInputControl.Action.OPEN) { {
			this.getFileChooser().setCurrentDirectory(base);
		} };
		this.fieldFileInput = new FileInputControl(this, FileInputControl.Action.OPEN) { {
			this.getFileChooser().setCurrentDirectory(base);
		} };
		this.scriptFileInput = new FileInputControl(this, FileInputControl.Action.OPEN) { {
			this.getFileChooser().setCurrentDirectory(base);
		} };
		this.outputFileInput = new FileInputControl(this, FileInputControl.Action.SAVE) { {
			this.getFileChooser().setCurrentDirectory(base);
		} };
		this.commandTextField = new JTextField();
		this.settingFileChooser = (new ComponentBuilder<JFileChooser>(new JFileChooser(base)) { {
			this.component.setSelectedFile(Program.preference.getSettingFile());
		} }).get();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(MainFrame.class.getPackage().getName());
		if (Program.preference.getWindowBounds() != null) {
			this.setBounds(Program.preference.getWindowBounds());
		}
		else {
			this.setSize(480, 240);
			this.setLocationRelativeTo(null);
		}
		this.setLayout(new BorderLayout());
		main = new GridPanel();
		main.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
		main.grid(0, 0).anchorEast().add(new JLabel("PDF build binary: "));
		main.grid(1, 0).weightHorizontal(1.0F).fillHorizontal().add(this.binaryFileInput.getTextField());
		main.grid(2, 0).fillHorizontal().add(this.binaryFileInput.getButton());
		main.grid(0, 1).anchorEast().add(new JLabel("PDF template: "));
		main.grid(1, 1).weightHorizontal(1.0F).fillHorizontal().add(this.templateFileInput.getTextField());
		main.grid(2, 1).fillHorizontal().add(this.templateFileInput.getButton());
		main.grid(0, 2).anchorEast().add(new JLabel("Field definition: "));
		main.grid(1, 2).weightHorizontal(1.0F).fillHorizontal().add(this.fieldFileInput.getTextField());
		main.grid(2, 2).fillHorizontal().add(this.fieldFileInput.getButton());
		main.grid(0, 3).anchorEast().add(new JLabel("Run script: "));
		main.grid(1, 3).weightHorizontal(1.0F).fillHorizontal().add(this.scriptFileInput.getTextField());
		main.grid(2, 3).fillHorizontal().add(this.scriptFileInput.getButton());
		main.grid(0, 4).anchorEast().add(new JLabel("PDF output: "));
		main.grid(1, 4).weightHorizontal(1.0F).fillHorizontal().add(this.outputFileInput.getTextField());
		main.grid(2, 4).fillHorizontal().add(this.outputFileInput.getButton());
		main.grid(0, 5).anchorEast().add(new JLabel("Command: "));
		main.grid(1, 5).width(2).fillHorizontal().add(this.commandTextField);
		main.glueRow(6);
		main.setMinimumSize(new Dimension(320, main.getPreferredSize().height));
		footer = new BoxXAxisPanel();
		footer.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
		footer.setAlignmentY(BoxXAxisPanel.CENTER_ALIGNMENT);
		footer.glue();
		footer.add(new JLabel("Setting:"));
		footer.space(4);
		footer.add(new ComponentBuilder<JButton>(new JButton()) { {
			this.component.setText("Load");
			this.component.setActionCommand(Command.SETTING_LOAD.name());
			this.component.addActionListener(actions);
		} });
		footer.add(new ComponentBuilder<JButton>(new JButton()) { {
			this.component.setText("Save");
			this.component.setActionCommand(Command.SETTING_SAVE.name());
			this.component.addActionListener(actions);
		} });
		footer.glue();
		footer.add(new JLabel("Action:"));
		footer.space(4);
		footer.add(new ComponentBuilder<JButton>(new JButton()) { {
			this.component.setText("Merge");
			this.component.setActionCommand(Command.ACTION_MERGE.name());
			this.component.addActionListener(actions);
		} });
		footer.glue();
		this.add(main, BorderLayout.CENTER);
		this.add(footer, BorderLayout.SOUTH);
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent event) {
				int height;

				height = MainFrame.this.getSize().height;
				height -= main.getSize().height;
				height += main.getPreferredSize().height;
				MainFrame.this.setMinimumSize(new Dimension(320, height));
			}

			@Override
			public void componentResized(ComponentEvent event) {
				if (MainFrame.this.getExtendedState() != MainFrame.NORMAL) return;
				Program.preference.setWindowBounds(MainFrame.this.getBounds());
			}

			@Override
			public void componentMoved(ComponentEvent event) {
				if (MainFrame.this.getExtendedState() != MainFrame.NORMAL) return;
				Program.preference.setWindowBounds(MainFrame.this.getBounds());
			}

		});
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent event) {
				Program.preference.save();
			}

		});
	}

	public void error(Throwable cause) {
		cause.printStackTrace(System.err);
		JOptionPane.showMessageDialog(
				this,
				"See standard error (console) for more details.",
				"Error occured",
				JOptionPane.ERROR_MESSAGE);
	}

	public void load(File file) {
		Properties properties;

		properties = new Properties();
		try {
			InputStream input;

			input = new BufferedInputStream(new FileInputStream(file));
			properties.loadFromXML(input);
			input.close();
		}
		catch (IOException cause) {
			this.error(cause);
			return;
		}
		this.binaryFileInput.getTextField().setText(properties.getProperty("binary.file", ""));
		this.templateFileInput.getTextField().setText(properties.getProperty("template.file", ""));
		this.fieldFileInput.getTextField().setText(properties.getProperty("field.file", ""));
		this.scriptFileInput.getTextField().setText(properties.getProperty("script.file", ""));
		this.outputFileInput.getTextField().setText(properties.getProperty("output.file", ""));
		this.commandTextField.setText(properties.getProperty("command", ""));
	}

	public void save(File file) {
		Properties properties;

		properties = new Properties();
		properties.setProperty("binary.file", this.binaryFileInput.getTextField().getText());
		properties.setProperty("template.file", this.templateFileInput.getTextField().getText());
		properties.setProperty("field.file", this.fieldFileInput.getTextField().getText());
		properties.setProperty("script.file", this.scriptFileInput.getTextField().getText());
		properties.setProperty("output.file", this.outputFileInput.getTextField().getText());
		properties.setProperty("command", this.commandTextField.getText());
		try {
			OutputStream output;

			output = new BufferedOutputStream(new FileOutputStream(file));
			properties.storeToXML(output, null);
			output.close();
		}
		catch (IOException cause) {
			this.error(cause);
			return;
		}
	}

	public void actSettingLoad() {
		int result;
		File file;

		result = this.settingFileChooser.showOpenDialog(this);
		if (result != JFileChooser.APPROVE_OPTION) return;
		file = this.settingFileChooser.getSelectedFile();
		if (file == null || !file.isFile() || !file.canRead()) {
			JOptionPane.showMessageDialog(
					this,
					"Cannot read file: " + file.getAbsolutePath(),
					"Invalid file selection",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.load(file);
		Program.preference.setSettingFile(file);
	}

	public void actSettingSave() {
		int result;
		File file;

		result = this.settingFileChooser.showSaveDialog(this);
		if (result != JFileChooser.APPROVE_OPTION) return;
		file = this.settingFileChooser.getSelectedFile();
		if (file == null || file.isDirectory() || file.isFile() && !file.canWrite()) {
			JOptionPane.showMessageDialog(
					this,
					"Cannot write file: " + file.getAbsolutePath(),
					"Invalid file selection",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (file.exists()) {
			result = JOptionPane.showConfirmDialog(
					this,
					"File already exists: " + file.getAbsolutePath(),
					"Overwrite?",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result != JOptionPane.OK_OPTION) return;
		}
		this.save(file);
		Program.preference.setSettingFile(file);
	}

	public void actActionMerge() {
		Properties fields;
		ScriptEngine engine;
		Reader script;
		DocumentModel model;
		BuildLauncher launcher;

		fields = new Properties();
		try {
			InputStream input;

			input = new BufferedInputStream(new FileInputStream(this.fieldFileInput.getTextField().getText()));
			fields.loadFromXML(input);
			input.close();
		}
		catch (IOException cause) {
			this.error(cause);
			return;
		}
		engine = new ScriptEngineManager().getEngineByName("nashorn");
		script = null;
		try {
			script = new InputStreamReader(
					new BufferedInputStream(
							new FileInputStream(
									this.scriptFileInput.getTextField().getText())),
					StandardCharsets.UTF_8);
			engine.eval(script);
			model = (DocumentModel)((Invocable)engine).invokeFunction("main", fields);
			script.close();
		}
		catch (Throwable cause) {
			this.error(cause);
			return;
		}
		finally {
			if (script != null) {
				try {
					script.close();
				}
				catch (IOException ignored) {
					// do nothing
				}
			}
		}
		launcher = new BuildLauncher(
				new File(this.binaryFileInput.getTextField().getText()));
		try {
			launcher.build(
					new File(this.outputFileInput.getTextField().getText()),
					new File(this.templateFileInput.getTextField().getText()),
					model);
		}
		catch (IOException cause) {
			this.error(cause);
			return;
		}
		if (this.commandTextField.getText().isEmpty()) return;
		try {
			new ProcessBuilder(
					this.commandTextField.getText(),
					this.outputFileInput.getTextField().getText())
				.inheritIO()
				.start();
		}
		catch (IOException cause) {
			this.error(cause);
			return;
		}
	}

}
