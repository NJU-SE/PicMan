package inter;

import java.io.Serializable;

//这是每一条评论信息,包含了id和评论者的uid
public class Comment implements Serializable{
	private int id;//服务器在写入时候指定
	private String uid;
	private String comment;//评论
	
	public Comment(String uid, String comment){
		this.uid = uid;
		this.comment = comment;
	}
	public Comment(String uid, String comment, int id){
		this.uid = uid;
		this.comment = comment;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
 