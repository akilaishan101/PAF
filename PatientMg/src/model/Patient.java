package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospitalmanagement", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String AddPatient(String FName, String LName, String NIC, int Age, String contactNo,
			String Gender, String Address, String Email, String Gaurdian) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " INSERT INTO Patient(patientID,FName,LName,NIC,Age,contactNo,Gender,Address,Email,Gaurdian) values (?, ?, ?, ?, ?, ?, ?, ?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, FName);
			preparedStmt.setString(3, LName);
			preparedStmt.setString(4, NIC);
			preparedStmt.setInt(5, Age);
			preparedStmt.setString(6, contactNo);
			preparedStmt.setString(7, Gender);
			preparedStmt.setString(8, Address);
			preparedStmt.setString(9, Email);
			preparedStmt.setString(10, Gaurdian);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Patient information." + e;
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String getPatient() {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while reading the data.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Patient ID</th><th>First Name</th><th>Last Name</th><th>NIC</th><th>Age</th><th>Contact No</th><th>Gender</th><th>Address</th><th>Email</th><th>Gaurdian</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from Patient";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String patientID = Integer.toString(rs.getInt("patientID"));
				String FName = rs.getString("FName");
				String LName = rs.getString("LName");
				String NIC = rs.getString("NIC");
				String Age = Integer.toString(rs.getInt("Age"));
				String contactNo = rs.getString("contactNo");
				String Gender = rs.getString("Gender");
				String Address = rs.getString("Address");
				String Email = rs.getString("Email");
				String Gaurdian = rs.getString("Gaurdian");

				// Add into the html table
				output += "<tr><td>" + patientID + "</td>";
				output += "<td>" + FName + "</td>";
				output += "<td>" + LName + "</td>";
				output += "<td>" + NIC + "</td>";
				output += "<td>" + Age + "</td>";
				output += "<td>" + contactNo + "</td>";
				output += "<td>" + Gender + "</td>";
				output += "<td>" + Address + "</td>";
				output += "<td>" + Email + "</td>";
				output += "<td>" + Gaurdian + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"patient.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"patientID\" type=\"hidden\" value=\"" + patientID + "\">" + "</form></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String UpdatePatient(String patientID, String FName, String LName, String NIC, String Age, String contactNo,
			String Gender, String Address, String Email, String Gaurdian) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE Patient SET FName=?,LName=?,NIC=?,Age=?,contactNo=?,Gender=?,Address=?, Email=?, Gaurdian=? WHERE patientID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values\
			preparedStmt.setString(1, FName);
			preparedStmt.setString(2, LName);
			preparedStmt.setString(3, NIC);
			preparedStmt.setInt(4, Integer.parseUnsignedInt(Age));
			preparedStmt.setString(5, contactNo);
			preparedStmt.setString(6, Gender);
			preparedStmt.setString(7, Address);
			preparedStmt.setString(8, Email);
			preparedStmt.setString(9, Gaurdian);
			preparedStmt.setInt(10, Integer.parseUnsignedInt(patientID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating patient information.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePatient(String patientID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while deleting.";
			}
			// create a prepared statement
			String query = "delete from Patient where patientID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(patientID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the patient.";
			System.err.println(e.getMessage());
		}
		return output;

	}

}
