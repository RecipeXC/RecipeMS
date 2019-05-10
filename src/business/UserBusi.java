package business;

import java.sql.SQLException;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import mapper.UserMapper;
import pojo.User;
import pojo.UserExample;
import util.MybatisSpringUtil;

public class UserBusi {
	
	UserMapper userMapper = MybatisSpringUtil.getApplicationContext().getBean(UserMapper.class);
	
	public int login(User user) throws SQLException {
		
		String pass = "";
		User user_2 = new User();
		
		user_2 = userMapper.selectByPrimaryKey(user.getId());
		if(user_2 != null) {
			pass = user_2.getPassword();
			if(pass.equals(user.getPassword())) {
				return 1;
			}else {
				return 0;
			}
		}else {
			System.out.println("���˺��û������ڣ�");
			return 2;
		}
	}
	
	public int register(User user) throws SQLException {
		
		int r = 0;
		r = userMapper.insertSelective(user);
	    if(r == 1){
	        System.out.println("���ɹ������� " +r+ " ���û���");
	    }else{
	        System.out.println("ִ�в����û�����ʧ�ܣ�����");
	    }
		
		return r;
	}

	public User getAllInfo(User user) {
		
		user = userMapper.selectByPrimaryKey(user.getId());
		
		return user;
	}
	
	public PageInfo<User> getUserList(int page,int pagesize) throws Exception{
		
		PageHelper.startPage(page, pagesize);
		
		UserExample userExample = new UserExample();
		List<User> users = userMapper.selectByExample(userExample);
		
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		
		return pageInfo;
	}
	
}
