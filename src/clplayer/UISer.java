package clplayer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UISer extends JFrame
{
	/**
	 * 
	 */
	private findClass find = new findClass();
	private static final long serialVersionUID = 6762200049326769616L;
	
	//猜测词的个数
	static  int numofge = 5;
	// 输入面板的创建
	private JPanel inputPanel = new  JPanel(new GridLayout(1,1));
	private JTextField jtfInputWord = new  JTextField();
	private JButton accurateSearch = new JButton("精准查找");
	private JButton vagueSearch = new JButton("模糊查找");
	
	//输出面板的创建
	private JPanel outputPanel = new JPanel(new GridLayout(1,2));
	private JButton outputResult = new JButton("the result:");
	private JTextField outputTextField = new JTextField();
	
	//猜测面板的创建
	private JPanel searchDefault = new JPanel(new GridLayout(6,1));
	
	//private JButton []vagueResultButtons = new JButton[numofge];
	private JButton vagueResultButton1 = new JButton("1:       ");
	private JButton vagueResultButton2 = new JButton("2:       ");
	private JButton vagueResultButton3 = new JButton("3:       ");
	private JButton vagueResultButton4 = new JButton("4:       ");
	private JButton vagueResultButton5 = new JButton("5:       ");
	
	public  UISer(){
		
		
		// 输入面板的创建
		inputPanel.add(new JLabel(" Enter  a  word : "));
		inputPanel.add(jtfInputWord);
		inputPanel.add(accurateSearch);
		inputPanel.add(vagueSearch);
		//输出面板的创建
		outputPanel.add(outputResult);
		outputPanel.add(outputTextField);
		
		//猜测面板的创建
		searchDefault.add(new JLabel("Is those words?"));
		//for(int i=0;i<vagueResultButtons.length;i++){
		//	vagueResultButtons[i]=new JButton("Btn"+i);
		//}
	    //	for(int i = 0; i<numofge; i++)
	    //		searchDefault.add(vagueResultButtons[i]);
		searchDefault.add(vagueResultButton1);
		searchDefault.add(vagueResultButton2);
		searchDefault.add(vagueResultButton3);
		searchDefault.add(vagueResultButton4);
		searchDefault.add(vagueResultButton5);
		
		this.add(inputPanel,BorderLayout.NORTH);
		this.add(outputPanel,BorderLayout.EAST);
		this.add(searchDefault,BorderLayout.WEST);
		
		accurateSearch.addActionListener(new accurateSearchButtonListener());
		vagueSearch.addActionListener(new vagueSearchButtonListener()); 
		//for(int i = 0; i<numofge; i++)
		//	vagueResultButtons[i].addActionListener(new vagueResultButtonsListener());
		
		vagueResultButton1.addActionListener(new vagueResultButtonsListener1());
		vagueResultButton2.addActionListener(new vagueResultButtonsListener2());
		vagueResultButton3.addActionListener(new vagueResultButtonsListener3());
		vagueResultButton4.addActionListener(new vagueResultButtonsListener4());
		vagueResultButton5.addActionListener(new vagueResultButtonsListener5());
	}
	
	private class accurateSearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			//get value of textfield
			String Source = jtfInputWord.getText();
			String result = find.accurateFind(Source);
			JButton resultSource = new JButton(result);
			//outputPanel.add(resultSource);
			outputResult.setText(" Explain:");;
			outputTextField.setText(result);
		}
	}
	
	private class vagueSearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			findClass find = new findClass();
			//get value of textfield
			String Source = jtfInputWord.getText();
			String []resultArray = find.vagueFind(Source);
			String result = "aaddd\thuhihhih\tjijih\tnjijhihihi\tfqwfwqfwqef";
			//String [] arrayString = new String[numofge];
			String [] arrayString=result.split("\t");
			vagueResultButton1.setText(arrayString[0]);
			vagueResultButton2.setText(arrayString[1]);
			vagueResultButton3.setText(arrayString[2]);
			vagueResultButton4.setText(arrayString[3]);
			vagueResultButton5.setText(arrayString[4]);
			
		}
	}
	private class vagueResultButtonsListener1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			outputTextField.setText(find.accurateFind(vagueResultButton1.getText()));
		}
	}
	
	private class vagueResultButtonsListener2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			outputTextField.setText(find.accurateFind(vagueResultButton2.getText()));
		}
	}
	
	private class vagueResultButtonsListener3 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			outputTextField.setText(find.accurateFind(vagueResultButton3.getText()));
		}
	}
	
	private class vagueResultButtonsListener4 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			outputTextField.setText(find.accurateFind(vagueResultButton4.getText()));
		}
	}
	
	private class vagueResultButtonsListener5 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			outputTextField.setText(find.accurateFind(vagueResultButton5.getText()));
		}
	}
}