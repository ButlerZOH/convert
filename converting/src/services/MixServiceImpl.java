package services;

import basedao.BaseDaoImpl;
import entity.Converter;
import entity.Mix;

import java.util.List;
import java.util.Vector;

public class MixServiceImpl {
    // 条件查询商品
    public Vector<Vector> selectMix(Object[] paraArray) throws Exception {
        Vector<Vector> rows = new Vector<Vector>();
        BaseDaoImpl dao = new BaseDaoImpl();
        StringBuilder sqlBuilder = new StringBuilder(
                "select name from mix where true");
        String name = paraArray[0].toString().trim();
        if (!name.isEmpty()) {
            sqlBuilder.append(" and mix.name like '%" + paraArray[0] + "%' ");
        }

        String sql = sqlBuilder.toString();
        List<Object[]> list = dao.select(sql, 1, null);
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



    public Mix selectOne(Object[] paraArray) throws Exception {
        Mix mix = new Mix();
        BaseDaoImpl dao = new BaseDaoImpl();
        String sql = "select name from mix where name=?";
        List list = dao.select(sql, 1, paraArray);
        if (!list.isEmpty()) {
            mix.setName((String) ((Object[]) list.get(0))[0]);
            return mix;
        }
        return null;
    }

    // 逻辑删除混合电站
    public int deleteByName(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("delete from mix where name=?", paraArray);
        return result;
    }

    // 通过name修改混合电站
    public int updateByName(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("update mix set name=? where name=? ", paraArray);
        return result;
    }

    //添加混合电站
    public int insert(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.insert("insert into mix(id,name)  values(?,?)", paraArray);
        return result;
    }

    /*public static void main(String[] args) throws Exception {


        MixServiceImpl mixService = new MixServiceImpl();

        Object conditionParams[] = {""};
        Vector<Vector> a = mixService.selectMix(conditionParams);
        for (Vector vector : a) {
            System.out.println(vector.toString());
        }

        Object[] objects={"123","混合电站103"};
        int b = mixService.insert(objects);
        System.out.println(b);

        Object[] objects1={"混合电站102","混合电站"};
        int c = mixService.updateByName(objects1);
        System.out.println(c);

        Object[] object = {"混合电站103"};
        int d = mixService.deleteByName(object);
        System.out.println(d);
    }*/
}
