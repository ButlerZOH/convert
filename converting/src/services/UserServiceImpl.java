package services;

import basedao.BaseDaoImpl;
import entity.User;

import java.util.List;

public class UserServiceImpl {

	// 查询一条记录

	public User selectOne(Object[] paraArray) throws Exception {
		User user = new User();
		BaseDaoImpl dao = new BaseDaoImpl();
		String sql = "select id,name,password from user where name=? and password=?";
		List list = dao.select(sql, 3, paraArray);
		if (!list.isEmpty()) {
			user.setId((String) ((Object[]) list.get(0))[0]);
			user.setName((String) ((Object[]) list.get(0))[1]);
			user.setPassword((String) ((Object[]) list.get(0))[2]);
			return user;
		}
		return null;
	}

	public User selectOne2(Object[] paraArray) throws Exception {
		User user = new User();
		BaseDaoImpl dao = new BaseDaoImpl();
		String sql = "select id,name,password from user where name=?";
		List list = dao.select(sql, 3, paraArray);
		if (!list.isEmpty()) {
			user.setId((String) ((Object[]) list.get(0))[0]);
			user.setName((String) ((Object[]) list.get(0))[1]);
			user.setPassword((String) ((Object[]) list.get(0))[2]);
			return user;
		}
		return null;
	}

	// 通过Id修改用户

	public int updateUserById(Object[] paraArray) throws Exception {
		int result = 0;
		BaseDaoImpl dao = new BaseDaoImpl();
		String sql = "update user set name = ?,password=? where id=?";
		result = dao.update(sql, paraArray);
		return result;
	}


	public int registerUser(Object[] paraArray) throws Exception {
		BaseDaoImpl dao = new BaseDaoImpl();
		int result = 0;
		result = dao.insert("insert into user values(?,?,?)", paraArray);
		return result;

	}
}