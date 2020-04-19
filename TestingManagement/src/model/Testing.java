package model;

import database.DBconnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Testing {
	
	DBconnect db = new DBconnect();

	public String insertTesting(String testName, String tDescription, String tDate, String tTime) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			
			String query = " insert into testing (testId, testName, tDescription, tDate, tTime )" + " values (?, ?, ?,?,? )";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, testName);
			preparedStmt.setString(3, tDescription);
			preparedStmt.setString(4, tDate);
			preparedStmt.setString(5, tTime);
		
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the testing." + e;
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readTesting() {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>TestId</th><th>TestName</th><th>TestDescription</th><th>Date</th><th>Time</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from testing";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String testId = Integer.toString(rs.getInt("testId"));
				String testName = rs.getString("testName");
				String tDescription= rs.getString("tDescription");
				String tDate = rs.getString("tDate");
				String tTime = rs.getString("tTime");

				// Add into the html table
				output += "<tr><td>" + testId + "</td>";
				output += "<td>" + testName + "</td>";
				output += "<td>" + tDescription + "</td>";
				output += "<td>" + tDate + "</td>";
				output += "<td>" + tTime + "</td>";
				// buttons
				output += "<td><input name=\"btnUpdate\" " + " type=\"button\" value=\"Update\"></td>"
						+ "<td><form method=\"post\" action=\"testing.jsp\">" + "<input name=\"btnRemove\" "
						+ " type=\"submit\" value=\"Remove\">" + "<input name=\"testId\" type=\"hidden\" "
						+ " value=\"" + testId + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the testing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateTesting(Integer testId, String testName, String tDescription, String tDate, String tTime) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE testing SET testName=?, tDescription=?, tDate=?,tTime=? WHERE testId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, testId);
			preparedStmt.setString(2, testName);
			preparedStmt.setString(3, tDescription);
			preparedStmt.setString(4, tDate);
			preparedStmt.setString(5, tTime);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the testing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteTesting(String testId) {
		
		String output = "";
		
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query ="delete from testing where testId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(testId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Deleted successfully";
			System.out.println("Deleted successfully");
			
		} catch (Exception e) {
			output = "Error while deleting the testing.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}


