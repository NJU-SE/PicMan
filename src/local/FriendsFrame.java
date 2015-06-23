package local;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
/**
 * 朋友列表
 * @author Huixin
 *
 */
public class FriendsFrame extends JFrame{
	public JList<String> friends = new JList<String>();
	DefaultListModel<String> dlmFriends = new DefaultListModel<String>();
	
	public FriendsFrame(){
		 setVisible(false);
	     setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//点叉的时候只是隐藏面板
	     setTitle("好友列表");
	     setSize(220, 300);
	     setLocationRelativeTo(null);      //弹出面板时在屏幕的中央
	     friends.setModel(dlmFriends);
	     dlmFriends.add(0, "liming");
	     dlmFriends.add(1, "wanghua");
	     dlmFriends.add(2, "xiaowang");
	     dlmFriends.add(3, "wangkai");
	     dlmFriends.add(4, "wangmeng");
	     add(friends);
	}
	public void InitFriendsFrame(){

		
		
	}
	//TODO: 在JList上每一行添加动作监听
	
	
}
