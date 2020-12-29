package view;

import com.mysql.cj.xdevapi.Table;
import entity.Converter;
import entity.PathLoad;
import framework.BaseTableModule;
import framework.MyFont;
import javafx.beans.binding.ObjectExpression;
import jdk.nashorn.internal.scripts.JO;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import services.PathLoadServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class Message extends JFrame implements MouseListener {
    JPanel backgroundPanel, labelPanel, contentPanel, buttonPanel,contentLeft,contentRight;
    JLabel brief,allSoundSample,expSoundSample,photo,vedio,sampleAnalysisResults,listeningExperimentResult,rawData;

    JLabel[] labels;

    JTextField briefText;

    JButton button_modify;

    JFileChooser jFileChooser;

    // �����Ļ�Ĵ�С
    final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    //���վ��
    String filename;
    JFrame parentPanel;

    public Message(String filename){
        jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        labels = new JLabel[10];

        this.filename = filename;
        //this.parentPanel = parentPanel;

        initBackgroundPanel();
        this.add(backgroundPanel);

        this.setTitle("�鿴��Ϣ");
        this.setSize(800,600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    }
    public void initBackgroundPanel(){
        backgroundPanel = new JPanel(new BorderLayout());
        initLabelPanel();
        initContentPanel();
        //initButtonPanel();

        backgroundPanel.add(labelPanel,"North");
        backgroundPanel.add(contentPanel,"Center");
        //backgroundPanel.add(buttonPanel,"South");
    }

    public void initLabelPanel(){
        labelPanel = new JPanel();

        JLabel title = new JLabel(filename);

        title.setFont(MyFont.Static);

        labelPanel.add(title);
    }

    public void initContentPanel(){
        contentPanel = new JPanel(new BorderLayout());
        initLeftContent();
        initRightContent();
        contentPanel.add(contentLeft,"West");
        contentPanel.add(contentRight,"Center");

    }

    public void initLeftContent(){
        contentLeft = new JPanel();

        String name_String = filename;
        String load = "D:\\convert\\"+name_String+"\\"+name_String+"_brief.txt";
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(load));
            String s = null;
            while ((s = bf.readLine())!=null){
                buffer.append(s.trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String xml = buffer.toString();
        JTextArea field = new JTextArea(xml);
        field.setSize(300,200);
        field.setEnabled(false);
        field.setLineWrap(true);

        contentLeft.add(field);
    }

    public void initRightContent(){
        contentRight = new JPanel(new GridLayout(11,1));
        contentRight.setSize(300,300);

        String[] messages = {"���վ���","ȫ��������","ʵ��������","�����ֳ���Ƭ","�����ֳ���Ƶ","�����������","����ʵ����","ʵ��ԭʼ����"};

        for (int i=0; i<messages.length; i++){
            labels[i] = new JLabel(messages[i],JLabel.CENTER);
            labels[i].addMouseListener(this);
            contentRight.add(labels[i]);
        }

        /*brief = new JLabel("���վ���",JLabel.CENTER);
        brief.addMouseListener(this);
        allSoundSample = new JLabel("ȫ��������",JLabel.CENTER);
        allSoundSample.addMouseListener(this);
        expSoundSample = new JLabel("ʵ��������",JLabel.CENTER);
        expSoundSample.addMouseListener(this);
        photo = new JLabel("�����ֳ���Ƭ",JLabel.CENTER);
        photo.addMouseListener(this);
        vedio = new JLabel("�����ֳ���Ƶ",JLabel.CENTER);
        vedio.addMouseListener(this);
        sampleAnalysisResults = new JLabel("�����������",JLabel.CENTER);
        sampleAnalysisResults.addMouseListener(this);
        listeningExperimentResult = new JLabel("����ʵ����",JLabel.CENTER);
        listeningExperimentResult.addMouseListener(this);
        rawData = new JLabel("ʵ��ԭʼ����",JLabel.CENTER);
        rawData.addMouseListener(this);*/

        /*contentRight.add(brief);
        contentRight.add(allSoundSample);
        contentRight.add(expSoundSample);
        contentRight.add(photo);
        contentRight.add(vedio);
        contentRight.add(sampleAnalysisResults);
        contentRight.add(listeningExperimentResult);
        contentRight.add(rawData);*/
    }

    public void initButtonPanel(){
        buttonPanel = new JPanel();
        button_modify = new JButton("�����޸�");

        button_modify.addMouseListener(this);
        buttonPanel.add(button_modify);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*if (e.getSource() == brief){
            Object[] params = {1,filename};
            PathLoadServiceImpl pathLoadService = new PathLoadServiceImpl();
            try {
                PathLoad pathLoad = pathLoadService.selectOne(params);
                if (pathLoad == null){
                    int result = JOptionPane.showConfirmDialog(null, "�Ƿ�������ӣ�", "�û���ʾ", JOptionPane.YES_NO_OPTION);
                    if (result == 0){
                        jFileChooser.showOpenDialog(null);
                        File file = jFileChooser.getSelectedFile();
                        String filePath = file.getAbsolutePath().toString();
                        Object[] params1 = {1,filename,filePath};
                        int a = pathLoadService.insert(params1);
                        if (a>0){
                            JOptionPane.showMessageDialog(null, "������ӳɹ���");
                        }else {
                            JOptionPane.showMessageDialog(null, "�������ʧ�ܣ�");
                        }
                    }
                }else {
                    File file = new File(pathLoad.getPath());
                    if (file.exists()){
                        Desktop.getDesktop().open(new File(pathLoad.getPath()));
                    }else {
                        JOptionPane.showMessageDialog(null, "��·���ļ������ڣ���������ӣ�");
                        Object[] params1 = {1,filename};
                        pathLoadService.deleteByNameId(params1);
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }else if (e.getSource() == allSoundSample){
            JOptionPane.showMessageDialog(null,"Ok");

        }else if (e.getSource() == expSoundSample){
            JOptionPane.showMessageDialog(null,"Ok");

        }else if (e.getSource() == photo){
            JOptionPane.showMessageDialog(null,"ok");

        }else if (e.getSource() == vedio){
            JOptionPane.showMessageDialog(null,"ok");

        }else if (e.getSource() == sampleAnalysisResults){
            JOptionPane.showMessageDialog(null,"ok");

        }else if (e.getSource() == listeningExperimentResult){
            JOptionPane.showMessageDialog(null,"ok");

        }else if (e.getSource() == rawData){
            JOptionPane.showMessageDialog(null,"ok");

        }*/

        for (int i=0; i<8; i++){
            if (e.getSource() == labels[i]){
                Object[] params = {i,filename};
                PathLoadServiceImpl pathLoadService = new PathLoadServiceImpl();
                try {
                    PathLoad pathLoad = pathLoadService.selectOne(params);
                    if (pathLoad == null){
                        int result = JOptionPane.showConfirmDialog(null, "�Ƿ�����ļ���", "�û���ʾ", JOptionPane.YES_NO_OPTION);
                        if (result == 0){
                            //��ѡ��ȡ��ʱ���Զ��Ƴ�
                            int res = jFileChooser.showOpenDialog(null);
                            if (res != 0){
                                JOptionPane.showMessageDialog(null, "�ļ����ʧ�ܣ�");
                                continue;
                            }
                            File file = jFileChooser.getSelectedFile();
                            String filePath = file.getAbsolutePath().toString();
                            Object[] params1 = {i,filename,filePath};
                            int a = pathLoadService.insert(params1);
                            if (a>0){
                                JOptionPane.showMessageDialog(null, "�ļ���ӳɹ���");
                            }else {
                                JOptionPane.showMessageDialog(null, "�ļ����ʧ�ܣ�");
                            }
                        }
                    }else {
                        File file = new File(pathLoad.getPath());
                        if (file.exists()){
                            Desktop.getDesktop().open(new File(pathLoad.getPath()));
                        }else {
                            JOptionPane.showMessageDialog(null, "��·���ļ������ڣ���������ӣ�");
                            Object[] params1 = {i,filename};
                            pathLoadService.deleteByNameId(params1);
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        /*if (e.getSource() == brief){
            brief.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == allSoundSample){
            allSoundSample.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == expSoundSample){
            expSoundSample.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == photo){
            photo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == vedio){
            vedio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == sampleAnalysisResults){
            sampleAnalysisResults.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == listeningExperimentResult){
            listeningExperimentResult.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == rawData){
            rawData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }*/
        for (int i=0; i<8; i++){
            if (e.getSource() == labels[i]){
                labels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
