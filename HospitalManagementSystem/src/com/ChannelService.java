package com;

import model.Channel;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Channel")
public class ChannelService {
	Channel channelingObj = new Channel();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readChannel() {
		return channelingObj.readChannel();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertChannel(@FormParam("AppointmentDate") String AppointmentDate,
			@FormParam("AppointmentTime") String AppointmentTime,
			@FormParam("AppointmentCategory") String AppointmentCategory,
			@FormParam("AppointmentDoctor") String AppointmentDoctor) {
		String output = channelingObj.insertChannel(AppointmentDate, AppointmentTime, AppointmentCategory,
				AppointmentDoctor);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateChannel(String channelingData) {
		// Convert the input string to a JSON object
		JsonObject channelingObject = new JsonParser().parse(channelingData).getAsJsonObject();
		// Read the values from the JSON object
		String AppointmentID = channelingObject.get("AppointmentID").getAsString();
		String AppointmentDate = channelingObject.get("AppointmentDate").getAsString();
		String AppointmentTime = channelingObject.get("AppointmentTime").getAsString();
		String AppointmentCategory = channelingObject.get("AppointmentCategory").getAsString();
		String AppointmentDoctor = channelingObject.get("AppointmentDoctor").getAsString();
		String output = channelingObj.updateChannel(AppointmentID, AppointmentDate, AppointmentTime,
				AppointmentCategory, AppointmentDoctor);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteChannel(String channelingData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(channelingData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String AppointmentID = doc.select("AppointmentID").text();
		String output = channelingObj.deleteChannel(AppointmentID);
		return output;
	}

}
