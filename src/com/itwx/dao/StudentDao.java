package com.itwx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itwx.entry.PageBean;
import com.itwx.entry.Student;
import com.itwx.utils.DateUtil;
import com.itwx.utils.StringUtil;

public class StudentDao {

	public ResultSet studentList(Connection con, PageBean pageBean, Student student, String bbirthday, String ebirthday)
			throws Exception {
		StringBuffer sb = new StringBuffer("select * from t_student s,t_grade g where s.gradeId=g.id");
		if (StringUtil.isNotEmpty(student.getStuNo())) {
			sb.append(" and s.stuNo like '%" + student.getStuNo() + "%'");
		}
		if (StringUtil.isNotEmpty(student.getStuName())) {
			sb.append(" and s.stuName like '%" + student.getStuName() + "%'");
		}
		if (StringUtil.isNotEmpty(student.getSex())) {
			sb.append(" and s.sex ='" + student.getSex() + "'");
		}
		if (student.getGradeId() != -1) {
			sb.append(" and s.gradeId ='" + student.getGradeId() + "'");
		}
		if (StringUtil.isNotEmpty(bbirthday)) {
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('" + bbirthday + "')");
		}
		if (StringUtil.isNotEmpty(ebirthday)) {
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('" + ebirthday + "')");
		}
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getRows());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}

	public int studentCount(Connection con, Student student, String bbirthday, String ebirthday) throws Exception {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_student s,t_grade g where s.gradeId=g.id");
		if (StringUtil.isNotEmpty(student.getStuNo())) {
			sb.append(" and s.stuNo like '%" + student.getStuNo() + "%'");
		}
		if (StringUtil.isNotEmpty(student.getStuName())) {
			sb.append(" and s.stuName like '%" + student.getStuName() + "%'");
		}
		if (StringUtil.isNotEmpty(student.getSex())) {
			sb.append(" and s.sex ='" + student.getSex() + "'");
		}
		if (student.getGradeId() != -1) {
			sb.append(" and s.gradeId ='" + student.getGradeId() + "'");
		}
		if (StringUtil.isNotEmpty(bbirthday)) {
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('" + bbirthday + "')");
		}
		if (StringUtil.isNotEmpty(ebirthday)) {
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('" + ebirthday + "')");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}
	
	//批量删除
	public int studentDelete(Connection conn, String delIds) throws Exception {
		String sql ="delete from t_student where stuId in ("+delIds+")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	//添加方法
	public int studentAdd(Connection conn ,Student student) throws Exception {
		String sql="insert into t_student values(null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getSex());
		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setInt(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getStuDesc());
		
		return pstmt.executeUpdate();
	}
	
	//修改方法
	public int studentModify(Connection conn, Student student) throws Exception {
		String sql="update t_student set stuNo=?,stuName=?,sex=?,birthday=?,gradeId=?,email=?,stuDesc=? where stuId=?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getSex());
		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setInt(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getStuDesc());
		pstmt.setInt(8, student.getStuId());
		return pstmt.executeUpdate();
	}
	
	public boolean getStudentByGradeId(Connection con,String gradeId)throws Exception{
		String sql="select * from t_student where gradeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, gradeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
}
