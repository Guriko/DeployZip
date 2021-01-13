package com.example.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewDeployZip {
	
	public org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	public void runLog(IOException error) {
		logger.error("�G���[���e:" + error);
	}

	public void zipIf(File Folder) {
		
		// Zip�t�@�C���̓W�J��
		File destinationDir;

		//listFiles���g�p���ăt�@�C���ꗗ���擾
		File[] list = Folder.listFiles();

		for(int i=0; i<list.length; i++) {

			if(list[i].isDirectory()) {
				System.out.println(list[i]);
				File seaDir = new File(Folder + "//" + list[i].getName());
				this.zipIf(seaDir);
			}else {

				if (list[i].getName().contains(".zip")){

					//�@�W�J����zip�t�@�C��
					String fileZip = Folder + "//" + list[i].getName();

					// Zip�t�@�C�����Ńt�H���_���쐬
					File crFile = new File(Folder + "//" + this.getPreffix(list[i].getName()));
					crFile.mkdir();

					// Zip�t�@�C���̓W�J��
					destinationDir = crFile;

					try(FileInputStream fis = new FileInputStream(fileZip);
							BufferedInputStream bis = new BufferedInputStream(fis);
							ZipInputStream zis = new ZipInputStream(bis);
							) {
						ZipEntry zipEntry;
						while((zipEntry = zis.getNextEntry()) != null) {

							if(zipEntry.isDirectory()) {
								System.out.println("�f�B���N�g���ł�" + zipEntry);
								File getZipEnt = new File(zipEntry.getName());
								File crDir = new File(destinationDir + "//" + getZipEnt.getName());
								crDir.mkdir();

								destinationDir = crDir;
								System.out.println("�쐬���������܂�" + destinationDir);
								this.zipIf(destinationDir);
							}
							else {

								File getZipEnt = new File(zipEntry.getName());

								try(FileOutputStream fos = new FileOutputStream(destinationDir + "//" + getZipEnt.getName());
										BufferedOutputStream bos = new BufferedOutputStream(fos);
										){
									byte[] data = new byte[1024];
									int count = 0;
									while((count = zis.read(data)) != -1){
										bos.write(data,0,count);
									}
								}
							}
						}

//						this.zipIf(destinationDir);

					} catch (IOException e) {
						this.runLog(e);
						
						//�@�G���[���ɃG���[�t�H���_�쐬
						File errFolder = new File(Folder + "//errorFolder");
						if(!errFolder.exists()) {
							errFolder.mkdir();
						}

						//�@�G���[��������zip�t�@�C�����G���[�t�H���_�Ɉړ�
						File from = new File(fileZip);
						File to = new File(errFolder + "//" + list[i].getName());
						this.moveFile(from,to);

					} finally {
						File delZip = new File(fileZip);
						delZip.delete();
					}
				}
			}
		}
	}


	


	//File�̈ړ����\�b�h
	private void moveFile(File from,File to) {
		from.renameTo(to);
	}

	//�@�g���q�O�̃t�@�C�����𒊏o����
	private String getPreffix(String fileName) {
		if (fileName == null) {
			return null;
		}

		if (fileName.indexOf(".") > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}

		return fileName;
	}
}

