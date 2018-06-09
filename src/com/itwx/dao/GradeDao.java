package com.itwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itwx.entry.Grade;
import com.itwx.entry.PageBean;
//import com.mysql.jdbc.PreparedStatement;
import com.itwx.utils.StringUtil;

public class GradeDao {

	/**
	 * 面向对象的思想，再传参数的时候，参入对象
	 * @param conn
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	//分页查询
	public ResultSet gradeList(Connection conn, PageBean pageBean,Grade grade) throws Exception {
		// 分页语句:select * from t_grade limit start, size;
		// select * from t_grade limit (page-1)*rows,rows;
		StringBuffer sb=new StringBuffer("select * from t_grade");
		
		//查询班级
		/**
		 * 首先判断查询班级名称是否为空，不为空，将班级名称添加在字符串中，（SQL语句的格式）
		 */
		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())) {
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
			
		}
		//分页查询总的记录数
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	//获取总的记录数
	public int getCount(Connection conn,Grade grade) throws Exception {
		//String sql = "select count(*) as total from t_grade";
		StringBuffer sb = new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())) {
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		//预编译
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		//执行操作
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
		
	}
	
	//批量删除
	public int gradeDelete(Connection conn, String delIds) throws Exception {
		String sql ="delete from t_grade where id in("+delIds+")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	//添加功能
	public int gradeAdd(Connection conn, Grade grade) throws Exception {
		String sql ="insert into t_grade values(null,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//设置值
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2,grade.getGradeDesc());
		return pstmt.executeUpdate();
	}
	
	//修改功能
	public int gradeModify(Connection conn, Grade grade) throws Exception {
		String sql = "update t_grade set gradeName=? , gradeDesc = ? where id =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		pstmt.setInt(3, grade.getId());
		return pstmt.executeUpdate();
	}
}
