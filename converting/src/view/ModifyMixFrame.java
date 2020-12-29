package view;

import entity.Converter;
import entity.Mix;
import framework.Item;
import framework.MyFont;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import services.ConverterServiceImpl;
import services.MixServiceImpl;
import services.PathLoadServiceImpl;
import util.FileExecution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ModifyMixFrame extends JFrame implements MouseListener {

    // ����ȫ�����
    JPanel backgroundPanel, labelPanel, contentPanel, buttonPanel;
    JLabel label_name, label_brand;
    JTextField name;
    JComboBox brand;
    JButton button_modify;

    // �����Ļ�Ĵ�С
    final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    // ������
    JTable table;
    int selectedRow;
    MixManageFrame parentPanel;

    public ModifyMixFrame(MixManageFrame parentPanel, JTable table, int selectedRow) {
        this.table = table;
        this.selectedRow = selectedRow;
        this.parentPanel = parentPanel;

        initBackgroundPanel();
        this.add(backgroundPanel);

        this.setTitle("�޸Ļ��վ����");
        this.setSize(640, 200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void initBackgroundPanel(){
        backgroundPanel = new JPanel(new BorderLayout());

        initLabelPanel();
        initContentPanel();
        initButtonPanel();
        backgroundPanel.add(labelPanel, "North");
        backgroundPanel.add(contentPanel, "Center");
        backgroundPanel.add(buttonPanel, "South");

    }
    public void initLabelPanel(){
        labelPanel = new JPanel();
        JLabel title = new JLabel("���վ��Ϣ");
        title.setFont(MyFont.Static);
        labelPanel.add(title);
    }

    public void initContentPanel(){
        contentPanel = new JPanel(new GridLayout(1, 2));
        label_name = new JLabel("���վ����",JLabel.CENTER);

        name = new JTextField((String)table.getValueAt(selectedRow,0));

        contentPanel.add(label_name);
        contentPanel.add(name);

    }
    public void initButtonPanel(){
        buttonPanel = new JPanel();

        button_modify = new JButton("�����޸�");
        button_modify.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        button_modify.setForeground(Color.white);
        button_modify.setFont(MyFont.Static);
        button_modify.addMouseListener(this);

        buttonPanel.add(button_modify);
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == button_modify){
            String name_String = name.getText().trim();

            if (name_String.isEmpty()){
                JOptionPane.showMessageDialog(null,"��������վ����");
            }else {
                int result = 0;

                try{
                    //�жϼ�Ҫ�ĳɵĻ���վ�����Ƿ���Դ���ݿ��ͻ
                    Object[] params = { name_String };

                    Mix mix = new Mix();
                    MixServiceImpl mixService = new MixServiceImpl();

                    mix = mixService.selectOne(params);
                    String name1=(String) table.getValueAt(selectedRow, 0);

                    if (mix == null || name1.equals(name_String)){
                        //name_String �����֣�name1 ������
                        Object[] params1 ={name_String,name1};
                        result = mixService.updateByName(params1);
                        if (result>0){
                            JOptionPane.showMessageDialog(null,"�޸Ļ��վ�ɹ�");
                            this.setVisible(false);
                            parentPanel.refreshTablePanel();

                            FileExecution fileExecution = new FileExecution();
                            fileExecution.Rename(name1,name_String);

                            //�޸����ݿ��ļ���
                            PathLoadServiceImpl pathLoadService = new PathLoadServiceImpl();
                            Object[] params2 = {name_String,name1};
                            pathLoadService.updateByName(params2);

                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "���վ�����ظ�");
                    }
                }catch (Exception e1){
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
