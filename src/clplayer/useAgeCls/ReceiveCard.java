package clplayer.useAgeCls;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

/*import wordcard.WordCard;
import net.Message.Message;*/
import inter.Message;

public class ReceiveCard implements Runnable{

	private Socket socket;
	private JButton msgButton;
	private JList msgList;
	private ArrayList<Message> msgBox;
	public ReceiveCard(Socket socket, JButton msgButton, JList msgList, ArrayList<Message> msgBox){
		this.socket = socket;
		this.msgButton = msgButton;
		this.msgList = msgList;
		this.msgBox = msgBox;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectInputStream cardStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			Message msg = (Message)cardStream.readObject();
			synchronized(msgBox){
				msgBox.add(msg);
			}
			//WordCard wCard = new WordCard(((Message.Send_Card)msg.data).card);
			//wCard.display();
			//JOptionPane.showMessageDialog(null,
			//	       "接收成功", "系统信息", JOptionPane.ERROR_MESSAGE);
			DefaultListModel<Message> dlist = new DefaultListModel<Message>();
			dlist.removeAllElements();
			synchronized(msgBox){
				for(Message m:msgBox){
					dlist.addElement(m);
				}
			}
			msgList.setModel(dlist);
			msgButton.setText(msgBox.size() + " Message");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
