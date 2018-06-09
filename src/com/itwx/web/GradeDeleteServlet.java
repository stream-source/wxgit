package com.itwx.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwx.dao.GradeDao;
import com.itwx.dao.StudentDao;
import com.itwx.utils.DBUtil;
import com.itwx.utils.ResponseUtil;

import net.sf.json.JSONObject;

public class GradeDeleteServlet extends HttpServlet {

	DBUtil dbUtil = new DBUtil();
	GradeDao gradeDao = new GradeDao();
	StudentDao studentDao = new StudentDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//首先获取请求参数
		String delIds = request.getParameter("delIds");
		//连接数据库驱动
		Connection conn = null;
		try {
			//获取conn
			conn = dbUtil.getCon();
			String str[] = delIds.split(",");
			JSONObject result = new JSONObject();
			for(int i =0; i< delIds.length();i++) {
				boolean f =studentDao.getStudentByGradeId(conn, str[i]);
				if(f) {
					result.put("errorIndex", i);
					result.put("errorMsg", "无法删除，存在外键关联");
					ResponseUtil.write(response, result);
					return;
				}
			}
			//调用方法进行删除
			int delNums = gradeDao.gradeDelete(conn, delIds);
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
