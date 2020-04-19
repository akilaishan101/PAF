package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DBconnect;

public class Appoinment {

	// A common method to connect to the DB
	DBconnect db = new DBconnect();

	public String insertAppoinment(String doctorName, String patientID, String patientName, String date, String time,
			String doctorFee, String status) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into appoinments (doctorID, doctorName, patientID,  patientName, date, time, doctorFee, "
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, doctorName);
			preparedStmt.setInt(3, Integer.parseInt(patientID));
			preparedStmt.setString(4, patientName);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, time);
			preparedStmt.setDouble(7, Double.parseDouble(doctorFee));
			preparedStmt.setString(8, status);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the appoinment." + e;
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readAppoinment() {
		String output = "";
		try {
			Connection con = db.connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Doctor ID</th><th>Doctor Name</th><th>Patient ID</th><th>Patient Name</th><th>Date</th><th>Time</th><th>Doctor Fee</th><th>Status</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from appoinments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String doctorID = Integer.toString(rs.getInt("doctorID"));
				String doctorName = rs.getString("doctorName");
				String patientID = Integer.toString(rs.getInt("patientID"));
				String patientName = rs.getString("patientName");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String doctorFee = Double.toString(rs.getDouble("doctorFee"));
				String status = rs.getString("status");

				// Add into the html table
				output += "<tr><td>" + doctorID + "</td>";
				output += "<td>" + doctorName + "</td>";
				output += "<td>" + patientID + "</td>";
				output += "<td>" + patientName + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + time + "</td>";
				output += "<td>" + doctorFee + "</td>";
				output += "<td>" + status + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"appoinments.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"doctorID\" type=\"hidden\" value=\"" + doctorID + "\">" + "</form></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Appoinment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateAppoinment(String doctorID, String doctorName, String patientID, String patientName,
			String date, String time, String doctorFee, String status) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE Appoinments SET doctorName=?,patientID=?,patientName=?,date=?,time=?,doctorFee=?,status=? WHERE doctorID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, doctorName);
			preparedStmt.setInt(2, Integer.parseUnsignedInt(patientID));
			preparedStmt.setString(3, patientName);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, time);
			preparedStmt.setDouble(6, Double.parseDouble(doctorFee));
			preparedStmt.setString(7, status);
			preparedStmt.setInt(8, Integer.parseUnsignedInt(doctorID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the appoinment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppoinment(String doctorID) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from appoinments where doctorID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(doctorID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the appoinment.";
			System.err.println(e.getMessage());
		}
		return output;

	}

}
