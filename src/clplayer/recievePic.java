package clplayer;
/*
 *  接收顺序：图片名字
 *           图片长度
 *           图片内容
 */
import java.awt.Button;
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class recievePic {

    public  void main(DataInputStream fromServer,int numPic){
        
        try {
        	System.out.println("reciving pic...");
        	DataInputStream inputStream = fromServer;
            //本地保存路径，文件名会自动从服务器端继承而来。
            String savePath = "C://Users//liyize//workspace//Lab2_UIClient//src//recievePic//";
            int bufferSize = 10000;
            byte[] buf = new byte[bufferSize];
            int passedlen = 0;
            long len=0;
            String recivedPath  = null;
            //System.out.println(inputStream.readUTF());
            System.out.println("reciving pic path before ");
            recivedPath= numPic+inputStream.readUTF() ;
            savePath +=recivedPath;
            System.out.println("reciving pic " +savePath);
            DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream
            		(new BufferedOutputStream(new FileOutputStream(savePath))));
            len = inputStream.readLong();
            System.out.println("reciving pic " +len);
            System.out.println("文件的长度为:" + len + "\n");
            System.out.println("开始接收文件!" + "\n");
                    
            while (true) {
            	if(passedlen  == len)
					break;
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                passedlen += read;
                if (read == -1) {
                    break;
                }
                //下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
               // System.out.println("文件接收了" +  (passedlen * 100/ len) + "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("接收完成，文件存为" +savePath + "\n");
            fileOut.close();
            //JButton jbtShow = new JButton(new ImageIcon(savePath));
            //jbtShow.setPreferredSize(new Dimension(200,100));
           // JOptionPane.show(jbtShow,"已经有此用户名，请重新注册！");
            showImage shoMyPic = new showImage(savePath);
           
        } catch (Exception e) {
            System.out.println("接收消息错误" + "\n");
            return;
        }
    }
    public static class showImage extends JFrame{
    	
    	public showImage(String path){
    		JButton jbtShow = new JButton(new ImageIcon(path));
    		this.add(jbtShow);
    		setTitle("Client");
    		setSize(600,600);
    		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    		setVisible(true);
    		
    	}
    	
    	public static void main(String Path){
    		new showImage(Path);
    	}
    
    }
}


