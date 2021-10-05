package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import extra.ConfigDBConn;
import pojo.Prescription;
import pojo.PrescriptionDTO;

public class PrescriptionDAO {

	ConfigDBConn configDBConn = null;

	public PrescriptionDAO() {
	}

	public PrescriptionDAO(ConfigDBConn configDBConn) {
		this.configDBConn = configDBConn;
	}

	public int addPrescription(Prescription presc) {
		int precId = 0;
		int appmntId = 0;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String ADD_PRESCRIPTION = "INSERT INTO `prescriptions` (`appointment_id`, `problem`, `solution`, `prescription_date_time`, `prescription_status`) VALUES (?, ?, ?, now(), ?)";
			String UPDATE_APPOINTMENT = "UPDATE `hospital_appointments` SET  `appointment_status` = 0 WHERE `appointment_id` = ?";
			sqlPrepStmt = dBConn.prepareStatement(ADD_PRESCRIPTION);
			sqlPrepStmt.setInt(1, presc.getAppointmentId());
			sqlPrepStmt.setString(2, presc.getProblem());
			sqlPrepStmt.setString(3, presc.getSolution());
			sqlPrepStmt.setInt(4, presc.getPrescriotionStatus());
			precId = sqlPrepStmt.executeUpdate();
			if(precId > 0) {
				sqlPrepStmt = dBConn.prepareStatement(UPDATE_APPOINTMENT);
				sqlPrepStmt.setInt(1, presc.getAppointmentId());
				appmntId = sqlPrepStmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appmntId;
	}

	public int updatePrescription(Prescription presc) {
		int prescId = 0;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String UPDATE_PRESCRIPTION = "UPDATE `prescriptions` SET `appointment_id` = ?, `problem` = ?, `solution`= ?, `prescription_date_time` = ?, `prescription_status` = ? WHERE `prescription_id` = ?";
			sqlPrepStmt = dBConn.prepareStatement(UPDATE_PRESCRIPTION);
			sqlPrepStmt.setInt(1, presc.getAppointmentId());
			sqlPrepStmt.setString(2, presc.getProblem());
			sqlPrepStmt.setString(3, presc.getSolution());
			sqlPrepStmt.setTimestamp(4, presc.getPrescriptionDateTime());
			sqlPrepStmt.setInt(5, presc.getPrescriotionStatus());
			sqlPrepStmt.setInt(6, presc.getPrescriptionId());
			prescId = sqlPrepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prescId;
	}

	public List<PrescriptionDTO> getAllPrescriptionByPatientId(int patientId) {
		List<PrescriptionDTO> prescList = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String GET_PRESCRIPTION_PATIENT = "SELECT * FROM prescriptions pr, hospital_appointments ha where pr.appointment_id = ha.appointment_id AND ha.patient_id = ?";
			sqlPrepStmt = dBConn.prepareStatement(GET_PRESCRIPTION_PATIENT);
			sqlPrepStmt.setInt(1, patientId);
			ResultSet sqlRes = sqlPrepStmt.executeQuery();
			if (sqlRes.next() != false) {
				prescList = new ArrayList<PrescriptionDTO>();
				do {
					PrescriptionDTO presc = new PrescriptionDTO(sqlRes.getInt("prescription_id"),
							sqlRes.getInt("appointment_id"), sqlRes.getString("problem"), sqlRes.getString("solution"),
							sqlRes.getTimestamp("prescription_date_time"), sqlRes.getInt("prescription_status"),
							sqlRes.getInt("appointment_id"), sqlRes.getInt("doctor_id"), sqlRes.getInt("patient_id"),
							sqlRes.getInt("hospital_id"), sqlRes.getTimestamp("appointment_date_time"),
							sqlRes.getInt("appointment_status"));
					prescList.add(presc);
				} while (sqlRes.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prescList;
	}
}
