package clplayer.useAgeCls;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import inter.Message;

public class ListenCard implements Runnable{
	private ServerSocket listenSocket;
	private JButton msgButton;
	private JList msgList;
	private ArrayList msgBox;
	public ListenCard(int port, JButton msgButton, JList msgList, ArrayList<Message> msgBox){
		this.msgButton = msgButton;
		this.msgList = msgList;
		this.msgBox = msgBox;
		try {
			System.out.println("creating listencard~~~");
			listenSocket = new ServerSocket(port);
			System.out.println("created listencard~~~");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO 閼奉亜濮╅悽鐔稿灇閻ㄥ嫭鏌熷▔鏇炵摠閺嶏拷
		
	}
	
/*	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				System.out.println("running listencard listencard~~~");
				Socket newCard = listenSocket.accept();
				newCard.setTcpNoDelay(true);
				//ReceiveCard reveive = new ReceiveCard(newCard, msgButton, msgList, msgBox);
				//Thread t = new Thread(reveive);
				t.start();
				//JOptionPane.showMessageDialog(null,
				//	       "鐎殿噯鎷峰┑顔碱儐鐢挳宕ｉ敓锟�", "缂侇垵宕电划鐑樼┍閳╁啩绱�", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

}
