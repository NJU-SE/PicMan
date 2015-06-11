package database;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

//import DataBase.DataBase;
import server.User;
//import word.Word;
//import System.Sever;
//import System.UserInfo;

public class UserManager {
	//private static ArrayList<User> onlineUser = new ArrayList<User>();
	
	
	public static boolean createUser(String account,String Pw){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into USERTABLE(uid,pwd,online) values('"
					+account+"','"+Pw+"',false);";
			change = statement.execute(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("User Exists!");
			return false;
		}
		finally{
			DataBase.close(conn);
		}
		return true;
	}
	
	public static boolean createUser(String account,String Pw,String email){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into USERTABLE(uid,pwd,email,online) values('"
					+account+"','"+Pw+"','"+email+"', false );";
			change = statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				       "创建用户已存在", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	

	public static boolean createUser(String account,String Pw, byte[] head, String email){
		boolean change = false;
		try {
			PreparedStatement statement;
			Connection conn = DataBase.connect();
			statement = conn.prepareStatement("insert into USERTABLE(uid,pwd,head,email,online) values((?),(?),(?),(?),(?));");
			statement.setString(1, account);
			statement.setString(2, Pw);
			statement.setObject(3, head);
			statement.setString(4, email);
			statement.setBoolean(5, false);
			change = statement.execute();
			DataBase.close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				       "创建用户已存在", "系统信息", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public static boolean createUser(String account,String Pw, byte[] head){
		boolean change = false;
		try {
			PreparedStatement statement;
			Connection conn = DataBase.connect();
			statement = conn.prepareStatement("insert into USERTABLE(uid,pwd,head,online) values((?),(?),(?),(?));");
			statement.setString(1, account);
			statement.setString(2, Pw);
			statement.setObject(3, head);
			//statement.setString(4, email);
			statement.setBoolean(4, false);
			change = statement.execute();
			DataBase.close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				       "创建用户已存在", "系统信息", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
/*	public static boolean createUser(User usrinfo){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into USERTABLE(username,password,email,sex) values('"
					+usrinfo.uid+"','"+usrinfo.pw+"','"+usrinfo.email+"',"+(usrinfo.sex?"true":"false")+");";
			change = statement.execute(sql);
			saveUserImage(usrinfo.account, usrinfo.image);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				       "创建用户已存在", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	*/
	public static boolean addFriend(String account1, String account2){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = null;/*"select * from UserTable where username ='"+ account2 +"';";
			ResultSet result = statement.executeQuery(sql);
			if(!result.next())
			{
				System.out.println("User not exists");
				return false;
			}*/
			sql = "insert into FRIEND(uid1,uid2) values('"
					+account1+"','"+account2+"');";
			change = true;
			statement.execute(sql);
			sql = "insert into FRIEND(uid1,uid2) values('"
					+account2+"','"+account1+"');";
			statement.execute(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("FriendShip Exists!");
			return false;
		}
		finally{
			DataBase.close(conn);
		}
		return true;
	}
	
	
	public static boolean delFriend(String account1, String account2){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "delete from FRIEND where uid1='"
					+account1+"' and uid2='"+account2+"';";
			change = statement.execute(sql);	
			sql = "delete from FRIEND where uid='"
					+account2+"' and uid2='"+account1+"';";
			change = statement.execute(sql);	
		} catch (SQLException e) {
			System.out.println("Not Exists!");
			return false;
		}
		finally{
			DataBase.close(conn);
		}
		return true;
	}
	
	public static boolean delUser(String user){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "delete from USERTABLE where uid='"
					+ user +"';";
			change = statement.execute(sql);	
		} catch (SQLException e) {
			System.out.println("Delete User Not Exists!");
			return false;
		}
		finally{
			DataBase.close(conn);
		}
		return true;
	}
	
/*	public static ArrayList<UserInfo> getUserList(){
		Connection conn = null;
		ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select * from UserTable";
			ResultSet result = statement.executeQuery(sql);
			//UserInfo usrinfoInfo = new UserInfo(result.getString("nickname"),result.getString("username"),null);
			UserInfo usrinfoInfo;
			while(result.next())
			{
				//
				usrinfoInfo = new UserInfo(result.getString("nickname") ,result.getString("username"),null);
				usrinfoInfo.setEmail(result.getString("email"));
				usrinfoInfo.setOn(result.getBoolean("online"));
				userList.add(usrinfoInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		finally{
			DataBase.close(conn);
		}
		return userList;
	}
	*/
	
	public static boolean changePassword(String account,String oldPw,String newPw){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select uid,pwd from USERTABLE;";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()){
				if(account.equals(result.getString("uid"))
						&&oldPw.equals(result.getString("pwd"))){
					change = statement.execute("update user set pwd = '"+newPw+"' where uid = '"+account+"';");
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("Change Password Failed!");
			return false;
		}
		finally{
			DataBase.close(conn);
		}
		return true;
	}
	
	
	public static boolean identityVerify(String account,String Pw){
		boolean isValid = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select uid,pwd from USERTABLE;";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()){
				if(account.equals(result.getString("uid"))
						&&Pw.equals(result.getString("pwd"))){
					isValid = true;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("identityVerify, Access Failed!");
		}
		finally{
			DataBase.close(conn);
		}
		return isValid;
	}
	
	
	public static boolean friendJudge(String account1,String account2){
		boolean isFriend = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select uid1,uid2 from FRIEND "
					+ "where uid1 = '"+ account1 +"' and uid2 = '"+ account2 +"';";
//------------------------------------------------------
			ResultSet result = statement.executeQuery(sql);
			if(result.next())
				isFriend = true;
		} catch (SQLException e) {
			System.out.println("FriendJudge, DataBase Access Failed");
		}
		finally{
			DataBase.close(conn);
		}
		return isFriend;
	}
	
	public static boolean login(String uid, String pwd){
		//TODO
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select uid from USERTABLE where uid = '"+uid+"' and pwd = '"+pwd+"';";
			ResultSet result = statement.executeQuery(sql);
			if(result.next()){
					change = statement.execute("update user set online = true where uid = '"+uid+"';");
					change = true;
			}
		} catch (SQLException e) {
			System.out.println("add online Failed!");
			return false;
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	
	public static boolean logout(String uid){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select uid from USERTABLE;";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()){
				if(uid.equals(result.getString("uid"))){
					change = statement.execute("update user set online = false where uid = '"+uid+"';");
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("logout Failed!");
			return false;
		}
		finally{
			DataBase.close(conn);
		}
		return true;
	}
	
	/*public static ArrayList<UserInfo> getOnlineUser(){
		ArrayList<UserInfo> out = new ArrayList<UserInfo>(onlineUser); 
		return out;
	}
	
	public static UserInfo getUserInfo(String uid){
		Connection conn = null;
		UserInfo usrinfoInfo;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			System.out.println(uid);
			String sql = "select * from UserTable where username = '"+ uid +"';";
			ResultSet result = statement.executeQuery(sql);
			if(result.next())
			{
				usrinfoInfo = new UserInfo(result.getString("nickname") ,result.getString("username"),null);
				usrinfoInfo.setEmail(result.getString("email"));
				usrinfoInfo.setOn(result.getBoolean("online"));
				
				byte[] buff = result.getBytes("image");
				usrinfoInfo.setByteImage(buff);
				//System.out.println(inblobBaidu);
				usrinfoInfo.setOn(result.getBoolean("online"));
				return usrinfoInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			DataBase.close(conn);
		}
		return null;
	}
	*/
/*	public static UserInfo getOtherOnlineUser(String uid){
		UserInfo usrinfoInfo = null;
		for(int i = 0; i < onlineUser.size(); i ++)
			if(onlineUser.get(i).getAccount().equals(uid))
			{
				usrinfoInfo = onlineUser.get(i);
				break;
			}
		return usrinfoInfo;
	}*/
	
	public static boolean saveUserImage(String user,byte[] img){
		boolean change = false;
		try {
			PreparedStatement statement;
			Connection conn = DataBase.connect();
			statement = conn.prepareStatement("update UserTable set image = (?) where username = '"+user+"';");
			statement.setBytes(1, img);
			change = statement.execute();
			DataBase.close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				       "未知的错误，存储头像不成功", "系统信息", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	/*public static void main(String[] args){
		User usr = new User("zhangry","123456");
		User usr2 = new User("Jam","123456");
		User usr3 = new User("Haohao","123456");
		usr.login("ip1", 1);
		usr2.login("ip2", 1);
		usr3.login("ip3", 1);
		usr.addFriend("Haohao");
		usr.delFriend("Jam");
		//UserInfo usrinfo = UserManager.getOtherOnlineUser("zhangry");
		//usrinfo.display();
	}*/
}