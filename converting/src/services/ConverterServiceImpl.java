package services;

import basedao.BaseDaoImpl;
import entity.Converter;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.Array;
import java.util.List;
import java.util.Vector;

public class ConverterServiceImpl {
    // 条件查询商品
    public Vector<Vector> selectConverting(Object[] paraArray) throws Exception {
        Vector<Vector> rows = new Vector<Vector>();
        BaseDaoImpl dao = new BaseDaoImpl();
        StringBuilder sqlBuilder = new StringBuilder(
                "select voltage,name from Converter where true");
        String name = paraArray[0].toString().trim();
        if (!name.isEmpty()) {
            sqlBuilder.append(" and Converter.name like '%" + paraArray[0] + "%' ");
        }
        if (!"全部".equals(paraArray[1])) {
            sqlBuilder.append(" and Converter.voltage='" + paraArray[1] + "'");
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
        ConverterServiceImpl converterService = new ConverterServiceImpl();

        Vector<Vector> a = converterService.selectConverting(conditionParams);
        for (Vector vector : a) {
            System.out.println(vector.toString());
        }

        Object[] objects={2,"800kV","变电站102"};
        int b = converterService.insert(objects);
        System.out.println(b);

        Object[] objects={"800kV","变电站102","变电站103"};
        int c = converterService.updateByName(objects);
        System.out.println(c);

        Object[] objects={"变电站102"};
        int d = converterService.deleteByname(objects);
        System.out.println(d);

        Object[] objects={"换流站101"};
        Converter converter = converterService.selectOne(objects);
        System.out.println(converter.toString());
    }*/

    public List selectAll() throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        List list = dao.select("select voltage,name from converter", 2, null);
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

    public Converter selectOne(Object[] paraArray) throws Exception {
        Converter converter = new Converter();
        BaseDaoImpl dao = new BaseDaoImpl();
        String sql = "select name from converter where name=?";
        List list = dao.select(sql, 1, paraArray);
        if (!list.isEmpty()) {
            converter.setName((String) ((Object[]) list.get(0))[0]);
            return converter;
        }
        return null;
    }

    public List selectById(Object[] paraArray) throws Exception {
        Converter converter = new Converter();
        BaseDaoImpl dao = new BaseDaoImpl();
        String sql = "select id,name from converter where id=? ";
        List<Object[]> list = dao.select(sql, 2, paraArray);
        return list;
    }


    // 逻辑删除商品
    public int deleteByname(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("delete from converter where name=?", paraArray);
        return result;
    }


    // 通过name修改销售单
    public int updateByName(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("update converter set voltage=?, name=? where name=? ", paraArray);
        return result;
    }

    // 插入销售单

    public int insert(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.insert("insert into converter(id,voltage,name)  values(?,?,?)", paraArray);
        return result;
    }
}
