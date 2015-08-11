package com.htmmft.match.visu;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class GUIHelper {
	
	public static void showOnFrame(JComponent component, String frameName) {
		JFrame frame = new JFrame(frameName);
		WindowAdapter wa = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.getContentPane().add(component);
		frame.pack();
		frame.setVisible(true);
		drawMatch(component);
	}
	
	private static void drawMatch(JComponent component) {
		for (int i=0 ;i< 10 * 60  * 10; i++){
			 ((JCanvas) component).Timer();
			component.repaint();
			try{
				  Thread.sleep(100); //Ici, une pause d'un dizieme seconde
				}catch(InterruptedException e) {
				  e.printStackTrace();
				}
		}
		
	}


}