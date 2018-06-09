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
	 * ��������˼�룬�ٴ�������ʱ�򣬲������
	 * @param conn
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	//��ҳ��ѯ
	public ResultSet gradeList(Connection conn, PageBean pageBean,Grade grade) throws Exception {
		// ��ҳ���:select * from t_grade limit start, size;
		// select * from t_grade limit (page-1)*rows,rows;
		StringBuffer sb=new StringBuffer("select * from t_grade");
		
		//��ѯ�༶
		/**
		 * �����жϲ�ѯ�༶�����Ƿ�Ϊ�գ���Ϊ�գ����༶����������ַ����У���SQL���ĸ�ʽ��
		 */
		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())) {
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
			
		}
		//��ҳ��ѯ�ܵļ�¼��
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	//��ȡ�ܵļ�¼��
	public int getCount(Connection conn,Grade grade) throws Exception {
		//String sql = "select count(*) as total from t_grade";
		StringBuffer sb = new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())) {
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		//Ԥ����
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		//ִ�в���
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
		
	}
	
	//����ɾ��
	public int gradeDelete(Connection conn, String delIds) throws Exception {
		String sql ="delete from t_grade where id in("+delIds+")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	//��ӹ���
	public int gradeAdd(Connection conn, Grade grade) throws Exception {
		String sql ="insert into t_grade values(null,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//����ֵ
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2,grade.getGradeDesc());
		return pstmt.executeUpdate();
	}
	
	//�޸Ĺ���
	public int gradeModify(Connection conn, Grade grade) throws Exception {
		String sql = "update t_grade set gradeName=? , gradeDesc = ? where id =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		pstmt.setInt(3, grade.getId());
		return pstmt.executeUpdate();
	}
}
