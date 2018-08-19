package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.domain.CustomerDTO;
import model.domain.PlaceDTO;
import util.DButil;

public class PlaceDAO {
	

	public static ArrayList<PlaceDTO> typeSearch(String type) throws SQLException {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id, name, type, usage, lat, lng from place where type=?";
		ArrayList<PlaceDTO> data = new ArrayList<PlaceDTO>();
		try {
			con = DButil.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,type);
			rs =pstmt.executeQuery();
			while(rs.next()) {
				data.add(new PlaceDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getDouble(6)));
			}
		}finally {
			DButil.close(con, pstmt, rs);
		}		
		return data;
		
	}
	
	

	public static ArrayList<PlaceDTO> typeSearchAll() throws SQLException {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from place";
		ArrayList<PlaceDTO> data = new ArrayList<PlaceDTO>();
		try {
			con = DButil.getConnection();
			pstmt=con.prepareStatement(sql);
			rs =pstmt.executeQuery();
			while(rs.next()) {
				data.add(new PlaceDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getDouble(6)));
			}
		}finally {
			DButil.close(con, pstmt, rs);
		}		
		return data;
		
	}
}
