package view;

import entity.Converter;
import framework.Item;
import framework.MyFont;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import services.ConverterServiceImpl;
import services.PathLoadServiceImpl;
import util.FileExecution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ModifyConverterJFrame extends JFrame implements MouseListener {
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
    ConverterFrame parentPanel;

    public ModifyConverterJFrame(ConverterFrame parentPanel, JTable table, int selectedRow) {
        this.table = table;
        this.selectedRow = selectedRow;
        this.parentPanel = parentPanel;

        initBackgroundPanel();
        this.add(backgroundPanel);

        this.setTitle("�޸Ļ���վ����");
        this.setSize(640, 230);
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

        JLabel title = new JLabel("����վ��Ϣ");
        title.setFont(MyFont.Static);

        labelPanel.add(title);
    }
    public void initContentPanel(){
        contentPanel = new JPanel(new GridLayout(2, 2));
        label_name = new JLabel("����վ����",JLabel.CENTER);
        label_brand = new JLabel("����վ��ѹ",JLabel.CENTER);


        name = new JTextField((String)table.getValueAt(selectedRow,1));


        brand = new JComboBox();
        brand.addItem(new Item("800Kv","800Kv"));
        brand.addItem(new Item("1100Kv","1100Kv"));
        int sign=0;
        String[] convert1 = {"800Kv","1100Kv"};
        for (int i = 0; i < convert1.length; i++) {
            if (convert1[i].equals((String)table.getValueAt(selectedRow,0))){
                sign=i;
                break;
            }
        }
        brand.setSelectedIndex(sign);

        contentPanel.add(label_name);
        contentPanel.add(name);
        contentPanel.add(label_brand);
        contentPanel.add(brand);

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
                JOptionPane.showMessageDialog(null,"�����뻻��վ����");
            }else {
                int result = 0;
                String brand_id = ((Item) brand.getSelectedItem()).getKey();
                try{
                    //�жϼ�Ҫ�ĳɵĻ���վ�����Ƿ���Դ���ݿ��ͻ
                    Object[] params = { name_String };
                    Converter converter = new Converter();
                    ConverterServiceImpl converterService = new ConverterServiceImpl();
                    converter = converterService.selectOne(params);

                    String name1=(String) table.getValueAt(selectedRow, 1);

                    if (converter == null || name1.equals(name_String)){
                        //name_String �����֣�name1 ������
                        Object[] params1 ={brand_id,name_String,name1};
                        result = converterService.updateByName(params1);
                        if (result>0){
                            JOptionPane.showMessageDialog(null,"�޸Ļ���վ�ɹ�");
                            this.setVisible(false);
                            parentPanel.refreshTablePanel();

                            //�޸�˵���ļ�
                            FileExecution fileExecution = new FileExecution();
                            fileExecution.Rename(name1,name_String);

                            //�޸����ݿ��ļ���
                            PathLoadServiceImpl pathLoadService = new PathLoadServiceImpl();
                            Object[] params2 = {name_String,name1};
                            pathLoadService.updateByName(params2);

                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "����վ�����ظ�");
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
