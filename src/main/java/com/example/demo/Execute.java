package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Execute {

	public static void main(String[] args) throws IOException{
		
		//File�N���X�̃I�u�W�F�N�g�𐶐����Ώۂ̃f�B���N�g�����w��
		File dir = new File(args[0]);
		
		//listFiles���g�p���ăt�@�C���ꗗ���擾
		File[] list = dir.listFiles();
		
		//�@�C���X�^���X�쐬
		DeployZip deployZip = new DeployZip();
		
		//�@�w��t�H���_�Ƀ��O�t�@�C���쐬
		File log_file = new File(dir + "//log");
		log_file.mkdir();
		
		for(int i=0; i<list.length; i++) {
//			System.out.println(list[i]);
			//�@�g���q��.zip�̂��̂𒊏o
			deployZip.ifZip(list[i], dir);
		}
	}

}