package com.example.demo;

import java.io.File;
import java.io.IOException;



public class Execute {
	public static void main(String[] args) throws IOException{
		
		//Fileクラスのオブジェクトを生成し対象のディレクトリを指定
		File dir = new File(args[0]);
		
		//　インスタンス作成
		NewDeployZip deployZip = new NewDeployZip();
		
		deployZip.zipIf(dir);
	}

}