package com;

import model.DocDetails;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/DocDetails")
public class DoctorDetails {
	DocDetails DocDetailsObj = new DocDetails();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppoinment() {
		return DocDetailsObj.readDoctorDetails();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertDoctorDetails(@FormParam("dName") String dName, @FormParam("age") String age,
			@FormParam("dSpecialization") String dSpecialization, @FormParam("contactNo") String contactNo,
			@FormParam("email") String email) {
		String output = DocDetailsObj.insertDoctorDetails(dName, age, dSpecialization, contactNo, email);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDocDetails(String DoctorDetailsData) {
		// Convert the input string to a JSON object
		JsonObject DocDetailsObject = new JsonParser().parse(DoctorDetailsData).getAsJsonObject();
		// Read the values from the JSON object
		String doctorID = DocDetailsObject.get("doctorID").getAsString();
		String dName = DocDetailsObject.get("dName").getAsString();
		String age = DocDetailsObject.get("age").getAsString();
		String dSpecialization = DocDetailsObject.get("dSpecialization").getAsString();
		String contactNo = DocDetailsObject.get("contactNo").getAsString();
		String email = DocDetailsObject.get("email").getAsString();
		String output = DocDetailsObj.updateDoctorDetails(doctorID, dName, age, dSpecialization, contactNo, email);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctorDetails(String DoctorDetailsData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(DoctorDetailsData, "", Parser.xmlParser());

		// Read the value from the element <doctorID>
		String doctorID = doc.select("doctorID").text();
		String output = DocDetailsObj.deleteDoctorDetails(doctorID);
		return output;
	}

}
