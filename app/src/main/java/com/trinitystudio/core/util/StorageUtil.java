package com.trinitystudio.core.util;

import android.os.Environment;

import com.trinitystudio.core.GlobalConstant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageUtil {
	public static final String PATH = GlobalConstant.FOLDER_NAME_FOR_STORAGE;

	public static String getStoragePath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
		} else {
			return Environment.getRootDirectory().getAbsolutePath() + "/";
		}
	}

	public static String getDataPath(String fileName)
	{
		return Environment.getDataDirectory().getAbsolutePath() + "/" + PATH + fileName;
	}

	public static String getFolderPath() {
		return getStoragePath() + PATH;
	}

	public static String getFilePath(String fileName) {
		return getStoragePath() + PATH + fileName;
	}

	public static boolean isFileExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);
		return file.delete();
	}

	public static void saveFile(String path, byte[] bytes) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(path));
			bos.write(bytes);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
