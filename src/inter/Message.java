package inter;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Message implements Serializable{
	public int type;
	public static final int LOGIN = 1;
	public static final int LOGOUT = 2;
	public static final int REGISTER = 3;
	public static final int CHANGE_PSW = 4;
	public static final int ADD_FRIEND = 5;
	public static final int DEL_FRIEND = 6;
	public static final int UPDATE_FRIEND_ONLINE = 7;
	public static final int SEARCH = 8;
	//public static final int ADD_PRAISE = 9;
	//public static final int DEL_PRAISE = 10;
	//public static final int SEND_CARD = 11;
	public static final int SEND_MESSAGE = 12;
	//public static final int IP_DATA = 13;
	public static final int USER_INFO = 14;
	//public static final int IS_ONLINE = 15;
	public static final int GETALBUM = 16;//获得指定相册,需要提供uid 和 path,返回整个相册的信息,包括子相册name,所有图片及评论和name
	public static final int DEL_ALBUM = 17;//删除指定相册,需要提供path
	public static final int CRE_ALBUM = 18;//创建相册,需要提供path和name
	public static final int UPLOAD_PIC = 19;//上传图片到指定相册,需要指定相册path和图片,以及图片名称
	public static final int DEL_PIC = 20;//删除图片,需要指定相册path和图片名称
	public static final int ADD_MSG = 21;//为图片添加评论,需要指定相册path,图片name和评论信息
	
	//需要商讨
	public static final int DEL_MSG = 22;//删除自己的评论,需要指定相册path,图片name和评论id.
	
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
	public String toString(){
		
		if(type == Message.SEND_MESSAGE){
			return "from " + ((Message.Send_Message)data).uid;
		}else if(type == Message.SEND_CARD){
			return "from " + ((Message.Send_Card)data).uid;
		}else
			return "error!";
	}
	
	public abstract class MsgData implements Serializable{
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
		public String uid;
		public String psw;
		public String email;
		public boolean sex;//false for famale true for male
		public byte[] head;
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

	public class Serach extends MsgData implements Serializable{
		
	}

	public class Add_Praise extends MsgData implements Serializable{
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

	public class Send_Message extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String targetuid;
		public String dialoge;
	}
	public class ReplyData extends MsgData implements Serializable{
		public boolean success;
	}

	public class IpData extends MsgData implements Serializable{
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
