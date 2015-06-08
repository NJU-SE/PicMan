package inter;

import java.io.Serializable;
import java.util.ArrayList;

//这是一个图片信息累用于中间传输,包含了图片二进制流,图片名称,以及图片的评论信息
public class Pic implements Serializable{
	private String picname;//图片名称
	private String path;//所属相册路径
	private ArrayList<Comment> comms;//评论信息
	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ArrayList<Comment> getComms() {
		return comms;
	}
	public void setComms(ArrayList<Comment> comms) {
		this.comms = (ArrayList<Comment>) comms.clone();
	}
}
