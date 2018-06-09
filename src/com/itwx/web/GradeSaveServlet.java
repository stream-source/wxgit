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
import com.itwx.utils.DBUtil;
import com.itwx.utils.ResponseUtil;
import com.itwx.utils.StringUtil;

import net.sf.json.JSONObject;

public class GradeSaveServlet extends HttpServlet {
	GradeDao gradeDao = new GradeDao();
	DBUtil dbUtil = new DBUtil();
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//获取请求参数
		String gradeName = request.getParameter("gradeName");
		String gradeDesc = request.getParameter("gradeDesc");
		String id = request.getParameter("id");
		Grade grade = new Grade(gradeName,gradeDesc);
		if(StringUtil.isNotEmpty(id)) {
			grade.setId(Integer.parseInt(id));
		}
		//System.out.println(gradeName);
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			int saveNums=0;
			JSONObject result = new JSONObject();
			if(StringUtil.isNotEmpty(id)) {
				saveNums=gradeDao.gradeModify(conn, grade);
			}else {
				saveNums = gradeDao.gradeAdd(conn, grade);
			}
			if(saveNums>0) {
				result.put("success", "true");
			}else {
				result.put("success", "true");
				result.put("erorMsg", "保存失败");
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
