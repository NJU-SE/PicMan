package inter;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

//璇存槑:鍑℃槸闇�瑕佽繑鍥瀊oolen 鍨嬬殑娑堟伅閮戒娇鐢≧eplydata

public class Message implements Serializable{
	public int id;
	public int type;
	public static final int LOGIN = 1;
	public static final int LOGOUT = 2;
	public static final int REGISTER = 3;
	public static final int CHANGE_PSW = 4;
	public static final int ADD_FRIEND = 5;
	public static final int DEL_FRIEND = 6;
	public static final int UPDATE_FRIEND_ONLINE = 7;
	//public static final int SEARCH = 8;
	//public static final int ADD_PRAISE = 9;
	//public static final int DEL_PRAISE = 10;
	//public static final int SEND_CARD = 11;
	public static final int SEND_MESSAGE = 12;
	//public static final int IP_DATA = 13;
	//public static final int USER_INFO = 14;
	//public static final int IS_ONLINE = 15;
	public static final int GETALBUM = 16;//鑾峰緱鎸囧畾鐩稿唽,闇�瑕佹彁渚泆id 鍜� path,杩斿洖鏁翠釜鐩稿唽鐨勪俊鎭�,鍖呮嫭瀛愮浉鍐宯ame,鎵�鏈夊浘鐗囧強璇勮鍜宯ame
	public static final int DEL_ALBUM = 17;//鍒犻櫎鎸囧畾鐩稿唽,闇�瑕佹彁渚沺ath
	public static final int CRE_ALBUM = 18;//鍒涘缓鐩稿唽,闇�瑕佹彁渚沺ath鍜宯ame
	public static final int UPLOAD_PIC = 19;//涓婁紶鍥剧墖鍒版寚瀹氱浉鍐�,闇�瑕佹寚瀹氱浉鍐宲ath鍜屽浘鐗�,浠ュ強鍥剧墖鍚嶇О
	public static final int DEL_PIC = 20;//鍒犻櫎鍥剧墖,闇�瑕佹寚瀹氱浉鍐宲ath鍜屽浘鐗囧悕绉�
	public static final int ADD_MSG = 21;//涓哄浘鐗囨坊鍔犺瘎璁�,闇�瑕佹寚瀹氱浉鍐宲ath,鍥剧墖name鍜岃瘎璁轰俊鎭�
	
	//闇�瑕佸晢璁�
	public static final int DEL_MSG = 22;//鍒犻櫎鑷繁鐨勮瘎璁�,闇�瑕佹寚瀹氱浉鍐宲ath,鍥剧墖name鍜岃瘎璁篿d.
	public static final int REPLY = 23;
	public MsgData data;
	






	/*
		static final int LOGIN = 1;
		static final int LOGOUT = 2;
		static final int REGISTER = 3;
		static final int CHANGE_PSW = 4;
		static final int ADD_FRIEND = 5;
		static final int DEL_FRIEND = 6;
		static final int UPDATE_FRIEND_ONLINE = 7;
		static final int SEARCH = 8;
		static final int ADD_PRAISE = 9;
		static final int DEL_PRAISE = 10;
		static final int SEND_CARD = 11;
		static final int SEND_MESSAGE = 12;
	*/
/*	public String toString(){
		
		
	}*/
	
	public abstract class MsgData implements Serializable{
		
	}
	
	public class GetAlbum extends MsgData implements Serializable{
		public ArrayList<Pic> pics;
		public ArrayList<String> albums;
	}
	
	public class DEL_Album extends MsgData implements Serializable{
		public String path;
	}

	public class CRE_Album extends MsgData implements Serializable{
		public String path;
		public String name;
	}
	
	public class DelPic extends MsgData implements Serializable{
		public String path;
		public String name;
	}
	
	public class UploadPic extends MsgData implements Serializable{
		public String path;//album path
		public byte[] pic;
		public String name;
	}
	
	public class LoginMsg extends MsgData implements Serializable{
		public String uid;
		public String psw;
	}

	public class LogoutMsg extends MsgData implements Serializable{
		public String uid;
		public String psw;
	}

	public class RegsiterMsg extends MsgData implements Serializable{
		public String uid;//can't be null
		public String psw;//can't be null
		public String email;//can be null
		public byte[] head;//can be null
		//public UserInfo user;
	}

	public class ChangePsw extends MsgData implements Serializable{
		public String uid;
		public String oldpsw;
		public String newpsw;
	}

	public class AddFriend extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String friend_uid;
	}

	public class DelFriend extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String friend_uid;
	}

	public class OnlineFriend extends MsgData implements Serializable{
		public String uid;
		public String psw;
	}
	
	public class UPDATE_FriendList extends MsgData implements Serializable{
		public ArrayList<Entry<String, Boolean>> friends;//姣忎釜鐢ㄦ埛鐘舵�佽鏀瑰彉 
	}

	
	

	/*public class Add_Praise extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String word;
		public int source;
	}

	public class Del_Praise extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String word;
		public int source;
	}

	public class Send_Card extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String targetuid;
		public byte[] card;
	}
*/
	public class Send_Message extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String targetuid;
		public String dialoge;
	}
	
	public class ReplyData extends MsgData implements Serializable{
		public boolean success;
	}

	
	/*public class IpData extends MsgData implements Serializable{
		public String uid;
		public String Ip;
	}
	public class Info extends MsgData implements Serializable{
		public String uid;
		public String psw;
	}
	public class IsOnline extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public boolean isOnline;
	}
	*/
	public static byte[] imageToBytes(BufferedImage image){
		byte[] buf = null;
		
		if(image == null)
			return null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			buf = baos.toByteArray();
			
		}catch(IOException e){
			
		}
		return buf;
	}
	
	public static BufferedImage bytesToImage(byte[] buf){
		BufferedImage image = null;
		if(buf == null)
			return null;
		try{
			ByteArrayInputStream baos = new ByteArrayInputStream(buf);
			image = ImageIO.read(baos);
		}catch(IOException e){
			
		}
		return image;
	}
}
