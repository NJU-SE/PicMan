package clplayer.useAgeCls;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import inter.Message;

public class ListenMessage implements Runnable{
	private static Map requestMap;
	private Socket socket;
	private ObjectInputStream objIn;
	private JButton msgButton;
	private JList msgList;
	private ArrayList<Message> msgs;
	public ListenMessage(Map requestMap, final Socket socket, JButton msgButton, JList msgList, ArrayList msgs){
		System.out.println("ctrating listen msg~~");
		this.requestMap = requestMap;
		this.socket = socket;
		this.msgButton = msgButton;
		this.msgList = msgList;
		this.msgs = msgs;
		System.out.println("ctrated listen msg~~");
	}
	@Override
public void run() {

		
		while(true){
			Message msg = null;
			if(socket == null || socket.isClosed())//
				break;
			try{
				System.out.println("listenning msg");

				if(objIn == null)
					objIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));	
				
				msg = (Message)(objIn.readObject());
				//char eof = fromClient.readChar();//閻忓繐妫欓弸鍐╃閸撲胶娉㈤柡澶屽枔椤戜胶鎷犵拠鎻掓瘔闁哄鍎荤槐锟�
				//fromClient.close();
				
				System.out.println("receive a msg");
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				objIn = null;//闂佹彃绉堕悿鍡樻綇閹惧啿寮虫繛杈炬嫹
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			if(msg == null)
				continue;
			/*if(msg.reply){// themsg is a reply
				System.out.println("reply to:"+ msg.id);
				synchronized(requestMap){
					if(requestMap.containsKey(msg.id))//闁哄牆顦卞ù澶嬫償閺冨倹鐣遍悹鍥敱閻即骞嶅鍕獥闁告梻濮撮崣鍡涙晬鐏炶姤鍎婇柛鎺撶懆椤曗晠寮版惔銈庡殙閺夆晜鏌ㄥú鏍ь啅閼碱剛鐥呴悺鎺戞噺濡炲倿鏁嶇仦鍏间涪鐎殿噯鎷�
						requestMap.put(msg.id, msg);
				}
				
			}else{//then it is a request 
				System.out.println("a msg from b: id="+msg.id);
				//msgList.add(name, comp);
				synchronized(msgs){
					msgs.add(msg);
				}
				DefaultListModel<Message> dlist = new DefaultListModel<Message>();
				dlist.removeAllElements();
				synchronized(msgs){
					for(Message m:msgs){
						dlist.addElement(m);
					}
				}
				msgList.setModel(dlist);//闁哄洤鐡ㄩ弻濠囧礆濡ゅ嫨锟藉啯绋夐鐘崇暠闁稿繐鍟扮粈锟�
				synchronized(msgs){
					msgButton.setText(msgs.size() +" Message");
				}
			}*/
		
		}
	
	}
	
}
