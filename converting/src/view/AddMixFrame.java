package view;

import entity.Converter;
import entity.Mix;
import framework.Item;
import framework.MyFont;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import services.ConverterServiceImpl;
import services.MixServiceImpl;
import util.FileExecution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

public class AddMixFrame extends JFrame implements MouseListener {

    JPanel backgroundPanel, labelPanel, contentPanel, buttonPanel;
    JLabel label_name, label_price, label_brand, label_type, label_category;
    JTextField name, price;
    JComboBox type, category, brand;
    JButton button_add;
    // 获得屏幕的大小
    final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    // 父面板对象
    MixManageFrame parentPanel;

    public AddMixFrame(MixManageFrame parentPanel){
        this.parentPanel = parentPanel;
        initBackgroundPanel();

        this.add(backgroundPanel);
        this.setTitle("添加混合站");
        this.setSize(500,190);
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
        JLabel title = new JLabel("混合站信息");
        title.setFont(MyFont.Static);
        labelPanel.add(title);
    }

    public void initContentPanel(){
        contentPanel = new JPanel(new GridLayout(1, 2));
        label_name = new JLabel("混合站名称",JLabel.CENTER);
        name = new JTextField("");
        contentPanel.add(label_name);
        contentPanel.add(name);

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
                JOptionPane.showMessageDialog(null,"请输入混合站名称");
            }else {
                int result = 0;

                String id = UUID.randomUUID().toString().replaceAll("-", "");
                try{
                    Object[] params = { name_String };

                    Mix mix = new Mix();
                    MixServiceImpl mixService = new MixServiceImpl();

                    mix = mixService.selectOne(params);
                    if (mix == null){
                        Object[] params1 = {id,name_String};
                        result = mixService.insert(params1);
                        if (result>0){
                            JOptionPane.showMessageDialog(null,"添加混合站成功");
                            this.setVisible(false);
                            parentPanel.refreshTablePanel();
                        }


                        FileExecution reName = new FileExecution();
                        reName.createFile(name_String);
                    }else{
                        JOptionPane.showMessageDialog(null,"混合站名称重复");
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
