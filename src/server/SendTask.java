package server;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;

//import DataBase.DictionaryManager;
import database.UserManager;
import inter.*;

public class SendTask extends Task implements Runnable{
	private Socket socket;
	private ArrayList<Message> msgBox;
	private ObjectOutputStream toTarget;
	
	public SendTask(final Socket userSocket, ArrayList<Message> userMsgBox){
		socket = userSocket;
		
		this.msgBox = userMsgBox;
		try {
			toTarget =  new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			toTarget = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public Task[] taskCore() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean needExecuteImmediate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void run(){
		
		while(true){
			
			try {
				if(socket.isClosed())
					break;

				synchronized(msgBox){
					if(!msgBox.isEmpty()){
						//System.out.println("server send task is sending");
					
					
						//socket.setSendBufferSize(2);
						toTarget.writeObject(msgBox.get(0));
						//if(msgBox.get(0).type == Message.UPDATE_FRIEND_ONLINE){
							//System.out.println("when send  the friend list :!!!!!!!!!!!!!!!!!!!!!!");
							//System.out.println(((Message.OnlineFriend)msgBox.get(0).data).friendList.size());
						//}
					//	byte[] gb = new byte[10000];
					//	socket.getOutputStream().write(gb);
					//	System.out.println("ip:"+socket.getInetAddress().getHostAddress()+"port:"+socket.getPort());
					//	socket.connect(new SocketAddress(socket.getLocalAddress().getHostAddress(),socket.getPort() - 1));
						toTarget.flush();
						
						//socket.getOutputStream().flush();
						//System.out.println("the msg id:" + msgBox.get(0).id);
						msgBox.remove(0);
						
					
					
						//System.out.println("server send task is sended");
					//toTarget.close();
				
					}
				}
			} catch (IOException e) {
					// TODO Auto-generated catch block
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		
	}

	
}
