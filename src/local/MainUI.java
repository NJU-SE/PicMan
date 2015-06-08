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
    ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>();        //这里定义N个小的面板，上面存放标签，下面用于存放文件名的文本框
    ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>();        //这里定义N个小的标签，用来存放读取的图片
    ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>();  //这里定义N个文本框，用来显示与图片相对应的文件的名称
    JScrollPane BigScrollPane;             //这里定义一个滚动条，把大的面板放在滚动条里
    ArrayList<File> ClickedFilePath = new ArrayList<File>();      //这里定义了一个，鼠标点击的文件路径下的所有文件
    int ImagesQuantity;                                         //这里定义的是图片的总数
    int SelectImage = -1;                                    //这里定义的是选择的图片，0为第一张，-1是未选择
    JFrame IntroduceFrame = new JFrame();                        //帮助里的关于介绍软件作者而弹出的面板
    JTextArea IntroduceTextArea = new JTextArea();                //同上，介绍软件作者的文本域，被加在了面板上
    JPopupMenu PopupMenu = new JPopupMenu();                      //右键单击文件时弹出的弹出式菜单
    JMenuItem Copy = new JMenuItem(" 复制 ");                    // 菜单中的复制选项
    JMenuItem Delete = new JMenuItem(" 删除 ");              // 菜单中的删除选项
    JMenuItem Cut = new JMenuItem(" 剪切 ");                  //菜单中的剪切选项
    JMenuItem Rename = new JMenuItem(" 重命名 ");                  //菜单中的重命名选项
    JPanel ImagePanel = new JPanel();
    String FilePath;
    MouseEvent E;
    File TemporaryFile;
    ImageIcon TemporaryIcon;
    String OldName;
    JPopupMenu OutPopupMenu = new JPopupMenu();
    JMenuItem Refresh = new JMenuItem("刷新");
    JMenuItem Paste = new JMenuItem("粘贴");
    JMenuItem BatchRename = new JMenuItem("批量重命名");
    ArrayList<BufferedInputStream> SourceFile = new ArrayList<BufferedInputStream>();
    ArrayList<FileOutputStream> NewFile = new ArrayList<FileOutputStream>();
    ArrayList<TreePath> TreePaths = new ArrayList<TreePath>();
    String SourceFileName = null;
    int CopyNum = 0;
    int PasteNumber = 0;
    String CopyName;
    String CopyPath;
    int CutFlag = 0;

    /** 创建主界面*/
    public MainUI() {
        initComponents();
    }
    
    /** 初始化介绍面板 */
    public void InitIntroduction(){
    	
    }
    
    /** 初始化数据(面板上内容等)*/
    public void Init() {
    
    }
    
    /** 路径回退*/
    public void Back() {
    
    }
    
    /** 路径前进*/
    public void Next() {
    
    }
    
    /** */
    public void Up() {
    }
    
    /** */
    public void BatchRename() {
    
    }
    
    /** */
    //TODO: 将throw 更改为 try catch
    public void Paste() throws FileNotFoundException {
    
    }
    
    /** 复制2*/
    public void CopyTwo() throws IOException {
    
    }
    
    /** 复制*/
    public void Copy() throws IOException {
    
    }
    
    /** 剪切*/
    public void Cut() throws IOException {
    
    }
    
    /** 右键点击的事件*/
    public void OutPopupMenu(MouseEvent evt) {
        if (evt.isPopupTrigger()) {                          //判断鼠标的点击是否为右键的点击
            OutPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());         //弹出式菜单在此时弹出，并设置好了其弹出的位置
        }
    }

    /** 删除*/
    public void Delete() {
    
    }
    
    /** 重命名*/
    public void Rename() {
    
    }
    
    /** 重命名中。。。对文字修改*/
    public void RenameText() throws IOException {
    
    }
    
    /** 获取一个图标(缩略图)*/
    public ImageIcon GetImageIcon(ImageIcon imageicon) {
    
    }
    
    /** Menu*/
    public void PopupMenu(MouseEvent evt) {
    
    }
    
    /** 标签上的事件监听*/
    public void InitLabelListener() {
    
    }
    
    /** 打开函数..(双击打开)*/
    public void Open() {
    
    }
    
    /** */
    public void RunTree(final JTree jTree1) {
    
    }
    
    /** 展示图片*/
    public void ShowImages(MouseEvent e, TreePath path, int FlagTree) {
    
    }
    
    
    /** 对每个组件进行初始化*/
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
        IntroduceTextArea.setText("\n      制作人:第九组\n" );
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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//尝试使用系统外观
        } catch (Exception e) {
            System.err.println("不能使用系统外观");
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
