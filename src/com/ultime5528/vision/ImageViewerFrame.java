package com.ultime5528.vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ImageViewerFrame extends JFrame {

	private ArrayList<String> imageList;
	private ArrayList<ResizableImagePane> imagePanes;
	private ArrayList<BiConsumer<String, Integer>> sliderListeners;
	
	private JPanel panelCenter;
	private Box panelRight;
	private JPanel panelSliders;
	private JLabel lblStatus;
	
	private GridLayout layoutPanelCenter;
	
	public ImageViewerFrame(String title) {
		
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initCenterComponents();
		initRightComponents();
		initLayout();
		
		imageList = new ArrayList<>();
		imagePanes = new ArrayList<>();
		sliderListeners = new ArrayList<>();
		
	}
		
	
	
	private void initCenterComponents() {
		
		panelCenter = new JPanel();
		panelCenter.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
		
		layoutPanelCenter = new GridLayout();
		layoutPanelCenter.setHgap(8);
		layoutPanelCenter.setVgap(8);
		
		panelCenter.setLayout(layoutPanelCenter);
		
	}
	
	
	
	private void initRightComponents() {
		
		panelSliders = new JPanel();
		panelSliders.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		BoxLayout layout = new BoxLayout(panelSliders, BoxLayout.PAGE_AXIS);
		panelSliders.setLayout(layout);
		
		JLabel label = new JLabel("Valeurs");
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
		label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		panelSliders.add(label);
		
		JScrollPane rightScrollPane = new JScrollPane(panelSliders);
		rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rightScrollPane.setBorder(null);
		
		JButton btnLeft = new JButton("< Last ");
		JButton btnRight = new JButton(" Next >");
		
		Box buttonBox = new Box(BoxLayout.LINE_AXIS);
		buttonBox.add(btnLeft);
		buttonBox.add(btnRight);
		
		panelRight = new Box(BoxLayout.PAGE_AXIS);
		panelRight.add(rightScrollPane);
		panelRight.add(Box.createVerticalGlue());
		panelRight.add(buttonBox);
		
		
		
	}
	
	private void initLayout() {
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelRight, panelCenter);
		
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(300);

		lblStatus = new JLabel("No message");
		lblStatus.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY), BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		
		add(splitPane, BorderLayout.CENTER);
		add(lblStatus, BorderLayout.SOUTH);
		
	}
	
	
	
	public void putImage(String title, Image image) {
		
		if(!imageList.contains(title)) {
			
			imageList.add(title);
			addImagePane(title);
			
		}
		
		int index = imageList.indexOf(title);
		imagePanes.get(index).setImage(image);
		
		
	}
	
	private void addImagePane(String title) {
		
		JPanel panel = new JPanel();
		
		panel.setBorder(BorderFactory.createTitledBorder(title));
		
		ResizableImagePane imagePane = new ResizableImagePane();
		
		imagePanes.add(imagePane);
		
		panel.add(imagePane);
		
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(imagePane));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(imagePane));
		
		panelCenter.add(panel);
		layoutPanelCenter();
		
	}
	
	
	
	private void layoutPanelCenter() {
		
		int rows = 1, cols = 1, n = imageList.size();
		
		while(rows * cols < n) {
			
			rows++;
			
			if(rows * cols >= n)
				break;
			
			cols++;
			
		}
		
		layoutPanelCenter.setRows(rows);
		layoutPanelCenter.setColumns(cols);
		
		repaint();
		revalidate();
		
	}
	
	
	public void addSlider(String title, int initialValue, int min, int max) {
		
		JLabel lblTitle = new JLabel(title + " : " + initialValue);
		lblTitle.setAlignmentX(LEFT_ALIGNMENT);
		
		
		JSlider slider = new JSlider(min, max, initialValue);
		slider.setAlignmentX(LEFT_ALIGNMENT);
		slider.setBorder(BorderFactory.createEmptyBorder(0, 0, 32, 0));
		slider.addChangeListener(new SliderListener(slider, lblTitle, title));
		
		Hashtable<Integer, JLabel> table = new Hashtable<>();
		table.put(min, new JLabel("" + min));
		table.put(max, new JLabel("" + max));
		
		slider.setLabelTable(table);
		slider.setPaintLabels(true);
		
		panelSliders.add(lblTitle);
		panelSliders.add(slider);
		
		panelRight.repaint();
		panelRight.revalidate();
		
	}
	
	public void addSliderListener(BiConsumer<String, Integer> listener) {
		
		sliderListeners.add(listener);
		
	}
	
	private void callSliderListeners(String name, int value) {
		
		for(BiConsumer<String, Integer> listener : sliderListeners)
			listener.accept(name, value);
		
	}
	
	private class SliderListener implements ChangeListener {

		private JSlider slider;
		private JLabel label;
		private String title;
		
		public SliderListener(JSlider slider, JLabel label, String title) {
			this.slider = slider;
			this.label = label;
			this.title = title;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			
			label.setText(title + " : " + slider.getValue());
			callSliderListeners(title, slider.getValue());
			
		}
		
	}

}
