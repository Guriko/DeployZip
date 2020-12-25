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
 * �w��t�H���_����Zip�t�@�C����W�J����B
 * @author heisei-20
 *
 */
public class DeployZip {

	// Zip�t�@�C���̓W�J��
	private File destinationDir;

	// listFiles���g�p���ăt�@�C���ꗗ���擾
	private File[] list;

	// zip�t�@�C�������������̃t���O
	private boolean flg = false;

	/**
	 * Zip�t�@�C�����������A���̃t�@�C�����Ńt�H���_���쐬��
	 * i�W�J�����t�H���_�̒���Zip�t�@�C����W�J����
	 * i�����P��zip�t�@�C�����m�F�������̂�����
	 * i����2�Ɍ����������Ă���t�H���_������
	 * @param fileName
	 * @param dir
	 * @throws IOException
	 */
	public void ifZip(File fileName, File dir) throws IOException {

		if (fileName.getName().contains(".zip") || fileName.getName().contains(".tar.gz")) {

			//�@�W�J����zip�t�@�C�����擾
			String fileZip = dir + "//" + fileName.getName();

			// Zip�t�@�C�����Ńt�H���_���쐬
			File crFile = new File(dir + "//" + this.getPreffix(fileName.getName()));
			crFile.mkdir();

			destinationDir = crFile;

			byte[] buffer = new byte[1024];
			ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
			ZipEntry zipEntry = zis.getNextEntry();

			while (zipEntry != null) {
				if (zipEntry.getName().contains(".zip")) {
					// zip�t�@�C����zip�t�@�C���̒��ɂ���Ƃ�
					// ����zip�t�@�C�������ق���
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
			// �������Ȃ�
		}
		

	}

	private void callZip(boolean flg, File[] list, File dir) throws IOException {
		if (flg == true) {
			for (int i = 0; i < list.length; i++) {
				System.out.println("OOOO");
				//�@�g���q��.zip�̂��̂𒊏o
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

	//�@�g���q�O�̃t�@�C�����𒊏o����
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
