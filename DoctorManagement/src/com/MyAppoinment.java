package com;

import model.Appoinment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Appoinment")
public class MyAppoinment {
	Appoinment appoinmentObj = new Appoinment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppoinment() {
		return appoinmentObj.readAppoinment();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAppoinment(@FormParam("doctorName") String doctorName, @FormParam("patientID") String patientID,
			@FormParam("patientName") String patientName, @FormParam("date") String date,
			@FormParam("time") String time, @FormParam("doctorFee") String doctorFee,
			@FormParam("status") String status) {
		String output = appoinmentObj.insertAppoinment(doctorName, patientID, patientName, date, time, doctorFee,
				status);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAppoinment(String appoinmentData) {
		// Convert the input string to a JSON object
		JsonObject appoinmentObject = new JsonParser().parse(appoinmentData).getAsJsonObject();
		// Read the values from the JSON object
		String doctorID = appoinmentObject.get("doctorID").getAsString();
		String doctorName = appoinmentObject.get("doctorName").getAsString();
		String patientID = appoinmentObject.get("patientID").getAsString();
		String patientName = appoinmentObject.get("patientName").getAsString();
		String date = appoinmentObject.get("date").getAsString();
		String time = appoinmentObject.get("time").getAsString();
		String doctorFee = appoinmentObject.get("doctorFee").getAsString();
		String status = appoinmentObject.get("status").getAsString();
		String output = appoinmentObj.updateAppoinment(doctorID, doctorName, patientID, patientName, date, time,
				doctorFee, status);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAppoinment(String appoinmentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(appoinmentData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String doctorID = doc.select("doctorID").text();
		String output = appoinmentObj.deleteAppoinment(doctorID);
		return output;
	}

}
