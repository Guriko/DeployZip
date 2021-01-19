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
		logger.error("エラー内容:" + error);
	}

	public void zipIf(File Folder) {

		// Zipファイルの展開先
		File destinationDir;

		//listFilesを使用してファイル一覧を取得
		File[] list = Folder.listFiles();

		for(int i=0; i<list.length; i++) {

			if(list[i].isDirectory()) {
				System.out.println(list[i]);
				File seaDir = new File(Folder + "//" + list[i].getName());
				this.zipIf(seaDir);
			}else {

				if (list[i].getName().contains(".zip")){

					//　展開するzipファイル
					String fileZip = Folder + "//" + list[i].getName();

					// Zipファイル名でフォルダを作成
					File crFile = new File(Folder + "//" + this.getPreffix(list[i].getName()));
					crFile.mkdir();

					// Zipファイルの展開先
					destinationDir = crFile;
					this.zipDep(destinationDir, fileZip,list[i].getName(),Folder);


				}else if(list[i].getName().endsWith("tar.gz")) {
					System.out.println(list[i].getName());

					//　展開するtar.gzファイル
					String fileGz = Folder + "//" + list[i].getName();

					File crFile = new File(Folder + "//" + this.getPreffix(list[i].getName()));
					crFile.mkdir();

					// tar.gzファイルの展開先
					destinationDir = crFile;
					this.tarDep(destinationDir,fileGz,list[i].getName(),Folder);
				}
			}
		}
	}



	//　エラーフォルダに退避させる
	private void moveErr(File err,String fileZip,File toFile) {
		if(!err.exists()) {
			err.mkdir();
		}

		//　エラー発生したzipファイルをエラーフォルダに移動
		File from = new File(fileZip);
		File to = new File(err + "//" + toFile);
		this.moveFile(from,to);
	}


	//Fileの移動メソッド
	private void moveFile(File from,File to) {
		from.renameTo(to);
	}

	//　拡張子前のファイル名を抽出する
	private String getPreffix(String fileName) {
		if (fileName == null) {
			return null;
		}

		if (fileName.indexOf(".") > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}

		return fileName;
	}

	//Zipファイル解凍
	public void zipDep(File destinationDir,String fileZip,String list,File Folder) {
		try(FileInputStream fis = new FileInputStream(fileZip);
				BufferedInputStream bis = new BufferedInputStream(fis);
				ZipInputStream zis = new ZipInputStream(bis);
				) {
			ZipEntry zipEntry;
			while((zipEntry = zis.getNextEntry()) != null) {

				if(zipEntry.isDirectory()) {

					System.out.println("ディレクトリです" + zipEntry.getName());
					File getZipEnt = new File(zipEntry.getName());
					File crDir = new File(destinationDir,getZipEnt.getName());
					crDir.mkdir();

					destinationDir = crDir;
					this.zipIf(destinationDir);

				} else {

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

			//　エラー時にエラーフォルダ作成
			File errFolder = new File(Folder + "//errorFolder");
			File to = new File(errFolder + "//" + list);
			this.moveErr(errFolder, fileZip, to);

		} finally {
			File delZip = new File(fileZip);
			delZip.delete();
		}
	}
	
	//tar.gzの解凍
	public void tarDep(File destinationDir,String fileGz,String list,File Folder) {
		try(FileInputStream fis = new FileInputStream(fileGz);
				BufferedInputStream bis = new BufferedInputStream(fis);
				TarInputStream tin = new TarInputStream(new GZIPInputStream(bis));
				) {
			TarEntry tarEnt;
			while ((tarEnt = tin.getNextEntry()) != null) {
				if(tarEnt.isDirectory()) {

					System.out.println("ディレクトリです" + tarEnt.getName());
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

			//　エラー時にエラーフォルダ作成
			File errFolder = new File(Folder + "//errorFolder");
			File to = new File(errFolder + "//" + list);
			this.moveErr(errFolder, fileGz, to);
		}
	}
}


