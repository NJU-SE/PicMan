package clplayer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import inter.Message;

public class RefreshList implements Runnable {

	private JList<UserInfo> list;
	private LinkToServer link;
	public RefreshList(final LinkToServer link, JList list){
		this.list = list;
		this.link = link;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			DefaultListModel<UserInfo> dlist = new DefaultListModel<UserInfo>();
			dlist.removeAllElements();
			
			System.out.println("try to refresh list $$$$$$$$$$$$$$$$$$$");
				//鎵惧埌浜嗘柊鐨勫崟璇嶅垪琛�,閭ｄ箞鏇存柊list涓殑鍗曡瘝
			if(link.isOnline()){
				ArrayList<UserInfo> online = link.getOnlineFriend();
				if(online != null){
					System.out.println("********************online is not null");
					for(UserInfo info:online){
						dlist.addElement(info);
						info.display();
						//System.out.println("refresh friend list : friend:" + info.getAccount());
					}
				}else{
					System.out.println("no friend online");
				}
				
			}
	
			try {
				list.setModel(dlist);//鏇存柊鍒楄〃涓殑鍏冪礌
				list.repaint();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
