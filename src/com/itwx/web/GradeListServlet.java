package com.itwx.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwx.dao.GradeDao;
import com.itwx.entry.Grade;
import com.itwx.entry.PageBean;
import com.itwx.utils.DBUtil;
import com.itwx.utils.JsonUtil;
import com.itwx.utils.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GradeListServlet extends HttpServlet {

	DBUtil dbUtil = new DBUtil();
	GradeDao gradeDao = new GradeDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// �������л�ȡ������Ϣ
		String pages = request.getParameter("page");
		String rowss = request.getParameter("rows");
		//��ȡ�ͻ��˴������Ĳ�ѯ����
		String gradeName = request.getParameter("gradeName");///
		System.out.println(gradeName);
		if(gradeName==null) {
			gradeName="";
		}
		Grade grade = new Grade();
		grade.setGradeName(gradeName);
		// ת����������
		int page = Integer.parseInt(pages);
		int rows = Integer.parseInt(rowss);
		// ��װ����
		PageBean pageBean = new PageBean(page, rows);
		// �������ݿ�
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			JSONObject result= new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(gradeDao.gradeList(conn, pageBean, grade));
			//��ȡ�ܵļ�¼��
			int total = gradeDao.getCount(conn,grade);
			result.put("rows", jsonArray);
			result.put("total", total);
			//�ͻ�����Ӧ��Ϣ
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
