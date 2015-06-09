package server;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.Icon;
import database.UserManager;
//import net.Message.Message;
//import System.UserInfo;
//import word.Word;

public class User implements Serializable{
	//protected String nickname;
	protected String uid;
	protected String pw;
	protected String email;
	//protected BufferedImage head;
	protected ArrayList<User> onlineFriend;
	protected boolean on = false;
	//protected boolean sex = true;
	protected byte[] image = null;
	protected Date date = null;		//date是上次上线时间
	protected String strdate; 	//date的字符串表示

	public User(){}
	public User(String uid,String Pw)
	{
		this.uid = uid;
		this.pw = Pw;
		onlineFriend = new ArrayList<User>();
	}
	
	/*public User(String nickname,String account,String Pw)
	{
		this.nickname = nickname;
		this.account = account;
		this.pw = Pw;
		onlineFriend = new ArrayList<UserInfo>();
	}
	*/
	protected User(User usr) {
		// TODO Auto-generated constructor stub
	//	nickname = usr.nickname;
		uid = usr.uid;
		pw = usr.pw;
		onlineFriend = usr.onlineFriend;
		on = usr.on;
		//sex = usr.sex;
		image = usr.image;
		date = usr.date;		//date是上次上线时间
		strdate = usr.strdate; 
	}
	
	public boolean login(String ip,int port)
	{
		boolean success = false;
		success = UserManager.identityVerify(uid, pw);
		on = true;
		date = new Date(System.currentTimeMillis());
		if(success)
		{
			User tmpUserInfo = new User(this);
			//tmpUserInfo.setprot(port);
			//tmpUserInfo.setIpAddr(ip);
			//tmpUserInfo.display();
			UserManager.addOnlineUser(tmpUserInfo);
		}
		return success;
	}
	
	public boolean logout()
	{
		boolean success = false;
		on = false;
		success = UserManager.identityVerify(uid, pw);
		System.out.println(success);
		if(success)
		{
			UserInfo tmpUserInfo;
			ArrayList<UserInfo> uInfos = UserManager.getOnlineUser();
			for(int i = 0; i < uInfos.size();i ++ )
				if(uInfos.get(i).getAccount().equals(this.account))
					UserManager.delOnlineUser(uInfos.get(i));
		}
		return success;
	}
	
	public boolean addFriend(String _account)
	{
		//A more complex Implement Needed!!!!!!!!!!!!!!!!!!!!!!!
		boolean success,success1 = false,success2 = false;
		success = UserManager.friendJudge(this.account, _account);
		if(!success)
		{			
			System.out.println("OK1");
			success1 = UserManager.addFriend(this.account, _account);
			System.out.println("OK2");
			success2 = UserManager.addFriend(_account,this.account);
		}
		return success1 & success2;
	}
	
	public boolean delFriend(String _account)
	{
		//A more complex Implement Needed!!!!!!!!!!!!!!!!!!!!!!!
		boolean success = false;
		success = UserManager.friendJudge(this.account, _account);
		if(success)
			success = UserManager.delFriend(this.account, _account) && UserManager.delFriend(_account,this.account);
		return success;
	}
	
	public boolean isFriend(String _account)
	{
		boolean success = false;
		//success = UserManager.identityVerify(account, pw);
		success = UserManager.friendJudge(account, _account);
		return success;
	}
	
	public boolean changePassword(String oldPw,String newPw){
		boolean change = false;
		change = UserManager.changePassword(account, oldPw, newPw);
		return change;
	}
	
	public void updateFriendOnline()
	{
		onlineFriend.clear();
		ArrayList<UserInfo> temp = UserManager.getOnlineUser();
		for(int i = 0;i < temp.size();i ++)
		{
			//System.out.println(temp.get(i).getAccount()+" "+ this.account +" "+ temp.get(i).isFriend(this.account));
			
			if(isFriend(temp.get(i).getAccount()))
			{	
				onlineFriend.add(temp.get(i));
				//temp.get(i).display();
			}
		}
	}
	
	public ArrayList<UserInfo> getFriendOnline()
	{
		updateFriendOnline();
		return new ArrayList<UserInfo>(onlineFriend);
	}
	
	public Word SearchWord(String word)
	{
		return new Word();
	}
	
	public void display()
	{
		System.out.println("用户名:       "+account);
		System.out.println("昵称:        "+nickname);
		System.out.println("密码:        "+pw);
		System.out.println("在线:        "+on);
		System.out.println("性别:        "+sex);
		System.out.println("上次登录:     "+date);
	}
	
	public UserInfo UpdateUserInfo()
	{
		
		return null;	
	}
		
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public BufferedImage getImge(){
		return Message.bytesToImage(image);
	}
	
	public void setImge(BufferedImage img){
		image = Message.imageToBytes(img);
	}
	
	public byte[] getByteImage() {
		return image;
	}
	public void setByteImage(byte[] image) {
		this.image = image;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStrdate() {
		return strdate;
	}
	public void setStrdate(String strdate) {
		this.strdate = strdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

