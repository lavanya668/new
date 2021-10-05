package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import extra.ConfigDBConn;
import pojo.EndUser;
import pojo.EndUserDTO;

public class EndUserTransDAO {

	ConfigDBConn configDBConn = null;

	public EndUserTransDAO() {	}

	public EndUserTransDAO(ConfigDBConn configDBConn) {
		this.configDBConn = configDBConn;
	}

	public int addApplicationUserByType(EndUser endUser) {
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		int appUserId = 0;
		try {
			dBConn = configDBConn.connect();
			String SQL_APPUSER_ADD = "INSERT INTO `app_users` (`user_type`, `first_name`, `last_name`, `phone`, "
					+ "`email`, `password`,`hospital_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
			sqlPrepStmt = dBConn.prepareStatement(SQL_APPUSER_ADD);
			sqlPrepStmt.setString(1, endUser.getUserType());
			sqlPrepStmt.setString(2, endUser.getFirstName());
			sqlPrepStmt.setString(3, endUser.getLastName());
			sqlPrepStmt.setString(4, endUser.getPhone());
			sqlPrepStmt.setString(5, endUser.getEmail());
			sqlPrepStmt.setString(6, endUser.getPassword());
			sqlPrepStmt.setInt(7, endUser.getHospitalId());

			appUserId = sqlPrepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appUserId;
	}

	public int updateApplicationUserByType(EndUser endUser) {
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		int appUserId = 0;
		try {
			dBConn = configDBConn.connect();
			String SQL_APPUSER_UPDATE = "UPDATE `app_users` SET `first_name` = ?, `last_name` = ?,"
					+ " `phone` = ?, `email` = ?, `hospital_id` = ? WHERE (`user_id` = ?);";
			sqlPrepStmt = dBConn.prepareStatement(SQL_APPUSER_UPDATE);
			sqlPrepStmt.setString(1, endUser.getFirstName());
			sqlPrepStmt.setString(2, endUser.getLastName());
			sqlPrepStmt.setString(3, endUser.getPhone());
			sqlPrepStmt.setString(4, endUser.getEmail());
			sqlPrepStmt.setInt(5, endUser.getHospitalId());
			sqlPrepStmt.setInt(6, endUser.getUserId());
			appUserId = sqlPrepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appUserId;
	}

	public Object getListofAppUsersByUserType(String appUserType) {
		List<EndUser> appUserList = null;
		List<EndUserDTO> appUserDTOList = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		Object obj = null;
		try {
			dBConn = configDBConn.connect();
			String GET_END_USER;
			if (appUserType.equalsIgnoreCase("doctor") || appUserType.equalsIgnoreCase("staff")) {
				GET_END_USER = "SELECT * FROM app_users au, hospital_detail hd where au.user_type = ? and au.hospital_id = hd.hospital_id;";
				sqlPrepStmt = dBConn.prepareStatement(GET_END_USER);
				sqlPrepStmt.setString(1, appUserType);
			} else if (appUserType.equalsIgnoreCase("patient")) {
				GET_END_USER = "SELECT * FROM app_users au where au.user_type = ?";
				sqlPrepStmt = dBConn.prepareStatement(GET_END_USER);
				sqlPrepStmt.setString(1, appUserType);
			} else {
				throw new Exception("Request for a proper user type.");
			}
			/* Execute Query */
			ResultSet sqlResSet = sqlPrepStmt.executeQuery();
			if (sqlResSet.next() != false) {
				/* If records exists then get them and add to list */
				appUserList = new ArrayList<EndUser>();
				appUserDTOList = new ArrayList<EndUserDTO>();
				do {
					if (appUserType.equalsIgnoreCase("doctor") || appUserType.equalsIgnoreCase("staff")) {
						EndUserDTO appUserDTO = new EndUserDTO();
						appUserDTO.setUserId(sqlResSet.getInt("user_id"));
						appUserDTO.setFirstName(sqlResSet.getString("first_name"));
						appUserDTO.setLastName(sqlResSet.getString("last_name"));
						appUserDTO.setEmail(sqlResSet.getString("email"));
						appUserDTO.setPhone(sqlResSet.getString("phone"));
						appUserDTO.setHospitalId(sqlResSet.getInt("hospital_id"));
						appUserDTO.setHospitalName(sqlResSet.getString("hospital_name"));
						appUserDTOList.add(appUserDTO);
					} else {
						EndUser appUser = new EndUser(sqlResSet.getInt("user_id"), sqlResSet.getString("user_type"),
								sqlResSet.getString("first_name"), sqlResSet.getString("last_name"),
								sqlResSet.getString("phone"), sqlResSet.getString("email"),
								sqlResSet.getInt("hospital_id"));
						appUserList.add(appUser);
					}
				} while (sqlResSet.next());
			}
			if (appUserType.equalsIgnoreCase("doctor") || appUserType.equalsIgnoreCase("staff"))
				obj = appUserDTOList;
			else
				obj = appUserList;
		} catch (Exception e) {
			
		}
		return obj;
	}

	public EndUser getAppUserByUserId(int appUserId) {
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		EndUser endUser = null;
		try {
			dBConn = configDBConn.connect();
			String GET_END_USER = "SELECT * FROM app_users where user_id = ?";
			sqlPrepStmt = dBConn.prepareStatement(GET_END_USER);
			sqlPrepStmt.setInt(1, appUserId);
			ResultSet sqlResSet = sqlPrepStmt.executeQuery();
			if (sqlResSet.next() != false) {
				do {
					endUser = new EndUser(sqlResSet.getInt("user_id"), sqlResSet.getString("user_type"),
							sqlResSet.getString("first_name"), sqlResSet.getString("last_name"),
							sqlResSet.getString("phone"), sqlResSet.getString("email"),
							sqlResSet.getInt("hospital_id"));
				} while (sqlResSet.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return endUser;
	}

	public int updateAppUserPassword(EndUser endUser) {
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		int appUserId = 0;
		try {
			dBConn = configDBConn.connect();
			String UPDATE_END_USER = "UPDATE `app_users` SET `password` = ? WHERE (`user_id` = ?)";
			sqlPrepStmt = dBConn.prepareStatement(UPDATE_END_USER);
			sqlPrepStmt.setString(1, endUser.getPassword());
			sqlPrepStmt.setInt(2, endUser.getUserId());
			appUserId = sqlPrepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appUserId;
	}

	public List<EndUser> getListofAppUsersByHopitalId(EndUser appUserRequest) {
		List<EndUser> appUserList = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String GET_END_USER = "SELECT * FROM app_users where user_type =? AND hospital_id = ?";
			sqlPrepStmt = dBConn.prepareStatement(GET_END_USER);
			sqlPrepStmt.setString(1, appUserRequest.getUserType());
			sqlPrepStmt.setInt(2, appUserRequest.getHospitalId());
			ResultSet sqlResSet = sqlPrepStmt.executeQuery();
			if (sqlResSet.next() != false) {
				appUserList = new ArrayList<EndUser>();
				do {
					EndUser endUser = new EndUser(sqlResSet.getInt("user_id"), sqlResSet.getString("user_type"),
							sqlResSet.getString("first_name"), sqlResSet.getString("last_name"),
							sqlResSet.getString("phone"), sqlResSet.getString("email"),
							sqlResSet.getInt("hospital_id"));
					appUserList.add(endUser);
				} while (sqlResSet.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appUserList;
	}

}