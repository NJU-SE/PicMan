package local;


import MainUI;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
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
        IntroduceFrame.setVisible(false);
        IntroduceFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//����ʱ��ֻ���������
        IntroduceFrame.setSize(220, 130);
        IntroduceFrame.setLocationRelativeTo(null);      //�������ʱ����Ļ������
        IntroduceFrame.add(IntroduceTextArea);
        IntroduceTextArea.setEditable(false);            //�ı���Ϊ���ɱ༭
    	
    }
    
    /** ��ʼ������(��������ݵ�)*/
    public void Init() {


        InitIntroduction();
        BigScrollPane = new JScrollPane(ImagePanel);         //��������������ʾ������ʾͼ��Ĵ����
        ImagePanel.setLayout(null);                             //ͼ�����Ĳ�����Ϊnull�����ǳ���Ҫ��
        jTabbedPane1.add(BigScrollPane);                    //�ڱ�ǩ�������м�������ͼ�����Ĺ������
        PopupMenu.add(Copy);                                 //����ʽ�����м��븴�Ʋ˵���
        //jPopupMenu.addSeparator();                         // ���˵��мӺ���
        PopupMenu.add(Cut);                                 //����ʽ�����м�����в˵���
        PopupMenu.add(Delete);                             //����ʽ�����м���ɾ���˵���
        PopupMenu.add(Rename);                              //����ʽ�����м����������˵���
        OutPopupMenu.add(Refresh);
        OutPopupMenu.add(Paste);
        OutPopupMenu.add(BatchRename);
        jComboBox1.addPopupMenuListener(new PopupMenuListener() {

            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex()));
                ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex()), 1);
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
                System.out.println("����3");
            }
        });

        ImagePanel.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseReleased(MouseEvent evt) {
                OutPopupMenu(evt);
            }
        });

        BatchRename.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                BatchRename();
            }
        });

        Delete.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete();
            }
        });

        Rename.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rename();
            }
        });

        Copy.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    Copy();
                } catch (IOException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Cut.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Cut();
                } catch (IOException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Refresh.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowImages(E, new TreePath(0), 0);
            }
        });

        Paste.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    Paste();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


    
    }
    
    /** ·������*/
    public void Back() {

        jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex() - 1));
        ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex() - 1), 1);
        jScrollPane1.getVerticalScrollBar().setValue((int) (jTree1.getRowHeight() * jTree1.getMaxSelectionRow()));
    
    }
    
    /** ·��ǰ��*/
    public void Next() {

        jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex() + 1));
        ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex() + 1), 1);
        jScrollPane1.getVerticalScrollBar().setValue((int) (jTree1.getRowHeight() * jTree1.getMaxSelectionRow()));
    
    }
    
    /** */
    public void Up() {
        if (jTree1.getSelectionPath().getParentPath() != null) {
            jTree1.setSelectionPath(jTree1.getSelectionPath().getParentPath());
            System.out.println(jTree1.getMaxSelectionRow());
            for (int i = jTree1.getRowCount(); i >= jTree1.getMaxSelectionRow(); i--) {
                jTree1.collapseRow(i);
            }
        }
    
    }
    
    /** */
    public void BatchRename() {
        //ImagePanel.removeAll();
        int num = 0;
        File file = new File(FilePath);
        File[] files = file.listFiles();
        ArrayList<File> filesA = new ArrayList<File>();
        ArrayList<ImageIcon> ImageIcons = new ArrayList<ImageIcon>();
        for (int i = 0; i < files.length; i++) {
            String ext = files[i].getName().substring(
                    files[i].getName().lastIndexOf("."),
                    files[i].getName().length()).toLowerCase();
            if (ext.equals(".jpg") || ext.equals(".gif") || ext.equals(".bmp")) {
                filesA.add(files[i]);
            }
        }
        for (int i = 0; i < filesA.size(); i++) {
            if (!filesA.get(i).renameTo(filesA.get(i))) {
                //ImageIcons.add(GetImageIcon(new ImageIcon(filesA.get(i).getAbsolutePath())));
                SmallLabels.get(i).setIcon(null);
            }
        }

        String string = JOptionPane.showInputDialog(null, "�������µ�����(��������׺)", "����������", JOptionPane.INFORMATION_MESSAGE);

        for (int i = 0; i < filesA.size(); i++) {
            String axt = filesA.get(i).getName().substring(
                    filesA.get(i).getName().lastIndexOf("."),
                    filesA.get(i).getName().length()).toLowerCase();
            System.out.println(filesA.get(i));
            int j = i + 1;
            if (SmallLabels.get(i).getIcon() == null) {
                filesA.get(i).renameTo(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
                ImageIcons.add(GetImageIcon(new ImageIcon(FilePath + File.separator + string + "(" + j + ")" + axt)));
                SmallTextFields.get(i).setText(string + "(" + j + ")" + axt);
                ClickedFilePath.add(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
            } else {
                filesA.get(i).renameTo(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
                SmallTextFields.get(i).setText(string + "(" + j + ")" + axt);
                ClickedFilePath.add(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
            }
        }
        for (int i = 0; i < filesA.size(); i++) {
            if (SmallLabels.get(i).getIcon() == null) {
                SmallLabels.get(i).setIcon(ImageIcons.get(num));
                num++;
            }
        }
    }
    
    /** */
    //TODO: ��throw ����Ϊ try catch
    public void Paste() throws FileNotFoundException {

        int flag = 0;
        int PasteNum = 0;
        int FlagName = 0;
        if (CutFlag == 1) {
            for (int i = 0; i < ImageFiles.size(); i++) {
                if (SmallTextFields.get(i).getText().equals(SourceFileName)) {
                    FlagName = 1;
                }


            }
            if (FlagName == 1) {
                JOptionPane.showMessageDialog(null,
                        "��Ŀ¼�´�����ͬ���ֵ��ļ������ܽ���ճ������!!!",
                        "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (CopyPath == FilePath) {
                    JOptionPane.showMessageDialog(null,
                            "�޷���ͬһĿ¼���м��к�ճ������!!!",
                            "ERROR", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    NewFile.add(new FileOutputStream(new File(FilePath + File.separator + SourceFileName)));
                    byte[] content = new byte[1000];
                    int size = 0;

                    try {
                        while ((size = SourceFile.get(CopyNum - 1).read(content)) != -1) {
                            NewFile.get(PasteNumber).write(content, 0, size);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        NewFile.get(PasteNumber).close();
                        SourceFile.get(CopyNum - 1).close();
                    } catch (IOException ex) {
                    }
                    PasteNumber++;
                    ImageIcon ic1 = new ImageIcon(FilePath + File.separator + SourceFileName);
                    double h1 = ic1.getIconHeight();
                    double w1 = ic1.getIconWidth();
                    if (h1 < 77 && w1 < 100) {
                        Image im = ic1.getImage().getScaledInstance((int) w1, (int) h1, Image.SCALE_DEFAULT);//�ı��С
                        ImageIcon ic2 = new ImageIcon(im);//���µõ�һ���̶�ͼƬ
                        SmallLabels.add(new JLabel());
                        SmallTextFields.add(new JTextField());
                        SmallLabels.get(SmallLabels.size() - 1).setIcon(ic2);
                        SmallTextFields.get(SmallLabels.size() - 1).setText(SourceFileName);

                    } else {
                        if (h1 * 180 > w1 * 142) {
                            Image im = ic1.getImage().getScaledInstance((int) (w1 / (h1 / 81)), 81, Image.SCALE_DEFAULT);//�ı��С
                            ImageIcon ic2 = new ImageIcon(im);
                            SmallLabels.add(new JLabel());
                            SmallTextFields.add(new JTextField());
                            SmallLabels.get(SmallLabels.size() - 1).setIcon(ic2);
                            SmallTextFields.get(SmallLabels.size() - 1).setText(SourceFileName);
                        } else {
                            Image im = ic1.getImage().getScaledInstance(105, (int) (h1 / (w1 / 105)), Image.SCALE_DEFAULT);//�ı��С
                            ImageIcon ic2 = new ImageIcon(im);
                            SmallLabels.add(new JLabel());
                            SmallTextFields.add(new JTextField());
                            SmallLabels.get(SmallLabels.size() - 1).setIcon(ic2);
                            SmallTextFields.get(SmallLabels.size() - 1).setText(SourceFileName);
                        }
                        SmallPanels.add(new JPanel());
                        int j = SmallLabels.size() - 1;
                        ImagePanel.add(SmallPanels.get(j));
                        ImageFiles.add(new File(FilePath + File.separator + SourceFileName));
                        
                        if (ImageFiles.size() > 20) {
                            SmallPanels.get(j).setBounds(j % 5 * 131, 1 + (j / 5 * 125), 120, 110);
                        } else {
                            SmallPanels.get(j).setBounds(j % 5 * 135, 1 + (j / 5 * 125), 120, 110);
                        }
                        SmallPanels.get(j).setLayout(new java.awt.BorderLayout(0, 0));
                        SmallPanels.get(j).add(SmallLabels.get(j), java.awt.BorderLayout.CENTER);
                        SmallPanels.get(j).add(SmallTextFields.get(j), java.awt.BorderLayout.PAGE_END);
                        SmallTextFields.get(j).setBorder(null);
                        SmallTextFields.get(j).setHorizontalAlignment(SwingConstants.CENTER);
                        SmallTextFields.get(j).setEditable(false);
                        if (j == 0) {
                            SmallLabels.get(0).setDisplayedMnemonic(501);
                        } else {
                            SmallLabels.get(j).setDisplayedMnemonic(j);
                        }
                        SmallLabels.get(j).setHorizontalAlignment(SwingConstants.CENTER);
                        SmallLabels.get(j).setOpaque(true);
                        SmallLabels.get(j).setBackground(new java.awt.Color(244, 244, 244));
                        if (new File(CopyPath + File.separator + SourceFileName).delete()) {
                            JOptionPane.showMessageDialog(null,
                                    "���в����ѳɹ����!!!",
                                    "ERROR", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Դ�ļ�������һ������ʹ���У����ܱ�ɾ��!!!",
                                    "ERROR", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        } else {
            String SourceFileNameFront = SourceFileName.substring(0,
                    SourceFileName.indexOf(".")).toLowerCase();

            if (SourceFileName.indexOf("(") != -1) {
                SourceFileNameFront = SourceFileName.substring(0,
                        SourceFileName.indexOf("(")).toLowerCase();
            }
            System.out.println((String) (SourceFileNameFront) + "aabcdefg");

            String SourceFileNameLast = SourceFileName.substring(
                    SourceFileName.lastIndexOf("."),
                    SourceFileName.length()).toLowerCase();
            for (int size = 0; size < SmallTextFields.size(); size++) {
                if (SourceFileNameFront.equals(SmallTextFields.get(size).getText().substring(0,
                        SmallTextFields.get(size).getText().indexOf(".")).toLowerCase())) {
                    PasteNum = 2;

                }


            }
            for (int size = 0; size < SmallTextFields.size(); size++) {


                if (new String(SourceFileNameFront + "(" + PasteNum + ")").equals(SmallTextFields.get(size).getText().substring(0,
                        SmallTextFields.get(size).getText().indexOf(".")).toLowerCase())) {
                    PasteNum++;
                    size = 0;
                }
            }

            if (PasteNum != 0) {
                NewFile.add(new FileOutputStream(new File(FilePath + File.separator + SourceFileNameFront + "(" + PasteNum + ")" + SourceFileNameLast)));
            } else {
                NewFile.add(new FileOutputStream(new File(FilePath + File.separator + SourceFileNameFront + SourceFileNameLast)));
            }

            try {
                CopyTwo();
            } catch (IOException e) {
            }
            byte[] content = new byte[1000];
            int size = 0;

            try {
                while ((size = SourceFile.get(CopyNum - 1).read(content)) != -1) {
                    NewFile.get(PasteNumber).write(content, 0, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ShowImages(E, new TreePath(0), 0);
            try {
                NewFile.get(PasteNumber).close();
            } catch (IOException ex) {
            }
            PasteNumber++;


        }

    
    
    }
    
    /** ����2*/
    public void CopyTwo() throws IOException {
        if (CopyNum != 0) {
            try {
                SourceFile.get(CopyNum - 1).close();
            } catch (IOException ex) {
            }
        }

        try {
            SourceFile.add(new BufferedInputStream(new FileInputStream(CopyPath + File.separator + CopyName)));

            SourceFileName = CopyName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CopyNum++;

    
    
    }
    
    /** ����*/
    public void Copy() throws IOException {
        CutFlag = 0;
        if (CopyNum != 0) {
            try {
                SourceFile.get(CopyNum - 1).close();
            } catch (IOException ex) {
            }
        }
        CopyName = SmallTextFields.get(SelectImage).getText();
        CopyPath = FilePath;
        try {
            SourceFile.add(new BufferedInputStream(new FileInputStream(FilePath + File.separator + SmallTextFields.get(SelectImage).getText())));

            SourceFileName = SmallTextFields.get(SelectImage).getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CopyNum++;

    
    
    }
    
    /** ����*/
    public void Cut() throws IOException {
        if (CopyNum != 0) {
            try {
                SourceFile.get(CopyNum - 1).close();
            } catch (IOException ex) {
            }
        }
        CopyPath = FilePath;
        try {
            SourceFile.add(new BufferedInputStream(new FileInputStream(FilePath + File.separator + SmallTextFields.get(SelectImage).getText())));
            SourceFileName = SmallTextFields.get(SelectImage).getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CopyNum++;
        CutFlag = 1;
    
    
    }
    
    /** �Ҽ�������¼�*/
    public void OutPopupMenu(MouseEvent evt) {
        if (evt.isPopupTrigger()) {                          //�ж����ĵ���Ƿ�Ϊ�Ҽ��ĵ��
            OutPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());         //����ʽ�˵��ڴ�ʱ�����������ú����䵯����λ��
        }
    }

    /** ɾ��*/
    public void Delete() {
        SmallLabels.get(SelectImage).setIcon(null);
        if (JOptionPane.showConfirmDialog(null, "��ȷ��Ҫɾ��" + SmallTextFields.get(SelectImage).getText() + "��?") == JOptionPane.YES_OPTION) {
            if (new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText()).delete()) {
                if (ImagesQuantity - 1 > 20) {
                    SmallPanels.get(SelectImage).setBounds(3000, 1, 0, 0);
                    SmallPanels.remove(SelectImage);
                    SmallTextFields.remove(SelectImage);
                    SmallLabels.remove(SelectImage);
                    SmallPanels.trimToSize();
                    SmallLabels.trimToSize();
                    SmallTextFields.trimToSize();
                    for (int i = SelectImage; i < SmallPanels.size(); i++) {
                        System.out.println("size" + SmallPanels.size());
                        SmallPanels.get(i).setBounds((i) % 5 * 135, 1 + ((i) / 5 * 125), 120, 110);
                    }
                } else {
                    SmallPanels.get(SelectImage).setBounds(3000, 1, 0, 0);
                    SmallPanels.remove(SelectImage);
                    SmallTextFields.remove(SelectImage);
                    SmallLabels.remove(SelectImage);
                    SmallPanels.trimToSize();
                    SmallLabels.trimToSize();
                    SmallTextFields.trimToSize();
                    for (int i = SelectImage; i < SmallPanels.size(); i++) {
                        System.out.println("size" + SmallPanels.size());
                        SmallPanels.get(i).setBounds((i) % 5 * 135, 1 + ((i) / 5 * 125), 120, 110);
                    }
                }
                ImagesQuantity--;
            } else {
                JOptionPane.showMessageDialog(null,
                        "�ļ�������һ������ʹ���У��޷�����ɾ������!!!",
                        "ERROR", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    
    
    }
    
    /** ������*/
    public void Rename() {
        Robot mRobot = null;
        try {
            mRobot = new Robot();
        } catch (java.awt.AWTException awe) {
            System.err.println("new   Robot   error");
        }
        TemporaryFile = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());
        Point point = new Point();
        point = SmallTextFields.get(SelectImage).getLocationOnScreen();
        mRobot.mouseMove(point.x + 50, point.y + 5);//����ƶ�
        SmallTextFields.get(SelectImage).setEditable(true);
        OldName = (String) SmallTextFields.get(SelectImage).getText();
        SmallTextFields.get(SelectImage).setBackground(Color.white);
        TemporaryIcon = (ImageIcon) SmallLabels.get(SelectImage).getIcon();
        if (!TemporaryFile.renameTo(TemporaryFile)) {
            SmallLabels.get(SelectImage).setIcon(null);
        }
        SmallTextFields.get(SelectImage).addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    RenameText();
                } catch (IOException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    
    
    }
    
    /** �������С������������޸�*/
    public void RenameText() throws IOException {
        File filetwo = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());

        //Runtime.getRuntime().exec("cmd /c RENAME " + TemporaryFile.getPath() + " " + SmallTextFields.get(SelectImage).getText());      
        /*System.out.println(filetwo);
        System.out.println("cmd /c ren " + TemporaryFile.getPath() + " " + SmallTextFields.get(SelectImage).getText());
        if (!TemporaryFile.renameTo(filetwo)) {
        BufferedInputStream BIS = new BufferedInputStream(new FileInputStream(TemporaryFile));
        FileOutputStream FOS = new FileOutputStream(filetwo);
        byte[] content = new byte[1000];
        int size = 0;
        while ((size = BIS.read(content)) != -1) {
        FOS.write(content, 0, size);
        }
        BIS.close();
        FOS.close();
        Delete2();
        ShowImages(E, new TreePath(0), 0);}*/
        if (TemporaryFile.renameTo(filetwo)) {
            JOptionPane.showMessageDialog(null,
                    "�����������ɹ�!!!",
                    "������", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null, "�ļ�����������ռ�ã�����������ʧ��", "������", JOptionPane.INFORMATION_MESSAGE);

        }
        SmallTextFields.get(SelectImage).setBackground(null);
        SmallTextFields.get(SelectImage).setEditable(false);
        SmallLabels.get(SelectImage).setIcon(GetImageIcon(new ImageIcon(filetwo.getAbsolutePath())));
        SmallLabels.get(SelectImage).setBackground(new java.awt.Color(244, 244, 244));
    
    
    }
    
    /** ��ȡһ��ͼ��(����ͼ)*/
    public ImageIcon GetImageIcon(ImageIcon imageicon) {
        //File filetwo = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());
        //ImageIcon imageicon = new ImageIcon(filetwo.getAbsolutePath());
        double h1 = imageicon.getIconHeight();
        double w1 = imageicon.getIconWidth();
        if (h1 < 77 && w1 < 100) {
            Image image = imageicon.getImage().getScaledInstance((int) w1, (int) h1, Image.SCALE_DEFAULT);//�ı��С
            ImageIcon Finalii = new ImageIcon(image);//���µõ�һ���̶�ͼƬ
            return Finalii;

        } else {
            if (h1 * 180 > w1 * 142) {
                Image image = imageicon.getImage().getScaledInstance((int) (w1 / (h1 / 81)), 81, Image.SCALE_DEFAULT);//�ı��С
                ImageIcon Finalii = new ImageIcon(image);//���µõ�һ���̶�ͼƬ
                return Finalii;
            } else {
                Image image = imageicon.getImage().getScaledInstance(105, (int) (h1 / (w1 / 105)), Image.SCALE_DEFAULT);//�ı��С
                ImageIcon Finalii = new ImageIcon(image);//���µõ�һ���̶�ͼƬ
                return Finalii;
            }
        }
    
    
    }
    
    /** Menu*/
    public void PopupMenu(MouseEvent evt) {
        if (evt.isPopupTrigger()) {                          //�ж����ĵ���Ƿ�Ϊ�Ҽ��ĵ��
            JLabel SelectLabel = new JLabel();                     //����һ����ʱ�ı�ǩ
            SelectLabel = (JLabel) evt.getSource();                 //��������������ı�ǩ
            PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());         //����ʽ�˵��ڴ�ʱ�����������ú����䵯����λ��

            for (int b = 0; b < SmallLabels.size(); b++) {
                SmallLabels.get(b).setBackground(new java.awt.Color(244, 244, 244));
            }
            JLabel CurrentLabel = new JLabel();
            CurrentLabel = (JLabel) evt.getSource();
            CurrentLabel.setBackground(new java.awt.Color(194, 194, 194));
            for (int y = 0; y < SmallLabels.size(); y++) {
                if (SmallLabels.get(y).getDisplayedMnemonic() == CurrentLabel.getDisplayedMnemonic()) {
                    SelectImage = y;
                }
            }
        }
    
    
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
