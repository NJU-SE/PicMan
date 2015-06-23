package clplayer;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.awt.*;

import javax.swing.*;

public class ChatDeal extends JFrame{
	//text area for displaying content
	Lock lock = new ReentrantLock();
	private JTextArea jta = new JTextArea();
	private boolean isPrint = false;
	private static List<HandleAClient> threadList =new ArrayList<HandleAClient>();//服务器已启用线程集合
	private static LinkedList<String> messageList =new LinkedList<String>();//存放消息队列
	public static void main(String args []){
		new ChatDeal();
	}
	 
	public ChatDeal(){
		//place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
		setTitle("MultiThreadServer");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	    try{
	    	sendMessageToAll sendMessage = new sendMessageToAll();
		    new Thread(sendMessage).start();
	    	ServerSocket serverSocket = new ServerSocket(8000);
	    	jta.append("chatMultiThreadServer started at " + new Date() + '\n' );
	    	int ClientNo = 1;
	    	while(true){
	    		Socket socket = serverSocket.accept();
	    		jta.append(" starting  thread for client  " + ClientNo + "at "  +new Date());
	    		//find the client's host name,and IP address
	    		InetAddress inetAddress = socket.getInetAddress();
	    		jta.append("Client "  + ClientNo + "'s hostname is   " + inetAddress.getHostName());
	    		jta.append("Client "+ClientNo + "'s Ip Adsress is "+inetAddress.getHostAddress()+'\n');
	    		HandleAClient task = new HandleAClient(socket);
	    		new Thread(task).start();
	    		lock.lock();
	    		threadList.add(task);
	    		lock.unlock();
	    		ClientNo++;
	    	}
	    	
	    }
	    catch(IOException ex){
	    	System.out.println(ex);
	    }
	    
	    
	    
	}
	class sendMessageToAll implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				//System.out.println("isempty: "+messageList.isEmpty() + " "+threadList.isEmpty());
				
				if(!messageList.isEmpty() && !threadList.isEmpty() ){
               //将缓存在队列中的消息按顺序发送到各客户端，并从队列中清除。
					lock.lock();
                    String message = messageList.getFirst();
                    for (HandleAClient thread : threadList) {
                    	System.out.println("server send message in for");
                        try {
                        	System.out.println("server send message in  for try ");
							thread.sendMessage(message);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    }
                    messageList.removeFirst();
                    lock.unlock();
				}
				else continue;
               
            }
		}
		
	}
	
	class HandleAClient implements Runnable {
		private Socket socket;//a conneted socket
		
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket = socket;
		}
		
		//run a thread
		public void run(){
			try{
				//creat data output and input streams
				
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				
				while(true)
				{
					String fromClient = inputFromClient.readUTF();
					//System.out.println(fromClient);
					jta.append(fromClient+'\n');
					lock.lock();
					messageList.add(fromClient);
					//System.out.println(fromClient);
					lock.unlock();
					//outputFromClient.writeUTF(fromClient);
					
				}
			}
			
			catch(IOException ex){
				System.out.println(ex);
				
			}
		}
		
		public void sendMessage(String message) throws IOException{
			
			DataOutputStream outputFromClient = new DataOutputStream(socket.getOutputStream());
			outputFromClient.writeUTF(message);
			System.out.println("server send message in handle ");
		}
	}
	
	
	
}