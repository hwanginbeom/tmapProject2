package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.domain.CustomerDTO;
import util.DButil;

public class CustomerDAO {
	
	public static ArrayList<CustomerDTO> allSearch() throws SQLException  {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from customer";
		ArrayList<CustomerDTO> data =null;
		
		try {
			con = DButil.getConnection();
			pstmt=con.prepareStatement(sql);
			rs =pstmt.executeQuery();
			while(rs.next()) {
				data.add(new CustomerDTO(rs.getString(1),rs.getString(2),rs.getString(3)));
			}
		}finally {
			DButil.close(con, pstmt, rs);
		}		
		return data;
		
	}
	
	
	//로그인 검증 - id/pw 값으로 name 반환
	//select : query
	public static String loginCheck(String id,String pw) throws SQLException  {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select name from customer where id = ? and pw =? ";
		String name=null;
		
		try {
			con = DButil.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2, pw);
			rs =pstmt.executeQuery();
			if(rs.next()) {
				name = rs.getString(1);
			}
		}finally {
			DButil.close(con, pstmt, rs);
		}		
		return name;
		
	}
	
	
	//해당 고객만의 정보 수정(id값으로 이름 수정)
	//update : DML
	public static boolean update(String id,String newName) throws SQLException  {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update customer set name=? where id=?";
		boolean result = false;
		try {
			con = DButil.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,newName);
			pstmt.setString(2, id);
			int check =  pstmt.executeUpdate();
			if(check!=0) {
				result  = true;
			}
		}finally {
			DButil.close(con, pstmt);
		}		
		return result;
		
	}
	
	public static boolean signup(String id, String pw, String name) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into customer values (?,?,?)";
		boolean result = false;
		try {
			con = DButil.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2, pw);
			pstmt.setString(3,name);
			int check =  pstmt.executeUpdate();
			if(check!=0) {
				result  = true;
			}
		}finally {
			DButil.close(con, pstmt);
		}		
		return result;
	}

	
	
}
