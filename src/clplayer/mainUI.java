package clplayer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.*;

public class mainUI extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7324045363208432875L;

	
	public static  void main(String[] args){
		UISer frame = new UISer();//create
		frame.pack();
		frame.setTitle("121220042_Dictionary");
		frame.setSize(500,309);//set the frame size
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        
		
	}
}