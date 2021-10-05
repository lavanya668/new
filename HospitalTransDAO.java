package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import extra.ConfigDBConn;
import pojo.HospitalDetail;

public class HospitalTransDAO {

	ConfigDBConn configDBConn = null;

	public HospitalTransDAO() {
	}

	public HospitalTransDAO(ConfigDBConn configDBConn) {
		this.configDBConn = configDBConn;
	}

	public List<HospitalDetail> getHospitalDetailsList() {
		List<HospitalDetail> hospitalList = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			hospitalList = new ArrayList<HospitalDetail>();
			String Get_HOSPITAL = "SELECT * FROM hospital_detail";
			sqlPrepStmt = dBConn.prepareStatement(Get_HOSPITAL);
			ResultSet sqlResSet = sqlPrepStmt.executeQuery();
			if (sqlResSet.next() != false) {
				do {
					HospitalDetail hospitalDetail = new HospitalDetail(sqlResSet.getInt("hospital_id"),
							sqlResSet.getString("hospital_name"), sqlResSet.getString("hospital_address"));
					hospitalList.add(hospitalDetail);
				} while (sqlResSet.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hospitalList;
	}

	public HospitalDetail getHospitalDetailByHospitalId(int hospitalId) {
		Connection sqlConnection = null;
		PreparedStatement sqlPrepStmt = null;
		HospitalDetail hospitalDetail = null;
		try {
			sqlConnection = configDBConn.connect();
			String Get_HOSPITAL = "SELECT * FROM hospital_detail where hospital_id = ?";
			sqlPrepStmt = sqlConnection.prepareStatement(Get_HOSPITAL);
			sqlPrepStmt.setInt(1, hospitalId);
			ResultSet sqlResSet = sqlPrepStmt.executeQuery();
			if (sqlResSet.next() != false) {
				do {
					hospitalDetail = new HospitalDetail(sqlResSet.getInt("hospital_id"),
							sqlResSet.getString("hospital_name"), sqlResSet.getString("hospital_address"));
				} while (sqlResSet.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hospitalDetail;
	}

	public int addHospitalDetail(HospitalDetail hospitalDetail) {
		Connection sqlConnection = null;
		PreparedStatement sqlPrepStmt = null;
		int hospitalId = 0;
		try {
			sqlConnection = configDBConn.connect();
			String HOSPITAL_ADD = "INSERT INTO `hospital_detail` (`hospital_name`, `hospital_address`) VALUES (?, ?)";
			sqlPrepStmt = sqlConnection.prepareStatement(HOSPITAL_ADD);
			sqlPrepStmt.setString(1, hospitalDetail.getHospitalName());
			sqlPrepStmt.setString(2, hospitalDetail.getHospitalAddress());
			hospitalId = sqlPrepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hospitalId;
	}

	public int updateHospitalDetail(HospitalDetail hospitalDetail) {
		Connection dbConn = null;
		PreparedStatement sqlPrepStmt = null;
		int hospitalId = 0;
		try {
			dbConn = configDBConn.connect();
			String HOSPITAL_ADD = "UPDATE `hospital_detail` SET `hospital_name` = ?, `hospital_address` = ? WHERE (`hospital_id` = ?)";
			sqlPrepStmt = dbConn.prepareStatement(HOSPITAL_ADD);
			sqlPrepStmt.setString(1, hospitalDetail.getHospitalName());
			sqlPrepStmt.setString(2, hospitalDetail.getHospitalAddress());
			sqlPrepStmt.setInt(3, hospitalDetail.getHospitalId());
			hospitalId = sqlPrepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hospitalId;
	}

}
