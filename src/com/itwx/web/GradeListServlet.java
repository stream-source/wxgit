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
		// 从请求中获取参数信息
		String pages = request.getParameter("page");
		String rowss = request.getParameter("rows");
		//获取客户端传过来的查询参数
		String gradeName = request.getParameter("gradeName");///
		System.out.println(gradeName);
		if(gradeName==null) {
			gradeName="";
		}
		Grade grade = new Grade();
		grade.setGradeName(gradeName);
		// 转换数据类型
		int page = Integer.parseInt(pages);
		int rows = Integer.parseInt(rowss);
		// 封装对象
		PageBean pageBean = new PageBean(page, rows);
		// 连接数据库
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			JSONObject result= new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(gradeDao.gradeList(conn, pageBean, grade));
			//获取总的记录数
			int total = gradeDao.getCount(conn,grade);
			result.put("rows", jsonArray);
			result.put("total", total);
			//客户端响应信息
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
