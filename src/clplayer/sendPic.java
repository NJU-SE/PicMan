package clplayer;
/*
 * ����˳��ͼƬ����
 *          ͼƬ����
 *          ͼƬ����
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
            
           
                // ѡ����д�����ļ�
                String filePath = sendPicPath;
                File fi = new File(filePath);

                System.out.println("�ļ�����:" + (int) fi.length());

                //System.out.println("����socket����");
               //DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                //dis.readByte();

                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                DataOutputStream ps = toServer;
                //���ļ��������ȴ����ͻ��ˡ�����Ҫ������������ƽ̨�������������Ĵ�������Ҫ�ӹ���������Բμ�Think In Java 4th�����ֳɵĴ��롣
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
                // ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����
                // ֱ��socket��ʱ���������ݲ�������                
                fis.close();
                               
                System.out.println("ͼƬ�ļ��������");
         

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
