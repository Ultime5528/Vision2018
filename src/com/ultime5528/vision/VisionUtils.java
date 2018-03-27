package com.ultime5528.vision;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import org.opencv.core.Mat;

public class VisionUtils {

	/**
	 * Converts a BGR/Gray Mat to a BufferedImage, which can be used in AWT UI interfaces.
	 * @param mat The mat to convert.
	 * @return A displayable BufferedImage.
	 */
	public static BufferedImage createAwtImage(Mat mat) {

	    int type = 0;
	    if (mat.channels() == 1) {
	        type = BufferedImage.TYPE_BYTE_GRAY;
	    } else if (mat.channels() == 3) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    } else {
	        return null;
	    }

	    BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    mat.get(0, 0, data);

	    return image;
	}
	
	
	public static BufferedImage resize(Image image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
	
}
