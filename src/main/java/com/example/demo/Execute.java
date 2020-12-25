package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Execute {

	public static void main(String[] args) throws IOException{
		
		//Fileクラスのオブジェクトを生成し対象のディレクトリを指定
		File dir = new File(args[0]);
		
		//listFilesを使用してファイル一覧を取得
		File[] list = dir.listFiles();
		
		//　インスタンス作成
		DeployZip deployZip = new DeployZip();
		
		//　指定フォルダにログファイル作成
		File log_file = new File(dir + "//log");
		log_file.mkdir();
		
		for(int i=0; i<list.length; i++) {
//			System.out.println(list[i]);
			//　拡張子が.zipのものを抽出
			deployZip.ifZip(list[i], dir);
		}
	}

}