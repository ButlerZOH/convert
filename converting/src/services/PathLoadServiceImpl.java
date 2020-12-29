package services;

import basedao.BaseDaoImpl;
import entity.PathLoad;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.SQLException;
import java.util.List;


public class PathLoadServiceImpl {

    public int insert(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.insert("insert into pathload(id,name,path)  values(?,?,?)", paraArray);
        return result;
    }

    public int deleteByNameId(Object[] paraArray) throws Exception {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("delete from pathload where id=? and name=?", paraArray);
        return result;
    }

    public PathLoad selectOne(Object[] paraArray) throws Exception {
        PathLoad pathLoad = new PathLoad();
        BaseDaoImpl dao = new BaseDaoImpl();
        String sql = "select name,path from pathload where id=? and name=?";
        List list = dao.select(sql, 2, paraArray);
        if (!list.isEmpty()) {
            pathLoad.setName((String) ((Object[]) list.get(0))[0]);
            pathLoad.setPath((String) ((Object[])list.get(0))[1]);
            return pathLoad;
        }
        return null;
    }

    public int deleteByName(Object[] paraArray) throws SQLException {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("delete from pathload where name=?", paraArray);
        return result;
    }

    public int updateByName(Object[] paraArray) throws SQLException {
        BaseDaoImpl dao = new BaseDaoImpl();
        int result = 0;
        result = dao.update("update pathload set name=? where name=? ", paraArray);
        return result;
    }

    /*public static void main(String[] args) throws Exception {
        PathLoadServiceImpl pathLoadService = new PathLoadServiceImpl();

        Object[] params = {2,"混合站","aasdwqa"};
        int a = pathLoadService.insert(params);
        System.out.println(a);

        Object[] params1 = {2,"混合站"};
        int b = pathLoadService.deleteByNameId(params);
        System.out.println(b);

        Object[] params2 = {1,"混合电站"};
        PathLoad pathLoad = pathLoadService.selectOne(params);
        System.out.println(pathLoad.toString());

        Object[] params3 = {"混合电站108","混合电站"};
        int c = pathLoadService.updateByName(params3);
        System.out.println(c);

        Object[] params4 = {"混合电站108"};
        int d = pathLoadService.deleteByName(params4);
        System.out.println(d);
    }*/
}
