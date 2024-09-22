package resources;

public enum APIResources {
	// API resource
	tickerAPI("/api/v3/ticker/24hr"), 
	exchangeAPI("/api/v3/exchangeInfo");

	private String resource;

	// constructor accepting the argument
	APIResources(String resource) {
		this.resource = resource;
	}

	public String getresource() {
		return resource;
	}

}
