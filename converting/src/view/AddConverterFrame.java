package view;

import entity.Converter;
import framework.Item;
import framework.MyFont;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import services.ConverterServiceImpl;
import util.FileExecution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

public class AddConverterFrame extends JFrame implements MouseListener {

    JPanel backgroundPanel, labelPanel, contentPanel, buttonPanel;
    JLabel label_name, label_price, label_brand, label_type, label_category;
    JTextField name, price;
    JComboBox type, category, brand;
    JButton button_add;

    // 获得屏幕的大小
    final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    // 父面板对象
    ConverterFrame parentPanel;

    public AddConverterFrame(ConverterFrame parentPanel){
        this.parentPanel = parentPanel;
        initBackgroundPanel();

        this.add(backgroundPanel);
        this.setTitle("添加换流站");
        this.setSize(500,230);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


    }
    public void initBackgroundPanel(){
        backgroundPanel = new JPanel(new BorderLayout());
        initContentPanel();
        initButtonPanel();
        initLabelPanel();

        backgroundPanel.add(labelPanel,"North");
        backgroundPanel.add(contentPanel,"Center");
        backgroundPanel.add(buttonPanel,"South");
    }
    public void initLabelPanel(){
        labelPanel = new JPanel();

        JLabel title = new JLabel("换流站信息");
        title.setFont(MyFont.Static);

        labelPanel.add(title);
    }

    public void initContentPanel(){
        contentPanel = new JPanel(new GridLayout(2, 2));
        label_name = new JLabel("换流站名称",JLabel.CENTER);
        label_brand = new JLabel("换流站电压",JLabel.CENTER);
        name = new JTextField("");

        brand = new JComboBox();
        brand.addItem(new Item("800Kv","800Kv"));
        brand.addItem(new Item("1100Kv","1100Kv"));

        contentPanel.add(label_name);
        contentPanel.add(name);
        contentPanel.add(label_brand);
        contentPanel.add(brand);

    }

    public void initButtonPanel(){
        buttonPanel = new JPanel();
        button_add = new JButton("保存");
        button_add.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        button_add.setForeground(Color.white);
        button_add.setFont(MyFont.Static);
        button_add.addMouseListener(this);

        buttonPanel.add(button_add);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == button_add){
            String name_String = name.getText().trim();
            if (name_String.isEmpty()){
                JOptionPane.showMessageDialog(null,"请输入换流站名称");
            }else {
                int result = 0;
                String brand_id = ((Item) brand.getSelectedItem()).getKey();
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                try{
                    Object[] params = { name_String };
                    Converter converter = new Converter();
                    ConverterServiceImpl converterService = new ConverterServiceImpl();
                    converter = converterService.selectOne(params);
                    if (converter == null){
                        Object[] params1 = {id,brand_id,name_String};
                        result = converterService.insert(params1);
                        if (result>0){
                            JOptionPane.showMessageDialog(null,"添加换流站成功");
                            this.setVisible(false);
                            parentPanel.refreshTablePanel();
                        }

                        FileExecution reName = new FileExecution();
                        reName.createFile(name_String);


                    }else{
                        JOptionPane.showMessageDialog(null,"换流站名称重复");
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
