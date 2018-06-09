package com.itwx.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwx.dao.UserDao;
import com.itwx.entry.User;
import com.itwx.utils.DBUtil;
import com.itwx.utils.StringUtil;

public class LoginServlet extends HttpServlet {

	DBUtil dbUtil = new DBUtil();
	UserDao userDao = new UserDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);//即使使用get方式提交，会调用doPost（）方法
		/**
		 * 两者的区别：
		 * 1.post提交方式更安全，数据信息放在数据包中，get方式提交数据在URL中
		 * 2.post提交数据大，get方式提交数据量小
		 */
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		DBUtil dbUtil = new DBUtil();
		UserDao userDao = new UserDao();
		//首先获取请求参数
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		//将数据显示在客户端页面:数据回显,在login.jsp中使用el表达式，获取属性值
		//可以保证信息错误仍然显示在页面上
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);
		//System.out.println(userName);
		//System.out.println(password);
		//服务器跳转（转发）:如果用户名或者密码输入为空，提示用户
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)) {
			//设置request域
			request.setAttribute("error","用户名或者密码为空！");
			//转发，在服务器中，地址栏中的URL不发生变化
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		//连接数据库进行验证:把客户端请求的数据传入
		User user = new User(userName,password);
		Connection con = null;
		try {
			//连接数据驱动
			con=dbUtil.getCon();
			User login = userDao.login(con, user);
			if(login==null) {//信息错误：得到用户名为空，则进行服务器端调转
				request.setAttribute("error", "用户名或者密码错误！");
				request.getRequestDispatcher("index.jsp").forward(request,response);
			}else {//信息正确，登录成功
				//获取session域
				HttpSession session = request.getSession();
				//存放属性值
				session.setAttribute("login",login);
				//客户端跳转:重定向
				response.sendRedirect("main.jsp");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
