package clplayer;
/*
 * 发送顺序：图片名字
 *          图片长度
 *          图片内容
 */
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class sendPic {
    int port = 8821;

    public void main(DataOutputStream toServer,String sendPicPath) {
       // Socket s = toServer;
        try {
            
           
                // 选择进行传输的文件
                String filePath = sendPicPath;
                File fi = new File(filePath);

                System.out.println("文件长度:" + (int) fi.length());

                //System.out.println("建立socket链接");
               //DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                //dis.readByte();

                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                DataOutputStream ps = toServer;
                //将文件名及长度传给客户端。这里要真正适用所有平台，例如中文名的处理，还需要加工，具体可以参见Think In Java 4th里有现成的代码。
                System.out.println(fi.getName());
                ps.writeUTF(fi.getName());
                ps.flush();
                ps.writeLong((long) fi.length());
                ps.flush();

                int bufferSize = 10000;
                byte[] buf = new byte[bufferSize];
                if(fis != null)
                while (true) {
                    int read = 0;
                    read = fis.read(buf);
                    if (read == -1) {
                        break;
                    }
                    ps.write(buf, 0, read);
                    System.out.println("."+read);
                }
                ps.flush();
                // 注意关闭socket链接哦，不然客户端会等待server的数据过来，
                // 直到socket超时，导致数据不完整。                
                fis.close();
                               
                System.out.println("图片文件传输完成");
         

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
