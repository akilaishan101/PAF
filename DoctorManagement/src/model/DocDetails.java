package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DBconnect;

public class DocDetails {

	// A common method to connect to the DB
	DBconnect db = new DBconnect();

	public String insertDoctorDetails(String dName, String age, String dSpecialization, String contactNo,
			String email) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into doctord (doctorID, dName, age, dSpecialization, contactNo, email)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, dName);
			preparedStmt.setInt(3, Integer.parseInt(age));
			preparedStmt.setString(4, dSpecialization);
			preparedStmt.setString(5, contactNo);
			preparedStmt.setString(6, email);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the doctor details." + e;
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readDoctorDetails() {
		String output = "";
		try {
			Connection con = db.connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Doctor ID</th><th>Doctor Name</th><th>Age</th><th>Specialization</th><th>Contact Number</th><th>Email</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from doctord";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String doctorID = Integer.toString(rs.getInt("doctorID"));
				String dName = rs.getString("dName");
				String age = Integer.toString(rs.getInt("age"));
				String dSpecialization = rs.getString("dSpecialization");
				String contactNo = rs.getString("contactNo");
				String email = rs.getString("email");

				// Add into the html table
				output += "<tr><td>" + doctorID + "</td>";
				output += "<td>" + dName + "</td>";
				output += "<td>" + age + "</td>";
				output += "<td>" + dSpecialization + "</td>";
				output += "<td>" + contactNo + "</td>";
				output += "<td>" + email + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"doctor.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"doctorID\" type=\"hidden\" value=\"" + doctorID + "\">" + "</form></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Doctor Details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateDoctorDetails(String doctorID, String dName, String age, String dSpecialization,
			String contactNo, String email) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE doctord SET dName=?,age=?,dSpecialization=?,contactNo=?,email=? WHERE doctorID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, dName);
			preparedStmt.setInt(2, Integer.parseUnsignedInt(age));
			preparedStmt.setString(3, dSpecialization);
			preparedStmt.setString(4, contactNo);
			preparedStmt.setString(5, email);
			preparedStmt.setInt(6, Integer.parseUnsignedInt(doctorID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the doctor details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteDoctorDetails(String doctorID) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from doctord where doctorID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(doctorID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the DoctorDetails.";
			System.err.println(e.getMessage());
		}
		return output;

	}

}
