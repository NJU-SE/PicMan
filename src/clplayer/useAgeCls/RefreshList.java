package clplayer.useAgeCls;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/*import System.UserInfo;
import word.Word;*/

public class RefreshList implements Runnable {

	//private JList<UserInfo> list;
	private LinkToServer link;
	public RefreshList(final LinkToServer link, JList list){
		//this.list = list;
		this.link = link;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			/*
			DefaultListModel<UserInfo> dlist = new DefaultListModel<UserInfo>();
			dlist.removeAllElements();
			
			System.out.println("try to refresh list $$$$$$$$$$$$$$$$$$$");
				//闁瑰灚鍎抽崺灞剧閸℃ɑ鐓�闁汇劌瀚畷鐔烘嫚瀹ュ懎鐏欓悶娑虫嫹,闂侇叏绲肩粻鐐哄即鐎涙ɑ鐓�list濞戞搩鍘惧▓鎴﹀础閺囷紕妲�
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
	*/
			try {
				//list.setModel(dlist);//闁哄洤鐡ㄩ弻濠囧礆濡ゅ嫨锟藉啯绋夐鐘崇暠闁稿繐鍟扮粈锟�
			//	list.repaint();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
