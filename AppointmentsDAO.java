package database;

import java.sql.Connection;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import extra.ConfigDBConn;
import pojo.Appointments;

public class AppointmentsDAO {

	ConfigDBConn configDBConn = null;

	public AppointmentsDAO() {
	}

	public AppointmentsDAO(ConfigDBConn configDBConn) {
		this.configDBConn = configDBConn;
	}

	public int addAppointments(Appointments appmts) {
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		int appointmentId = 0;
		try {
			dBConn = configDBConn.connect();
			String ADD_APPOINTMENT = "INSERT INTO `hospital_appointments` (`doctor_id`, `patient_id`, `hospital_id`, `appointment_date_time`, `appointment_status`) VALUES (?, ?, ?, ?, ?)";
			sqlPrepStmt = dBConn.prepareStatement(ADD_APPOINTMENT);
			sqlPrepStmt.setInt(1, appmts.getDoctorId());
			sqlPrepStmt.setInt(2, appmts.getPatientId());
			sqlPrepStmt.setInt(3, appmts.getHospitalId());
			sqlPrepStmt.setTimestamp(4, appmts.getAppointmentDateTime());
			sqlPrepStmt.setInt(5, appmts.getAppontmentStatus());
			appointmentId = sqlPrepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appointmentId;
	}

	public int updateAppointment(Appointments appmts) {
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		int appointmentId = 0;
		try {
			dBConn = configDBConn.connect();
			String UPDATE_APPOINTMENT = "UPDATE `hospital_appointments` SET `doctor_id` =?, `patient_id` = ?, `hospital_id` = ?, `appointment_date_time` = ?, `appointment_status` = ? WHERE `appointment_id` = ?";
			sqlPrepStmt = dBConn.prepareStatement(UPDATE_APPOINTMENT);
			sqlPrepStmt.setInt(1, appmts.getDoctorId());
			sqlPrepStmt.setInt(2, appmts.getPatientId());
			sqlPrepStmt.setInt(3, appmts.getHospitalId());
			sqlPrepStmt.setTimestamp(4, appmts.getAppointmentDateTime());
			sqlPrepStmt.setInt(5, appmts.getAppontmentStatus());
			sqlPrepStmt.setInt(6, appmts.getAppointmentId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appointmentId;
	}

	public Appointments getAppointmentByAppointmentId(int appointmentId) {
		Appointments appmts = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String GET_APPOINTMENT_BY_ID = "SELECT * FROM `hospital_appointments` WHERE `appointment_id` = ?";
			sqlPrepStmt = dBConn.prepareStatement(GET_APPOINTMENT_BY_ID);
			ResultSet sqlRes = sqlPrepStmt.executeQuery();
			if (sqlRes.next() != false) {
				do {
					appmts = new Appointments(sqlRes.getInt("appointment_id"), sqlRes.getInt("doctor_id"),
							sqlRes.getInt("patient_id"), sqlRes.getInt("hospital_id"),
							sqlRes.getTimestamp("appointment_date_time"), sqlRes.getInt("appointment_status"));
				} while (sqlRes.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appmts;
	}

	public List<Appointments> getAppointments() {
		List<Appointments> appmtsList = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String GET_APPOINTMENTS = "SELECT * FROM `hospital_appointments`";
			sqlPrepStmt = dBConn.prepareStatement(GET_APPOINTMENTS);
			ResultSet sqlRes = sqlPrepStmt.executeQuery();
			if (sqlRes.next() != false) {
				appmtsList = new ArrayList<Appointments>();
				do {
					Appointments appmt = new Appointments(sqlRes.getInt("appointment_id"), sqlRes.getInt("doctor_id"),
							sqlRes.getInt("patient_id"), sqlRes.getInt("hospital_id"),
							sqlRes.getTimestamp("appointment_date_time"), sqlRes.getInt("appointment_status"));
					appmtsList.add(appmt);
				} while (sqlRes.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appmtsList;
	}

	public List<Appointments> getAppointmentsByHospitalIdDoctorId(int hospitalId, int doctorId) {
		List<Appointments> appmtsList = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String GET_APPOINTMENTS = "SELECT * FROM `hospital_appointments` WHERE `hospital_id` = ? AND `doctor_id` = ? AND `appointment_status` = ?";
			sqlPrepStmt = dBConn.prepareStatement(GET_APPOINTMENTS);
			sqlPrepStmt.setInt(1, hospitalId);
			sqlPrepStmt.setInt(2, doctorId);
			sqlPrepStmt.setInt(3, 1);
			System.out.println("Sql Query: " + sqlPrepStmt);
			ResultSet sqlRes = sqlPrepStmt.executeQuery();
			if (sqlRes.next() != false) {
				appmtsList = new ArrayList<Appointments>();
				do {
					Appointments appmt = new Appointments(sqlRes.getInt("appointment_id"), sqlRes.getInt("doctor_id"),
							sqlRes.getInt("patient_id"), sqlRes.getInt("hospital_id"),
							sqlRes.getTimestamp("appointment_date_time"), sqlRes.getInt("appointment_status"));
					appmtsList.add(appmt);
				} while (sqlRes.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appmtsList;
	}

	public List<Appointments> getAppointmentsByHospitalId(int hospitalId) {
		List<Appointments> appmtsList = null;
		Connection dBConn = null;
		PreparedStatement sqlPrepStmt = null;
		try {
			dBConn = configDBConn.connect();
			String GET_APPOINTMENTS = "SELECT * FROM `hospital_appointments` WHERE `hospital_id` = ?";
			sqlPrepStmt = dBConn.prepareStatement(GET_APPOINTMENTS);
			sqlPrepStmt.setInt(1, hospitalId);
			ResultSet sqlRes = sqlPrepStmt.executeQuery();
			if (sqlRes.next() != false) {
				appmtsList = new ArrayList<Appointments>();
				do {
					Appointments appmt = new Appointments(sqlRes.getInt("appointment_id"), sqlRes.getInt("doctor_id"),
							sqlRes.getInt("patient_id"), sqlRes.getInt("hospital_id"),
							sqlRes.getTimestamp("appointment_date_time"), sqlRes.getInt("appointment_status"));
					appmtsList.add(appmt);
				} while (sqlRes.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appmtsList;
	}

}
