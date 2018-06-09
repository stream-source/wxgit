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
		this.doPost(request, response);//��ʹʹ��get��ʽ�ύ�������doPost��������
		/**
		 * ���ߵ�����
		 * 1.post�ύ��ʽ����ȫ��������Ϣ�������ݰ��У�get��ʽ�ύ������URL��
		 * 2.post�ύ���ݴ�get��ʽ�ύ������С
		 */
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		DBUtil dbUtil = new DBUtil();
		UserDao userDao = new UserDao();
		//���Ȼ�ȡ�������
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		//��������ʾ�ڿͻ���ҳ��:���ݻ���,��login.jsp��ʹ��el���ʽ����ȡ����ֵ
		//���Ա�֤��Ϣ������Ȼ��ʾ��ҳ����
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);
		//System.out.println(userName);
		//System.out.println(password);
		//��������ת��ת����:����û���������������Ϊ�գ���ʾ�û�
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)) {
			//����request��
			request.setAttribute("error","�û�����������Ϊ�գ�");
			//ת�����ڷ������У���ַ���е�URL�������仯
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		//�������ݿ������֤:�ѿͻ�����������ݴ���
		User user = new User(userName,password);
		Connection con = null;
		try {
			//������������
			con=dbUtil.getCon();
			User login = userDao.login(con, user);
			if(login==null) {//��Ϣ���󣺵õ��û���Ϊ�գ�����з������˵�ת
				request.setAttribute("error", "�û��������������");
				request.getRequestDispatcher("index.jsp").forward(request,response);
			}else {//��Ϣ��ȷ����¼�ɹ�
				//��ȡsession��
				HttpSession session = request.getSession();
				//�������ֵ
				session.setAttribute("login",login);
				//�ͻ�����ת:�ض���
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
