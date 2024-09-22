package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.Utils;
import static org.junit.Assert.assertTrue;

public class TickerAPISteps extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	String symbolName;
	String firstId;
	Response exchangeResponse;
	String baseAsset;
	String quoteAsset;

	@Given("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) throws IOException {
		res = given().spec(requestSpecification());
		APIResources resourceAPI = APIResources.valueOf(resource);

		response = res.when().get(resourceAPI.getresource());

	}

	@Then("API call is success with status {int}")
	public void api_call_is_success_with_status(Integer int1) {
		assertEquals(response.statusCode(), 200);

	}

	@When("symbol contains {string} and firstId is above {int} then count should be above {int}")
	public void symbol_contains_and_first_id_is_above_then_count_should_be_above(String symbol, Integer value,
			Integer count) {

		// Get the size of the array
		int size = getJsonPathSize(response, "response");
		System.out.println("Response size: " + size);

		// Iterate over the array
		for (int i = 0; i < size; i++) {
			// Access the array elements
			symbolName = getJsonPath(response, "[" + i + "].symbol");
			String firstIdString = getJsonPath(response, "[" + i + "].firstId");
			String countString = getJsonPath(response, "[" + i + "].count");

			// Parse firstId and count only if they are not null or empty
			if (firstIdString != null && !firstIdString.isEmpty() && countString != null && !countString.isEmpty()) {
				long firstId_value = Long.parseLong(firstIdString);
				long count_value = Long.parseLong(countString);

				// Check if symbol contains 'XRP', firstId > 0, and count > 0
				if (symbolName != null && symbolName.contains("XRP") && firstId_value > 0 && count_value > 0) {
					System.out.println("Conditions met: " + symbolName);
				} else {
					if (symbolName == null || !symbolName.contains("XRP")) {
						System.out.println("Symbol does not contain XRP, Symbol =  " + symbolName);
					}
					if (firstId_value <= 0) {
						System.out.println("FirstId is not above 0, Symbol = " + symbolName);
					}
					if (count_value <= 0) {
						System.out.println("Count is not above 0, Symbol = " + symbolName);
					}
				}
			} else {
				System.out.println("FirstId or Count is null or empty for index: " + i);
			}
		}
	}

	@When("user hits {string} with {string} http request")
	public void user_hits_with_http_request(String resource, String method) throws IOException {
		int size = getJsonPathSize(response, "response");

		// Iterate over the array
		for (int i = 0; i < size; i++) {
			// Access the array elements
			symbolName = getJsonPath(response, "[" + i + "].symbol");

			res = given().spec(requestSpecification()).queryParam("symbol", symbolName);

			APIResources resourceAPI = APIResources.valueOf(resource);
			exchangeResponse = res.when().get(resourceAPI.getresource());
			baseAsset = getJsonPath(exchangeResponse, "symbols[0].baseAsset");
			quoteAsset = getJsonPath(exchangeResponse, "symbols[0].quoteAsset");
			verify_is_a_concatination_of_and_value(symbolName, baseAsset, quoteAsset);

		}
	}

	@Then("verify {string} is a concatination of {string} and {string} value")
	public void verify_is_a_concatination_of_and_value(String symbolName, String baseAsset, String quoteAsset) {

		// Check if baseAsset + quoteAsset == symbol
		if (baseAsset != null && quoteAsset != null && symbolName != null) {
			String concatenated = baseAsset + quoteAsset;

			// Log the comparison result
			if (concatenated.equals(symbolName)) {
				System.out.println("Match found for symbol: " + symbolName);
			} else {
				System.out.println("Mismatch for symbol: " + symbolName + " | Expected: " + concatenated + " | Found: "
						+ symbolName);
			}
		} else {
			System.out.println("One or more fields (baseAsset, quoteAsset, symbol) are null for symbol: " + symbolName);
		}

	}

}
