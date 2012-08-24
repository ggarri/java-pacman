package ptp.pacman.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExtensionFileMap extends FileFilter {

	@Override
	public boolean accept(File file) {
		String filename = file.getName();
        return filename.endsWith(".map");
	}

	@Override
	public String getDescription() {
		return "*.map";
	}

}
