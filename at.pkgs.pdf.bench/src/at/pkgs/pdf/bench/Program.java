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

import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;
import java.io.File;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;

public class Program {

	public static class Preference {

		private final Preferences storage;

		private int windowLeft;

		private int windowTop;

		private int windowWidth;

		private int windowHeight;

		private String settingFile;

		public Preference() {
			this.storage = Preferences.userNodeForPackage(this.getClass());
			this.windowLeft = this.storage.getInt("window.left", -1);
			this.windowTop = this.storage.getInt("window.top", -1);
			this.windowWidth = this.storage.getInt("window.width", -1);
			this.windowHeight = this.storage.getInt("window.height", -1);
			this.settingFile = this.storage.get("setting.file", "");
		}

		public Rectangle getWindowBounds() {
			if (this.windowLeft < 0 || this.windowTop < 0) return null;
			if (this.windowWidth < 0 || this.windowHeight < 0) return null;
			return new Rectangle(
					this.windowLeft,
					this.windowTop,
					this.windowWidth,
					this.windowHeight);
		}

		public void setWindowBounds(Rectangle value) {
			if (value.x < 0 || value.y < 0) return;
			if (value.width < 0 || value.height < 0) return;
			this.windowLeft = value.x;
			this.windowTop = value.y;
			this.windowWidth = value.width;
			this.windowHeight = value.height;
		}

		public File getSettingFile() {
			return new File(this.settingFile);
		}

		public void setSettingFile(File value) {
			this.settingFile = value.getAbsolutePath();
		}

		public void save() {
			this.storage.putInt("window.left", this.windowLeft);
			this.storage.putInt("window.top", this.windowTop);
			this.storage.putInt("window.width", this.windowWidth);
			this.storage.putInt("window.height", this.windowHeight);
			this.storage.put("setting.file", this.settingFile);
			try {
				this.storage.flush();
			}
			catch (BackingStoreException cause) {
				throw new RuntimeException(cause);
			}
		}

	}

	public static final Preference preference = new Preference();

	public static void main(String... arguments) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainFrame().setVisible(true);
			}

		});
	}

}
