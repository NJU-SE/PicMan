package local;

import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreePath;

public class MainUI extends javax.swing.JFrame{
    ArrayList<File> ImageFiles = new ArrayList<File>();
    ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>();        //���ﶨ��N��С����壬�����ű�ǩ���������ڴ���ļ������ı���
    ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>();        //���ﶨ��N��С�ı�ǩ��������Ŷ�ȡ��ͼƬ
    ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>();  //���ﶨ��N���ı���������ʾ��ͼƬ���Ӧ���ļ�������
    JScrollPane BigScrollPane;             //���ﶨ��һ�����������Ѵ�������ڹ�������
    ArrayList<File> ClickedFilePath = new ArrayList<File>();      //���ﶨ����һ������������ļ�·���µ������ļ�
    int ImagesQuantity;                                         //���ﶨ�����ͼƬ������
    int SelectImage = -1;                                    //���ﶨ�����ѡ���ͼƬ��0Ϊ��һ�ţ�-1��δѡ��
    JFrame IntroduceFrame = new JFrame();                        //������Ĺ��ڽ���������߶����������
    JTextArea IntroduceTextArea = new JTextArea();                //ͬ�ϣ�����������ߵ��ı��򣬱������������
    JPopupMenu PopupMenu = new JPopupMenu();                      //�Ҽ������ļ�ʱ�����ĵ���ʽ�˵�
    JMenuItem Copy = new JMenuItem(" ���� ");                    // �˵��еĸ���ѡ��
    JMenuItem Delete = new JMenuItem(" ɾ�� ");              // �˵��е�ɾ��ѡ��
    JMenuItem Cut = new JMenuItem(" ���� ");                  //�˵��еļ���ѡ��
    JMenuItem Rename = new JMenuItem(" ������ ");                  //�˵��е�������ѡ��
    JPanel ImagePanel = new JPanel();
    String FilePath;
    MouseEvent E;
    File TemporaryFile;
    ImageIcon TemporaryIcon;
    String OldName;
    JPopupMenu OutPopupMenu = new JPopupMenu();
    JMenuItem Refresh = new JMenuItem("ˢ��");
    JMenuItem Paste = new JMenuItem("ճ��");
    JMenuItem BatchRename = new JMenuItem("����������");
    ArrayList<BufferedInputStream> SourceFile = new ArrayList<BufferedInputStream>();
    ArrayList<FileOutputStream> NewFile = new ArrayList<FileOutputStream>();
    ArrayList<TreePath> TreePaths = new ArrayList<TreePath>();
    String SourceFileName = null;
    int CopyNum = 0;
    int PasteNumber = 0;
    String CopyName;
    String CopyPath;
    int CutFlag = 0;

    /** ����������*/
    public MainUI() {
        initComponents();
    }
    
    /** ��ʼ��������� */
    public void InitIntroduction(){
    	
    }
    
    /** ��ʼ������(��������ݵ�)*/
    public void Init() {
    
    }
    
    /** ·������*/
    public void Back() {
    
    }
    
    /** ·��ǰ��*/
    public void Next() {
    
    }
    
    /** */
    public void Up() {
    }
    
    /** */
    public void BatchRename() {
    
    }
    
    /** */
    //TODO: ��throw ����Ϊ try catch
    public void Paste() throws FileNotFoundException {
    
    }
    
    /** ����2*/
    public void CopyTwo() throws IOException {
    
    }
    
    /** ����*/
    public void Copy() throws IOException {
    
    }
    
    /** ����*/
    public void Cut() throws IOException {
    
    }
    
    /** �Ҽ�������¼�*/
    public void OutPopupMenu(MouseEvent evt) {
        if (evt.isPopupTrigger()) {                          //�ж����ĵ���Ƿ�Ϊ�Ҽ��ĵ��
            OutPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());         //����ʽ�˵��ڴ�ʱ�����������ú����䵯����λ��
        }
    }

    /** ɾ��*/
    public void Delete() {
    
    }
    
    /** ������*/
    public void Rename() {
    
    }
    
    /** �������С������������޸�*/
    public void RenameText() throws IOException {
    
    }
    
    /** ��ȡһ��ͼ��(����ͼ)*/
    public ImageIcon GetImageIcon(ImageIcon imageicon) {
    
    }
    
    /** Menu*/
    public void PopupMenu(MouseEvent evt) {
    
    }
    
    /** ��ǩ�ϵ��¼�����*/
    public void InitLabelListener() {
    
    }
    
    /** �򿪺���..(˫����)*/
    public void Open() {
    
    }
    
    /** */
    public void RunTree(final JTree jTree1) {
    
    }
    
    /** չʾͼƬ*/
    public void ShowImages(MouseEvent e, TreePath path, int FlagTree) {
    
    }
    
    
    /** ��ÿ��������г�ʼ��*/
    private void initComponents() {
    
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    	Open();
    }
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    	System.exit(0);
    }
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        IntroduceFrame.setVisible(true);
        IntroduceTextArea.setText("\n      ������:�ھ���\n" );
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jComboBox1.getSelectedIndex() > 0) {
            Back();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jComboBox1.getSelectedIndex() + 1 < TreePaths.size()) {
            Next();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Up();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ShowImages(E, new TreePath(0), 0);
    }//GEN-LAST:event_jButton4ActionPerformed

    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Delete();
    }//GEN-LAST:event_jButton5ActionPerformed

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//����ʹ��ϵͳ���
        } catch (Exception e) {
            System.err.println("����ʹ��ϵͳ���");
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MainUI zhu = new MainUI();
                zhu.Init();
                zhu.setLocationRelativeTo(null);
                zhu.setVisible(true);
            }
        });
    } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**       // Variables declaration - do not modify//GEN-BEGIN:variables*/
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
