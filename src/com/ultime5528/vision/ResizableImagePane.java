package com.ultime5528.vision;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;



@SuppressWarnings("serial")
public class ResizableImagePane extends JPanel {

	private Image master;
	private Image scaled;
	
/*
	@Override
	public Dimension getPreferredSize() {
		//return new Dimension(master.getWidth(this), master.getHeight(this));
		return getSize();
	}
*/
	
	public void setImage(Image master) {
		
		if(master == null)
			throw new IllegalArgumentException("Master image cannot be null.");
		
		this.master = master;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		generateScaledInstance();
		
		int x = (getWidth() - scaled.getWidth(this)) / 2;
		int y = (getHeight() - scaled.getHeight(this)) / 2;
		g.drawImage(scaled, x, y, this);

	}

	
	private void generateScaledInstance() {
				
		double imgWidth = master.getWidth(this);
		double imgHeight = master.getHeight(this);
		double windowWidth = getWidth();
		double windowHeight = getHeight();
		
		double imgRatio = imgWidth / imgHeight;
		double windowRatio = windowWidth / windowHeight;
		
		
		if(imgRatio > windowRatio) { // Window taller than picture
			
			imgWidth = windowWidth;
			imgHeight = imgWidth / imgRatio;
			
		} else {
			
			imgHeight = windowHeight;
			imgWidth = imgHeight * imgRatio;
			
		}
		
		scaled = VisionUtils.resize(master, (int)Math.round(imgWidth), (int)Math.round(imgHeight));
		
	}
	
}
