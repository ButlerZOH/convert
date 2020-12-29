package services;

import basedao.BaseDaoImpl;

import entity.Transformer;

import java.util.List;
import java.util.Vector;

public class TransformerServiceImpl {
    public Vector<Vector> selectTrans(Object[] paraArray) throws Exception {
        Vector<Vector> rows = new Vector<Vector>();
        BaseDaoImpl dao = new BaseDaoImpl();
        StringBuilder sqlBuilder = new StringBuilder(
                "select voltage,name from Transformer where true");
        String name = paraArray[0].toString().trim();
        if (!name.isEmpty()) {
            sqlBuilder.append(" and Transformer.name like '%" + paraArray[0] + "%' ");
        }
        if (!"全部".equals(paraArray[1])) {
            sqlBuilder.append(" and Transformer.voltage='" + paraArray[1] + "'");
        }
        String sql = sqlBuilder.toString();
        List<Object[]> list = dao.select(sql, 2, null);
        if (!list.isEmpty()) {
            for (Object[] object : list) {
                Vector temp = new Vector<String>();
                for (int i = 0; i < object.length; i++) {
                    temp.add(object[i]);
                }
                rows.add(temp);
            }
        }
        return rows;
    }

    /*public static void main(String[] args) throws Exception {
        Object conditionParams[] = { "","全部"};
        TransformerServiceImpl transformerService = new TransformerServiceImpl();



        Object[] objects={2,"800kV","变电站102"};
        int b = transformerService.insert(objects);
        System.out.println(b);


        Object[] objects={"800kV","变电站103","变电站102"};
        int c = transformerService.updateByName(objects);
        System.out.println(c);


       Object[] objects={"变电站103"};
        int d = transformerService.deleteByName(objects);
        System.out.println(d);

        Object[] objects={"变电站101"};
        Transformer transformer1 = transformerService.selectOne(objects);
        System.out.println(transformer1.toString());

    }*/

    public List selectAll() throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        List list = dao.select("select voltage,name from Transformer", 2, null);
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

    public Transformer selectOne(Object[] paraArray) throws Exception {
        Transformer transformer = new Transformer();
        BaseDaoImpl dao = new BaseDaoImpl();
        String sql = "select name from Transformer where name=?";
        List list = dao.select(sql, 1, paraArray);
        if (!list.isEmpty()) {
            transformer.setName((String) ((Object[]) list.get(0))[0]);
            return transformer;
        }
        return null;
    }

    public List selectById(Object[] paraArray) throws Exception {
        Transformer transformer = new Transformer();
        BaseDaoImpl dao = new BaseDaoImpl();
        String sql = "select id,name from Transformer where id=? ";
        List<Object[]> list = dao.select(sql, 2, paraArray);
        return list;
    }

    // 逻辑删除商品
    public int deleteByName(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("delete from Transformer where name=?", paraArray);
        return result;
    }

    // 通过name修改销售单
    public int updateByName(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("update Transformer set voltage=?, name=? where name=? ", paraArray);
        return result;
    }


    // 插入销售单
    public int insert(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.insert("insert into Transformer(id,voltage,name)  values(?,?,?)", paraArray);
        return result;
    }
}
