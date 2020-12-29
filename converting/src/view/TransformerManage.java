package view;

import framework.BaseTableModule;
import framework.Item;
import framework.Tools;
import services.ConverterServiceImpl;
import services.PathLoadServiceImpl;
import services.TransformerServiceImpl;
import util.FileExecution;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class TransformerManage implements ActionListener, MouseListener, DocumentListener {
    // 定义全局组件
    JPanel backgroundPanel, topPanel, toolPanel, searchPanel, tablePanel;
    JTextField input_name;
    JComboBox select_brand, select_type,select_category;
    BaseTableModule baseTableModule;
    JTable table;
    JScrollPane jScrollPane;
    JLabel label_brand, label_type, label_category, tool_add, tool_modify, tool_delete, tool_retrieve,label_name;

    public TransformerManage(){
        backgroundPanel = new JPanel(new BorderLayout());

        initTopPanel();
        initTablePanel();
    }

    public void initTopPanel(){
        topPanel = new JPanel(new BorderLayout());

        initToolPanel();
        initSearchPanel();

        backgroundPanel.add(topPanel,"North");
    }



    public void initToolPanel(){
        toolPanel = new JPanel();
        // 工具图标
        Icon icon_add = new ImageIcon("image/add.png");
        tool_add = new JLabel(icon_add);
        tool_add.setToolTipText("新建换流站");
        tool_add.addMouseListener(this);

        Icon icon_modify = new ImageIcon("image/modify.png");
        tool_modify = new JLabel(icon_modify);
        tool_modify.setToolTipText("修改换流站");
        tool_modify.addMouseListener(this);

        Icon icon_delete = new ImageIcon("image/delete.png");
        tool_delete = new JLabel(icon_delete);
        tool_delete.setToolTipText("删除换流站");
        tool_delete.addMouseListener(this);

        Icon icon_retrieve = new ImageIcon("image/retrieve.png");
        tool_retrieve = new JLabel(icon_retrieve);
        tool_retrieve.setToolTipText("查询换流站");
        tool_retrieve.addMouseListener(this);


        toolPanel.add(tool_add);
        toolPanel.add(tool_modify);
        toolPanel.add(tool_delete);
        toolPanel.add(tool_retrieve);
        topPanel.add(toolPanel, "West");

    }
    private void initSearchPanel() {
        searchPanel = new JPanel();
        //电压下拉框
        select_brand = new JComboBox();
        select_brand.addItem(new Item("全部","全部"));

        String[] trams = {"110kV","220kV","330kV","500kV","750kV","1000kV"};

        for (int i = 0; i < trams.length; i++) {
            select_brand.addItem(new Item(trams[i],trams[i]));
        }

        select_brand.addActionListener(this);

        label_brand = new JLabel("电压种类");

        //换流站模糊名称输入框
        input_name = new JTextField(10);
        input_name.getDocument().addDocumentListener(this);

        //标签
        label_name = new JLabel("变电站名称查询");

        searchPanel.add(label_brand);
        searchPanel.add(select_brand);
        searchPanel.add(label_name);
        searchPanel.add(input_name);
        topPanel.add(searchPanel,"East");
    }


    public void initTablePanel(){
        String conditionParams[] = {"","全部"};
        String params[] = { "电压", "变电站名" };
        TransformerServiceImpl transformerService = new TransformerServiceImpl();
        Vector<Vector> vector = new Vector<Vector>();
        try {
            vector = transformerService.selectTrans(conditionParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        baseTableModule = new BaseTableModule(params, vector);

        table = new JTable(baseTableModule);
        Tools.setTableStyle(table);
        DefaultTableColumnModel dcm = (DefaultTableColumnModel) table.getColumnModel();// 获取列模型

        jScrollPane = new JScrollPane(table);
        Tools.setJspStyle(jScrollPane);

        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        tablePanel.add(jScrollPane);
        backgroundPanel.add(tablePanel, "Center");
    }

    public void refreshTablePanel() {
        backgroundPanel.remove(tablePanel);
        String name = input_name.getText();
        Item item_brand = (Item) select_brand.getSelectedItem();

        String conditionParams[] = { name,item_brand.getKey() };
        String params[] = { "电压", "变电站名"  };
        TransformerServiceImpl transformerService = new TransformerServiceImpl();
        Vector<Vector> vector = new Vector<Vector>();
        try {
            vector = transformerService.selectTrans(conditionParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        baseTableModule = new BaseTableModule(params, vector);

        table = new JTable(baseTableModule);
        Tools.setTableStyle(table);
        DefaultTableColumnModel dcm = (DefaultTableColumnModel) table.getColumnModel();// 获取列模型

        jScrollPane = new JScrollPane(table);
        Tools.setJspStyle(jScrollPane);

        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        tablePanel.add(jScrollPane);

        backgroundPanel.add(tablePanel, "Center");
        backgroundPanel.validate();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == select_brand) {
            refreshTablePanel();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tool_add){
            new AddTransformerFrame(this);
        }else if (e.getSource() == tool_modify) {
            int row = table.getSelectedRow();
            if (row < 0){
                JOptionPane.showMessageDialog(null,"请选择变电站");
            }else {
                new ModifyTransformerFrame(this, table, row);
            }

        }else if (e.getSource() == tool_delete){
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "请选择变电站");
            } else {
                JTextField name = new JTextField((String) table.getValueAt(row, 1));
                int result = JOptionPane.showConfirmDialog(null, "是否确定删除？", "用户提示", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    String[] params = { name.getText()};
                    TransformerServiceImpl transformerService = new TransformerServiceImpl();

                    try {
                        int tempresult = transformerService.deleteByName(params);
                        if (tempresult > 0) {
                            JOptionPane.showMessageDialog(null, "变电站删除成功！");
                            refreshTablePanel();

                            String name1 = name.getText().trim();
                            FileExecution fileExecution = new FileExecution();
                            fileExecution.deleteFile(name1);

                            //删除文件路径
                            PathLoadServiceImpl pathLoadService = new PathLoadServiceImpl();
                            pathLoadService.deleteByName(params);

                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }else if(e.getSource() == tool_retrieve){
            int row = table.getSelectedRow();
            if (row<0){
                JOptionPane.showMessageDialog(null,"请选择要查看的变电站");
            }else {
                new Message((String)table.getValueAt(row,1));
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
        if (e.getSource() == tool_add){
            tool_add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }else if (e.getSource() == tool_modify) {
            tool_modify.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else if (e.getSource() == tool_delete) {
            tool_delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else if (e.getSource() == tool_retrieve){
            tool_retrieve.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        refreshTablePanel();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        refreshTablePanel();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
