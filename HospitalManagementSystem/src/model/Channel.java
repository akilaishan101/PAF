package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Channel {
	
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/channeling", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertChannel(String AppointmentDate, String AppointmentTime, String AppointmentCategory, String AppointmentDoctor)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 // create a prepared statement
	 String query = " insert into  cappointment (AppointmentID, AppointmentDate, AppointmentTime, AppointmentCategory, AppointmentDoctor)" + " values (?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, AppointmentDate);
	 preparedStmt.setString(3, AppointmentTime);
	 preparedStmt.setString(4, AppointmentCategory);
	 preparedStmt.setString(5, AppointmentDoctor); 
	 
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while inserting the channeling details.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }

	public String readChannel()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
		 return "Error while connecting to the database for reading."; 
		 }
	 // Prepare the html table to be displayed
	 output = "<table border=\"1\"><tr><th>Appointment ID</th><th>Appointment Date</th><th>Appointment Time</th><th>Appointment Category</th><th>Appointment Doctor</th><th>Update</th><th>Remove</th></tr>";
	 String query = "select * from cappointment";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String AppointmentID = Integer.toString(rs.getInt("AppointmentID"));
	 String AppointmentDate = rs.getString("AppointmentDate");
	 String AppointmentTime = rs.getString("AppointmentTime");
	 String AppointmentCategory = rs.getString("AppointmentCategory");
	 String AppointmentDoctor = rs.getString("AppointmentDoctor");
	 // Add into the html table
	 output += "<tr><td>" + AppointmentID + "</td>";
	 output += "<td>" + AppointmentDate + "</td>";
	 output += "<td>" + AppointmentTime + "</td>";
	 output += "<td>" + AppointmentCategory + "</td>";
	 output += "<td>" + AppointmentDoctor + "</td>";
	 // buttons
	 output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>" 
	 + "<td><form method=\"post\" action=\"channeling.jsp\">" 
	 + "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
	 + "<input name=\"AppointmentID\" type=\"hidden\" value=\"" + AppointmentID
	 + "\">" + "</form></td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading channeling details.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }

	public String updateChannel(String AppointmentID, String AppointmentDate, String AppointmentTime, String AppointmentCategory, String AppointmentDoctor) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				// create a prepared statement
				String query = "UPDATE cappointment SET AppointmentDate=?,AppointmentTime=?,AppointmentCategory=?,AppointmentDoctor=? WHERE AppointmentID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, AppointmentDate);
				preparedStmt.setString(2, AppointmentTime);
				preparedStmt.setString(3, AppointmentCategory);
				preparedStmt.setString(4, AppointmentDoctor);
				preparedStmt.setInt(5, Integer.parseInt(AppointmentID));
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Updated successfully";
			} catch (Exception e) {
				output = "Error while updating channeling details.";
				System.err.println(e.getMessage());
			}
			return output;
		}

	public String deleteChannel(String AppointmentID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from cappointment where AppointmentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(AppointmentID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting channeling details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}


