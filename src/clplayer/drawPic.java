package clplayer;

import java.io.*;

import com.sun.image.codec.jpeg.*;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;


public class drawPic{
    public void main(String[]args) throws Exception{
        
        //1.jpg是你的 主图片的路径
        //InputStream isBig = new FileInputStream("./src/image/bg.jpg");
    	System.out.println(" drawing pic");
    	InputStream isBig = new FileInputStream(args[1]);
        //通过JPEG图象流创建JPEG数据流解码器
        JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(isBig);
        //解码当前JPEG数据流，返回BufferedImage对象
        BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
        //得到画笔对象
        Graphics gBig = buffImg.getGraphics();
        
        //创建你要附加的图象。
        //2.jpg是你的小图片的路径
        //ImageIcon imgIcon = new ImageIcon("./src/image/baidu.jpg");
        ImageIcon imgIcon = new ImageIcon(args[2]);
        //得到Image对象。
        Image img = imgIcon.getImage();
        
        //将小图片绘到大图片上。
        //5,300 .表示你的小图片在大图片上的位置。
        gBig.drawImage(img,5,20,null);
        //设置颜色。
        gBig.setColor(Color.BLACK);
        //最后一个参数用来设置字体的大小
        Font f = new Font("宋体",Font.BOLD,30);
        gBig.setFont(f);
        //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        //gBig.drawString("Hello",100,150);
        String []printWord = args[0].split("\n");
        f = new Font("宋体",Font.BOLD,20);
        gBig.setFont(f);
        gBig.drawString("来自好友：",140,30);
        gBig.drawString(printWord[2],150,60);
        f = new Font("宋体",Font.BOLD,30);
        gBig.drawString(printWord[0],120,130);
         f = new Font("楷体",Font.ITALIC,15);
         gBig.setFont(f);
         String explain[] = printWord[1].split("[，。；：,.;:]");
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
        
     
        //创键编码器，用于编码内存中的图象数据
        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
        en.encode(buffImg);
        os.close();
        System.out.println ("合成结束!");
        
        
    }    
    
}
