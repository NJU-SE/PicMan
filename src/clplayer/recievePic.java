package clplayer;
/*
 *  ����˳��ͼƬ����
 *           ͼƬ����
 *           ͼƬ����
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
            //���ر���·�����ļ������Զ��ӷ������˼̳ж�����
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
            System.out.println("�ļ��ĳ���Ϊ:" + len + "\n");
            System.out.println("��ʼ�����ļ�!" + "\n");
                    
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
                //�����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
               // System.out.println("�ļ�������" +  (passedlen * 100/ len) + "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("������ɣ��ļ���Ϊ" +savePath + "\n");
            fileOut.close();
            //JButton jbtShow = new JButton(new ImageIcon(savePath));
            //jbtShow.setPreferredSize(new Dimension(200,100));
           // JOptionPane.show(jbtShow,"�Ѿ��д��û�����������ע�ᣡ");
            showImage shoMyPic = new showImage(savePath);
           
        } catch (Exception e) {
            System.out.println("������Ϣ����" + "\n");
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


