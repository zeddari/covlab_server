package com.axilog.cov.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

	public static File createTmpFile(String file, String ext) {
		String tmpdir = System.getProperty("java.io.tmpdir");

        try {

            // Create an temporary file
            Path temp = Files.createTempFile(file, ext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
}
