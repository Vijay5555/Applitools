package com.applitools.quickstarts;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class URL2Mock_Tester2 {

	public static void main(String[] args) throws Exception {

		URL2Mock_Tester tester = new URL2Mock_Tester();
		String[] imageData;
		
		//From Figma
//		FigmaGetter figmaGetter = new FigmaGetter("figd_nBTcTesf6mDdhCvksEfed382cklVpgeBD_ql1wik","1nisS8ZE1gEmjYWvTbZ7Ji");
//
//		figmaGetter.getProjectFiles();
//		imageData = figmaGetter.getScreenImageUrl("11:113");

//		sObXwDPH2IUpLXY43P0hi0kHSH4FNHamO14Bbj6Qc1E110

		// Local file




		imageData = new String[] { "C:/Users/vchinnap/Desktop/Figma/REGISTRATION PAGE_D.jpg", "", "" };
		BufferedImage bimg = ImageIO.read(new File(imageData[0]));
		imageData[1] = bimg.getWidth() + "";
		imageData[2] = bimg.getHeight() + "";

		tester.run(imageData);

	}

}
