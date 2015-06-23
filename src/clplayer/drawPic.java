package clplayer;

import java.io.*;

import com.sun.image.codec.jpeg.*;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;


public class drawPic{
    public void main(String[]args) throws Exception{
        
        //1.jpg����� ��ͼƬ��·��
        //InputStream isBig = new FileInputStream("./src/image/bg.jpg");
    	System.out.println(" drawing pic");
    	InputStream isBig = new FileInputStream(args[1]);
        //ͨ��JPEGͼ��������JPEG������������
        JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(isBig);
        //���뵱ǰJPEG������������BufferedImage����
        BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
        //�õ����ʶ���
        Graphics gBig = buffImg.getGraphics();
        
        //������Ҫ���ӵ�ͼ��
        //2.jpg�����СͼƬ��·��
        //ImageIcon imgIcon = new ImageIcon("./src/image/baidu.jpg");
        ImageIcon imgIcon = new ImageIcon(args[2]);
        //�õ�Image����
        Image img = imgIcon.getImage();
        
        //��СͼƬ�浽��ͼƬ�ϡ�
        //5,300 .��ʾ���СͼƬ�ڴ�ͼƬ�ϵ�λ�á�
        gBig.drawImage(img,5,20,null);
        //������ɫ��
        gBig.setColor(Color.BLACK);
        //���һ������������������Ĵ�С
        Font f = new Font("����",Font.BOLD,30);
        gBig.setFont(f);
        //10,20 ��ʾ���������ͼƬ�ϵ�λ��(x,y) .��һ���������õ����ݡ�
        //gBig.drawString("Hello",100,150);
        String []printWord = args[0].split("\n");
        f = new Font("����",Font.BOLD,20);
        gBig.setFont(f);
        gBig.drawString("���Ժ��ѣ�",140,30);
        gBig.drawString(printWord[2],150,60);
        f = new Font("����",Font.BOLD,30);
        gBig.drawString(printWord[0],120,130);
         f = new Font("����",Font.ITALIC,15);
         gBig.setFont(f);
         String explain[] = printWord[1].split("[��������,.;:]");
         int index = 0;
         for(int i = 0; i<explain.length&&i<=4;i++)
         {
            gBig.drawString(explain[i],20,160+index);
            index+=18;
         }
        
        //gBig.drawString(printWord[2],30,190);
        gBig.dispose();
        //OutputStream os = new FileOutputStream("union.jpg");
        OutputStream os = new FileOutputStream(args[3]);
        
     
        //���������������ڱ����ڴ��е�ͼ������
        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
        en.encode(buffImg);
        os.close();
        System.out.println ("�ϳɽ���!");
        
        
    }    
    
}
