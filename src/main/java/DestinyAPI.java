import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DestinyAPI
{
	private static final String BASE_ENDPOINT = "https://www.bungie.net/Platform/";

	private final HttpClient client;
	private final String apiKey;

	public DestinyAPI(String apiKey)
	{
		this.client = HttpClient.newHttpClient();
		this.apiKey = apiKey;
	}

	public void searchDestinyPlayer(String bungieName, int discrim) throws URISyntaxException, IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(this.getEndpoint("Destiny2/SearchDestinyPlayerByBungieName/All/"))
				.header("X-API-Key", this.apiKey)
				.POST(HttpRequest.BodyPublishers.ofString("{\"displayName\":\"" + bungieName + "\",\"displayNameCode\":" + discrim + "}"))
				.build();

		HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject obj = new JSONObject(response.body());
		System.out.println(obj.toString(4));
	}

	private URI getEndpoint(String endpoint) throws URISyntaxException
	{
		return new URI(BASE_ENDPOINT + endpoint);
	}
}