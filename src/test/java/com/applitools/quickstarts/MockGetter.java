package com.applitools.quickstarts;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

enum GetterType {
	FIGMA, ZEPLIN
}

public abstract class MockGetter {

	String apiUrl;
	String x_token;
	String pid;
	GetterType type;

	public MockGetter(String apiUrl, String x_token, String pid, GetterType type) {
		this.apiUrl = apiUrl;
		this.x_token = x_token;
		this.pid = pid;
		this.type = type;
	}

	public String[] getHeaderbyType() {
		switch (type) {
		case FIGMA:
			return new String[] { "X-Figma-Token", x_token };
		case ZEPLIN:
			return new String[] { "Authorization", "Bearer " + x_token };
		default:
			return null;

		}
	}
	
	public String getProjectId()
	{
		return pid;
	}
	
	public void setProjectId(String pid)
	{
		this.pid=pid;
	}


	public abstract JSONObject getProjectFiles();

	public abstract String[] getScreenImageUrl(String screenId);

	public JSONObject httpReq(String endPoint) {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl + "" + endPoint))
				.headers(getHeaderbyType()).build();

		HttpResponse<String> response;
		try {
			response = client.send(request, BodyHandlers.ofString());
			String res = response.body();
			JSONObject obj = new JSONObject("{\"arr\":"+res+"}");
			System.out.println(obj);
			return obj;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
