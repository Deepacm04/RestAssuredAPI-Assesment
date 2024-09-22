package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

//import io.restassured.path.json.JsonArray;

//import files.Payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	public static int size;
	public static RequestSpecification req;

//Method to capture Base Url and print the request and response logs

	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("Logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return req;
		}
		return req;
	}

//method to retrieve the values from global.properties file
	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\deecm\\Documents\\RestAssured\\API\\src\\main\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);

	}

//method to parse JSON
	public String getJsonPath(Response response, String key) {
		try {
			String resp = response.asString();
			JsonPath js = new JsonPath(resp);
			Object keyValue = js.get(key);

			if (keyValue == null) {

				return null;

			}

			// If the value is a list (array)
			if (keyValue instanceof List<?>) {
				// If it's an array iterate through the array
				List<?> jsonArray = (List<?>) keyValue;
				StringBuilder result = new StringBuilder();

				for (int i = 0; i < jsonArray.size(); i++) {
					result.append("Element ").append(i).append(": ").append(jsonArray.get(i).toString()).append("\n");
				}

				String result12 = result.toString();
				return result.toString();
			} else {
				return keyValue.toString();
			}
		} catch (Exception e) {
			System.err.println("Error occurred while fetching key [" + key + "]: " + e.getMessage());
			e.printStackTrace();
			return "Error: Unable to retrieve key [" + key + "]";
		}
	}

//Method to find size of the response
	public int getJsonPathSize(Response response, String arrayPath) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);

		// Get the size of the array
		List<Object> jsonArray = js.getList(arrayPath);

		return jsonArray.size();
	}

}
