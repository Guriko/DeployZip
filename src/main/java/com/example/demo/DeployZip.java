package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 指定フォルダ内のZipファイルを展開する。
 * @author heisei-20
 *
 */
public class DeployZip {

	// Zipファイルの展開先
	private File destinationDir;

	// listFilesを使用してファイル一覧を取得
	private File[] list;

	// zipファイルがあったかのフラグ
	private boolean flg = false;

	/**
	 * Zipファイルだった時、そのファイル名でフォルダを作成し
	 * i展開したフォルダの中にZipファイルを展開する
	 * i引数１にzipファイルか確認したものを入れる
	 * i引数2に検索をかけているフォルダを入れる
	 * @param fileName
	 * @param dir
	 * @throws IOException
	 */
	public void ifZip(File fileName, File dir) throws IOException {

		if (fileName.getName().contains(".zip") || fileName.getName().contains(".tar.gz")) {

			//　展開するzipファイルを取得
			String fileZip = dir + "//" + fileName.getName();

			// Zipファイル名でフォルダを作成
			File crFile = new File(dir + "//" + this.getPreffix(fileName.getName()));
			crFile.mkdir();

			destinationDir = crFile;

			byte[] buffer = new byte[1024];
			ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
			ZipEntry zipEntry = zis.getNextEntry();

			while (zipEntry != null) {
				if (zipEntry.getName().contains(".zip")) {
					// zipファイルがzipファイルの中にあるとき
					// そのzipファイル名がほしい
					System.out.println(zipEntry.getName());
					System.out.println(destinationDir);
					list = destinationDir.listFiles();
					System.out.println(list.length);
					flg = true;
				}

				File newFile = this.newFile(destinationDir, zipEntry);
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			
			if (flg == true) {
				this.callZip(flg, list, destinationDir);
			}
			
		} else {
			// 何もしない
		}
		

	}

	private void callZip(boolean flg, File[] list, File dir) throws IOException {
		if (flg == true) {
			for (int i = 0; i < list.length; i++) {
				System.out.println("OOOO");
				//　拡張子が.zipのものを抽出
				ifZip(list[i], destinationDir);
			}
			// list.removeAll(list);
			flg = false;
		}
	}

	private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	//　拡張子前のファイル名を抽出する
	private String getPreffix(String fileName) {
		if (fileName == null)
			return null;
		// int point = fileName.lastIndexOf(".");
		// if (point != -1) {
		// return fileName.substring(0, point);
		// }
		if (fileName.indexOf(".") > 0)
			fileName = fileName.substring(0, fileName.lastIndexOf("."));

		return fileName;
	}
}
