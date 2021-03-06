package com.ultime5528.vision;

import java.io.ObjectInputStream.GetField;

import javax.swing.UIManager;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Main {

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}




		ImageViewer viewer = new ImageViewer();
		viewer.setSize(600, 400);
		viewer.setVisible(true);



		VideoCapture cap = new VideoCapture(0);

		Mat imgOrigine = new Mat();
		Mat imgModif = new Mat();
		Mat imageTexte = new Mat();

		viewer.putNumber("Blur", 1, 50, 25);
		viewer.putNumber("Temps", 0, 135, 135);

		long start;

		while(cap.isOpened()) {
			
			start = System.currentTimeMillis();
			
			if(cap.read(imgOrigine) && imgOrigine != null) {

				int blurValue = viewer.getNumber("Blur");

				Imgproc.blur(imgOrigine, imgModif, new Size(blurValue, blurValue));
				Imgproc.putText(imgModif, blurValue + "", new Point(0, imgModif.rows()), Core.FONT_HERSHEY_DUPLEX, 3, new Scalar(255, 255, 255), 3);

				imgOrigine.copyTo(imageTexte);
				ecrireInfo(imageTexte, viewer.getNumber("Temps"), false, false);



				long time = System.currentTimeMillis() - start;
				ecrireFps(imageTexte, time);

			
				
			}

			//viewer.putImage("Image originale", VisionUtils.createAwtImage(imgOrigine));
			//viewer.putImage("Image modifi�e", VisionUtils.createAwtImage(imgModif));
			viewer.putImage("Image Texte", VisionUtils.createAwtImage(imageTexte));


		}

	}

	public static void ecrireFps( Mat img, long time) {
		int hauteur = img.rows();
		int largeur = img.cols();

	
		Imgproc.putText(img, Math.round(1000.0 / time) + " fps", new Point(largeur * 0.45 , (hauteur * 0.05)), Core.FONT_HERSHEY_COMPLEX_SMALL, 1, new Scalar(255, 255, 255), 2);
		Imgproc.putText(img, time + " ms" , new Point(largeur * 0.85 , (hauteur * 0.05)), Core.FONT_HERSHEY_COMPLEX_SMALL, 1, new Scalar(255, 255, 255), 2);
		
		
		
	}




	public static void ecrireInfo( Mat img, int tempsRestant, boolean cube, boolean intake ) {

		int hauteur = img.rows();
		int largeur = img.cols();

		Scalar couleur;

		if(tempsRestant >= 30){
			couleur = new Scalar(0, 255, 0);

		}

		else{

			couleur = new Scalar(0, 255, 255);

		}
		
		if(intake) {
			Imgproc.rectangle(img, new Point(0.0, 0.0) , new Point(0.15 * largeur, hauteur * 0.075), new Scalar(0, 255, 0), -1, 8, 0);
		}
		else{
			Imgproc.rectangle(img, new Point(0.0, 0.0) , new Point(0.15 * largeur, hauteur * 0.075), new Scalar(0, 0, 255), -1, 8, 0);
		}

		Imgproc.rectangle(img, new Point(0, hauteur * 0.05),new Point(0, largeur * 0.5) , new Scalar(255, 0, 0), -1, 8, 0);

	

		
		
		Imgproc.rectangle(img, new Point(0, hauteur * 0.9) , new Point(largeur* tempsRestant / 135.0, hauteur), couleur, -1, 8, 0);

		Size tailleTexte = Imgproc.getTextSize(tempsRestant + "", Core.FONT_HERSHEY_DUPLEX, 1, 3, null);
		Imgproc.putText(img, tempsRestant + "", new Point((largeur / 2 ) - (tailleTexte.width / 2) , (hauteur * 0.97)), Core.FONT_HERSHEY_DUPLEX, 1, new Scalar(0, 0, 255), 3);

		Imgproc.putText(img, "Intake", new Point(0 , (hauteur * 0.05)), Core.FONT_HERSHEY_COMPLEX_SMALL, 1, new Scalar(255, 255, 255), 2);


}
}


