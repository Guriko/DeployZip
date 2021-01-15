package com.example.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.codehaus.plexus.archiver.tar.TarEntry;
import org.codehaus.plexus.archiver.tar.TarInputStream;
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
					this.zipDep(destinationDir, fileZip,list[i].getName(),Folder);


				}else if(list[i].getName().endsWith("tar.gz")) {
					System.out.println(list[i].getName());

					//�@�W�J����tar.gz�t�@�C��
					String fileGz = Folder + "//" + list[i].getName();

					File crFile = new File(Folder + "//" + this.getPreffix(list[i].getName()));
					crFile.mkdir();

					// tar.gz�t�@�C���̓W�J��
					destinationDir = crFile;
					this.tarDep(destinationDir,fileGz,list[i].getName(),Folder);
				}
			}
		}
	}



	//�@�G���[�t�H���_�ɑޔ�������
	private void moveErr(File err,String fileZip,File toFile) {
		if(!err.exists()) {
			err.mkdir();
		}

		//�@�G���[��������zip�t�@�C�����G���[�t�H���_�Ɉړ�
		File from = new File(fileZip);
		File to = new File(err + "//" + toFile);
		this.moveFile(from,to);
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

	public void zipDep(File destinationDir,String fileZip,String list,File Folder) {
		try(FileInputStream fis = new FileInputStream(fileZip);
				BufferedInputStream bis = new BufferedInputStream(fis);
				ZipInputStream zis = new ZipInputStream(bis);
				) {
			ZipEntry zipEntry;
			while((zipEntry = zis.getNextEntry()) != null) {

				if(zipEntry.isDirectory()) {

					System.out.println("�f�B���N�g���ł�" + zipEntry.getName());
					File getZipEnt = new File(zipEntry.getName());
					File crDir = new File(destinationDir,getZipEnt.getName());
					crDir.mkdir();

					destinationDir = crDir;
					this.zipIf(destinationDir);

				} else {
					System.out.println("�t�@�C���ł�" + zipEntry);
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
		} catch (IOException e) {
			this.runLog(e);

			//�@�G���[���ɃG���[�t�H���_�쐬
			File errFolder = new File(Folder + "//errorFolder");
			File to = new File(errFolder + "//" + list);
			this.moveErr(errFolder, fileZip, to);

		} finally {
			File delZip = new File(fileZip);
			delZip.delete();
		}
	}
	
	//tar.gz�̉�
	public void tarDep(File destinationDir,String fileGz,String list,File Folder) {
		try(FileInputStream fis = new FileInputStream(fileGz);
				BufferedInputStream bis = new BufferedInputStream(fis);
				TarInputStream tin = new TarInputStream(new GZIPInputStream(bis));
				) {
			TarEntry tarEnt;
			while ((tarEnt = tin.getNextEntry()) != null) {
				if(tarEnt.isDirectory()) {

					System.out.println("�f�B���N�g���ł�" + tarEnt.getName());
					File gettarEnt = new File(tarEnt.getName());
					File crDir = new File(destinationDir,gettarEnt.getName());
					crDir.mkdir();

					destinationDir = crDir;
					this.zipIf(destinationDir);

				} else {
					File gettarEnt = new File(tarEnt.getName());
					try(FileOutputStream fos = new FileOutputStream(destinationDir + "//" + gettarEnt.getName());
							BufferedOutputStream bos = new BufferedOutputStream(fos);
							){
						byte[] data = new byte[1024];
						int count = 0;
						while((count = tin.read(data)) != -1){
							bos.write(data,0,count);
						}
					}
				}
			}
		}catch(IOException e) {
			this.runLog(e);

			//�@�G���[���ɃG���[�t�H���_�쐬
			File errFolder = new File(Folder + "//errorFolder");
			File to = new File(errFolder + "//" + list);
			this.moveErr(errFolder, fileGz, to);
		}
	}
}


