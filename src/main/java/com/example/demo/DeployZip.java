//package com.example.demo;
//
//import java.io.IOException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * �w��t�H���_����Zip�t�@�C����W�J����B
// * @author heisei-20
// *
// */
//@Slf4j
//public class DeployZip {
//
//
//	public Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	public void runLog(IOException error) {
////		logger.trace("test-trace");
////		logger.debug("test-debug");
////		logger.info("test-info");
////		logger.warn("test-warn");
//		logger.error("�G���[���e:" + error);
//	}
//
////	public static void main(String[] args) {
////		DeployZip test = new DeployZip();
////		test.runLog();
////	}
//	
//}
////	// Zip�t�@�C���̓W�J��
////	private File destinationDir;
////
////	// listFiles���g�p���ăt�@�C���ꗗ���擾
////	private File[] list;
////
////	// zip�t�@�C���t���O
////	private boolean flg = false;
////
////	/**
////	 * Zip�t�@�C�����������A���̃t�@�C�����Ńt�H���_���쐬��
////	 * i�W�J�����t�H���_�̒���Zip�t�@�C����W�J����
////	 * i�����P��zip�t�@�C�����m�F�������̂�����
////	 * i����2�Ɍ����������Ă���t�H���_������
////	 * @param fileName
////	 * @param dir
////	 * @throws IOException
////	 */
////	public void ifZip(File fileName, File dir){
////		
////
////		if (fileName.getName().contains(".zip") || fileName.getName().contains(".tar.gz")) {
////
////			//�@�W�J����zip�t�@�C��
////			String fileZip = dir + "//" + fileName.getName();
////
////			// Zip�t�@�C�����Ńt�H���_���쐬
////			File crFile = new File(dir + "//" + this.getPreffix(fileName.getName()));
////			crFile.mkdir();
////			
////			//�@�o�͐�
////			destinationDir = crFile;
////
////			byte[] buffer = new byte[1024];
////
////	        try(
////	                FileInputStream fis = new FileInputStream(inputfile1);
////	                BufferedInputStream bis = new BufferedInputStream(fis);
////	        		ZipInputStream zis = new ZipInputStream(bis,charset);
////	        )
////			try(ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));) {
////				ZipEntry zipEntry = zis.getNextEntry();
////				
////				while (zipEntry != null) {
////					// zip�t�@�C����zip�t�@�C���̒��ɂ��鎞��zip�t�@�C����
////					String inZipName = null;
////					String ZipName = null;
////					if (zipEntry.getName().contains(".zip")) {
////						ZipName = zipEntry.getName();
////						inZipName = this.getPreffix(zipEntry.getName());
////						list = destinationDir.listFiles();
////						flg = true;
////					}
////
////					File newFile = this.newFile(destinationDir, zipEntry);
////					try(FileOutputStream fos = new FileOutputStream(newFile);){
////						int len;
////						while ((len = zis.read(buffer)) > 0) {
////							fos.write(buffer, 0, len);
////							zipEntry = zis.getNextEntry();
////						}
////
////					}
////
////
////					if (flg == true) {
////						crFile = new File(destinationDir + "//" + inZipName);
////						crFile.mkdir();
////
////						fileZip = destinationDir + "//" + ZipName;
////						try(ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));){
////						}
////						zipEntry = zis.getNextEntry();
////						destinationDir = crFile;
////
////						flg = false;
////					}
////
////				};
////			} catch (IOException e) {
////				File errFolda = new File(dir + "//errorFolda");
////				if(!errFolda.exists()) {
////					errFolda.mkdir();
////				}
////
////				File from = new File(fileZip);
////				File to = new File(errFolda + "//" + fileName.getName());
////				this.moveFile(from,to);
////			}
////		}
////	}
////
////	//File�̈ړ����\�b�h
////	private void moveFile(File from,File to) {
////		from.renameTo(to);
////	}
////
////
////
////
////
////	private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
////		File destFile = new File(destinationDir, zipEntry.getName());
////
////		String destDirPath = destinationDir.getCanonicalPath();
////		String destFilePath = destFile.getCanonicalPath();
////
////		if (!destFilePath.startsWith(destDirPath + File.separator)) {
////			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
////		}
////
////		return destFile;
////	}
////
////	//�@�g���q�O�̃t�@�C�����𒊏o����
////	private String getPreffix(String fileName) {
////		if (fileName == null) {
////			return null;
////		}
////
////		if (fileName.indexOf(".") > 0) {
////			fileName = fileName.substring(0, fileName.lastIndexOf("."));
////		}
////
////		return fileName;
////	}
//
//
////	//�@ifZip���Ăяo��
////	private void callZip(boolean flg, File[] list, File dir) throws IOException {
////		if (flg == true) {
////			for (int i = 0; i < list.length; i++) {
////				System.out.println("OOOO");
////				//�@�g���q��.zip�̂��̂𒊏o
////				ifZip(list[i], destinationDir);
////			}
////			// list.removeAll(list);
////			flg = false;
////		}
////	}
//
