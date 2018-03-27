package com.ultime5528.vision;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageViewer {

	
	private ImageViewerFrame window;
	private Map<String, Integer> numbers;
	
	
	public ImageViewer() {
		this("ImageViewer");
	}
	
	
	
	public ImageViewer(String title) {
		
		window = new ImageViewerFrame(title);
		window.setLocationByPlatform(true);
		window.addSliderListener(this::setNumber);
		
		numbers = new HashMap<String, Integer>();
		
	}
	
	
	
	public void setVisible(boolean visible) {
		
		window.setVisible(visible);
		
	}
	
	
	public void setSize(int width, int height) {
		
		window.setSize(width, width);
		
	}
	
	
	public void putImage(String title, Image image) {
		
		window.putImage(title, image);
		
	}
	
	public void putNumber(String name, int min, int max, int initialValue) {
		
		window.addSlider(name, initialValue, min, max);
		numbers.put(name, initialValue);
		
	}
	
	
	public int getNumber(String name) {
		
		Integer value = numbers.get(name);
		
		if(value == null)
			throw new IllegalArgumentException("The specified number name could not be found.");
		
		return value;
		
	}
	
	private void setNumber(String name, int value) {
		
		numbers.put(name, value);
		
	}
	
}
