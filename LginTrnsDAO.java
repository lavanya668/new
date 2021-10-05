package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import extra.ConfigDBConn;
import pojo.EndUser;

public class LginTrnsDAO {

	ConfigDBConn configDbConn = null;

	public LginTrnsDAO() {
	}

	public LginTrnsDAO(ConfigDBConn configDbConn) {
		this.configDbConn = configDbConn;
	}

	public EndUser validateLogin(EndUser endUserRequest) {
		Connection dBConn = null;
		PreparedStatement sqlPrepStmnt = null;
		EndUser endUserResp = null;
		try {
			dBConn = configDbConn.connect();
			String LOGIN_CHECK = "SELECT * FROM app_users where password = ? and email = ?";
			sqlPrepStmnt = dBConn.prepareStatement(LOGIN_CHECK);
			sqlPrepStmnt.setString(1, endUserRequest.getPassword());
			sqlPrepStmnt.setString(2, endUserRequest.getEmail());			
			ResultSet sqlRes = sqlPrepStmnt.executeQuery();
			if (sqlRes.next() != false) {
				do {
					endUserResp = new EndUser(sqlRes.getInt("user_id"), sqlRes.getString("user_type"),
							sqlRes.getString("first_name"), sqlRes.getString("last_name"),
							sqlRes.getString("phone"), sqlRes.getString("email"),
							sqlRes.getInt("hospital_id"));
				} while (sqlRes.next());
			}
		} catch (Exception e) {
		}
		return endUserResp;
	}

}
