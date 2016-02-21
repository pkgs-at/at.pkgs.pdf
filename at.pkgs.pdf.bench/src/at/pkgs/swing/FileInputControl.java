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

import java.io.File;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class FileInputControl implements ActionListener {

	public static enum Action {

		OPEN,

		SAVE;

	}

	private final Component parent;

	private final Action action;

	private final JTextField textField;

	private final JButton button;

	private final JFileChooser fileChooser;

	public FileInputControl(Component parent, Action action, String text) {
		this.parent = parent;
		this.action = action;
		this.textField = new JTextField();
		this.button = (new ComponentBuilder<JButton>(new JButton(text)) { {
			Dimension size;

			size = new Dimension(
					this.component.getPreferredSize().width,
					FileInputControl.this.textField.getPreferredSize().height);
			this.component.setMinimumSize(size);
			this.component.setMaximumSize(size);
			this.component.setPreferredSize(size);
			this.component.addActionListener(FileInputControl.this);
		} }).get();
		this.fileChooser = new JFileChooser();
	}

	public FileInputControl(Component parent, Action action) {
		this(parent, action, "...");
	}

	public Component getParent() {
		return this.parent;
	}

	public Action getAction() {
		return this.action;
	}

	public JTextField getTextField() {
		return this.textField;
	}

	public JButton getButton() {
		return this.button;
	}

	public JFileChooser getFileChooser() {
		return this.fileChooser;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int result;
		File file;

		result = JFileChooser.CANCEL_OPTION;
		if (!this.textField.getText().isEmpty()) {
			File current;

			current = new File(this.textField.getText());
			this.fileChooser.setCurrentDirectory(current.isFile() ? current.getParentFile() : current);
		}
		switch (this.action) {
		case OPEN :
			result = this.fileChooser.showOpenDialog(this.parent);
			break;
		case SAVE :
			result = this.fileChooser.showSaveDialog(this.parent);
			break;
		}
		if (result != JFileChooser.APPROVE_OPTION) return;
		file = this.fileChooser.getSelectedFile();
		this.textField.setText(file == null ? "" : file.toString());
	}

}
