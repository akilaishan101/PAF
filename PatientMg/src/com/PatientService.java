package com;

import model.Patient;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("Patient")
public class PatientService {
	Patient patient = new Patient();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getPatient() {
		return patient.getPatient();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String AddPatient(
			@FormParam("FName") String FName,
			@FormParam("LName") String LName,
			@FormParam("NIC") String NIC,
			@FormParam("Age") int Age, 
			@FormParam("contactNo") String contactNo,
			@FormParam("Gender") String Gender, 
			@FormParam("Address") String Address,
			@FormParam("Email") String Email, 
			@FormParam("Gaurdian") String Gaurdian) {
		String output = patient.AddPatient(FName, LName, NIC, Age, contactNo, Gender, Address, Email, Gaurdian);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String UpdatePatient(String patientData) {
		// Convert the input string to a JSON object
		JsonObject patientObject = new JsonParser().parse(patientData).getAsJsonObject();
		// Read the values from the JSON object
		String patientID = patientObject.get("patientID").getAsString();
		String FName = patientObject.get("FName").getAsString();
		String LName = patientObject.get("LName").getAsString();
		String NIC = patientObject.get("NIC").getAsString();
		String Age = patientObject.get("Age").getAsString();
		String contactNo = patientObject.get("contactNo").getAsString();
		String Gender = patientObject.get("Gender").getAsString();
		String Address = patientObject.get("Address").getAsString();
		String Email = patientObject.get("Email").getAsString();
		String Gaurdian = patientObject.get("Gaurdian").getAsString();
		String output = patient.UpdatePatient(patientID, FName, LName, NIC, Age, contactNo, Gender, Address, Email, Gaurdian);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(String patientData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(patientData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String patientID = doc.select("patientID").text();
		String output = patient.deletePatient(patientID);
		return output;
	}

}
