package com.itwx.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwx.dao.StudentDao;
import com.itwx.utils.DBUtil;
import com.itwx.utils.ResponseUtil;

import net.sf.json.JSONObject;

public class StudentDeleteServlet extends HttpServlet {

	StudentDao studentDao = new StudentDao();
	DBUtil dbUtil = new DBUtil();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String delIds = request.getParameter("delIds");
		
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			//调用方法进行删除
			int delNums = studentDao.studentDelete(conn, delIds);
			if(delNums>0) {
				result.put("success", "true");
				result.put("delNums", delNums);
			}else {
				result.put("errorMsg", "删除失败");
			}
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
