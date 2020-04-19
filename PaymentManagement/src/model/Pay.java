package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DBconnect;

public class Pay {
	// A common method to connect to the DB
	DBconnect db = new DBconnect();

	public String insertPay(String name, String amount, String date, String cardType, String cardNo) {
		String output = "";
		try {
			Connection con = db.connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = "insert into payment (payID, payName, payAmount, payDate, payCardType, payCardNo)"
					+ "values(?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, cardType);
			preparedStmt.setString(6, cardNo);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the pay.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPay() {
		String output = "";
		try {
			Connection con = db.connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Pay ID</th><th>Pay User Name</th><th>Pay Amount</th><th>Pay Date</th><th>Pay CardType</th><th>Pay CardNo</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String payID = Integer.toString(rs.getInt("payID"));
				String payName = rs.getString("payName");
				String payAmount = Double.toString(rs.getDouble("payAmount"));
				String payDate = rs.getString("payDate");
				String payCardType = rs.getString("payCardType");
				String payCardNo = rs.getString("payCardNo");

				// Add into the html table
				output += "<tr><td>" + payID + "</td>";
				output += "<td>" + payName + "</td>";
				output += "<td>" + payAmount + "</td>";
				output += "<td>" + payDate + "</td>";
				output += "<td>" + payCardType + "</td>";
				output += "<td>" + payCardNo + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"pay.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + payID + "\">" + "</form></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the pay.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePay(String id, String name, String amount, String date, String cardType, String cardNo) {
		String output = "";
		try {
			Connection con = db.connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET payName=?,payAmount=?,payDate=?,payCardType=?,payCardNo=? WHERE payID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setDouble(2, Double.parseDouble(amount));
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, cardType);
			preparedStmt.setString(5, cardNo);
			preparedStmt.setInt(6, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the pay.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePay(String id) {
		String output = "";
		try {
			Connection con = db.connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from payment where payID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the pay.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
