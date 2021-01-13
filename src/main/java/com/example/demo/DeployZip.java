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
// * 指定フォルダ内のZipファイルを展開する。
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
//		logger.error("エラー内容:" + error);
//	}
//
////	public static void main(String[] args) {
////		DeployZip test = new DeployZip();
////		test.runLog();
////	}
//	
//}
////	// Zipファイルの展開先
////	private File destinationDir;
////
////	// listFilesを使用してファイル一覧を取得
////	private File[] list;
////
////	// zipファイルフラグ
////	private boolean flg = false;
////
////	/**
////	 * Zipファイルだった時、そのファイル名でフォルダを作成し
////	 * i展開したフォルダの中にZipファイルを展開する
////	 * i引数１にzipファイルか確認したものを入れる
////	 * i引数2に検索をかけているフォルダを入れる
////	 * @param fileName
////	 * @param dir
////	 * @throws IOException
////	 */
////	public void ifZip(File fileName, File dir){
////		
////
////		if (fileName.getName().contains(".zip") || fileName.getName().contains(".tar.gz")) {
////
////			//　展開するzipファイル
////			String fileZip = dir + "//" + fileName.getName();
////
////			// Zipファイル名でフォルダを作成
////			File crFile = new File(dir + "//" + this.getPreffix(fileName.getName()));
////			crFile.mkdir();
////			
////			//　出力先
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
////					// zipファイルがzipファイルの中にある時のzipファイル名
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
////	//Fileの移動メソッド
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
////	//　拡張子前のファイル名を抽出する
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
////	//　ifZipを呼び出す
////	private void callZip(boolean flg, File[] list, File dir) throws IOException {
////		if (flg == true) {
////			for (int i = 0; i < list.length; i++) {
////				System.out.println("OOOO");
////				//　拡張子が.zipのものを抽出
////				ifZip(list[i], destinationDir);
////			}
////			// list.removeAll(list);
////			flg = false;
////		}
////	}
//
