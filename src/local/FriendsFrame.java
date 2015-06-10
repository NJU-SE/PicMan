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
		friends.setModel(dlmFriends);
		dlmFriends.add(1, "dada");
	}
	public void InitFriendsFrame(){
		
	}
	
	
}
