package local;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
/**
 * 消息（邮件）视图
 * @author Huixin
 *
 */

public class MailFrame extends JFrame{
	public JList<String> message = new JList<String>();
DefaultListModel<String> dlmMsg = new DefaultListModel<String>();
	
	public MailFrame(){
		 setVisible(false);
	     setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//点叉的时候只是隐藏面板
	     setTitle("邮件列表");
	     setSize(220, 300);
	     setLocationRelativeTo(null);      //弹出面板时在屏幕的中央
	     message.setModel(dlmMsg);
	     dlmMsg.add(0, "from John");
	     dlmMsg.add(1, "from lily");
	     dlmMsg.add(2, "from zz");
	     dlmMsg.add(3, "from y");
	     dlmMsg.add(4, "from xy");
	     add(message);
	}
	public void InitMailFrame(){

		
		
	}
	//TODO: 在JList上每一行添加动作监听
	
}
