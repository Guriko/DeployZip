package com.example.demo;

import java.io.File;
import java.io.IOException;



public class Execute {
	public static void main(String[] args) throws IOException{
		
		//File�N���X�̃I�u�W�F�N�g�𐶐����Ώۂ̃f�B���N�g�����w��
		File dir = new File(args[0]);
		
		//�@�C���X�^���X�쐬
		NewDeployZip deployZip = new NewDeployZip();
		
		deployZip.zipIf(dir);
	}

}