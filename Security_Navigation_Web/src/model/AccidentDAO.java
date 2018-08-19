package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.domain.AccidentDTO;
import model.domain.PlaceDTO;
import util.DButil;

public class AccidentDAO {

	public static AccidentDTO avgRoute(ArrayList NameList) throws SQLException {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select round(avg(riskgrade),2) as Riskgrade,round(avg(riskratio),2) as Riskratio,\r\n" + 
				"round(avg(AccidentNum),2) as AccidentNum ,round(avg(DeadNum),2) as DeadNum,\r\n" + 
				"round(avg(CriticalNum),2) as CriticalNum,\r\n" + 
				"round(avg(StableNum),2) as StableNum\r\n" + 
				"round(avg(ClaimantNum),2) as ClaimantNum from accident  where name=?";
		String where= "or name=?";
		String resultsql=sql;
		for(int i=1; i<NameList.size(); i++) {
			resultsql+=where;
		}
		AccidentDTO data = null ;
		try {
			con = DButil.getConnection();
			pstmt=con.prepareStatement(resultsql);
			for(int i=0;i<=NameList.size();i++) {
				pstmt.setString(i+1,(String) NameList.get(i));
			}
			rs =pstmt.executeQuery();
			while(rs.next()) {
				data=new AccidentDTO(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getFloat(4),rs.getFloat(5),rs.getFloat(6),rs.getFloat(7));
			}
		}finally {
			DButil.close(con, pstmt, rs);
		}		
		return data;
		
	}
}
