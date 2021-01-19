package br.com.ben.site.DAO;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import br.com.ben.site.model.BenRequestHandler;
import br.com.ben.site.utils.Utils;

import java.util.Map;

public class UniConnection {
	private String host;
	private String clientId = System.getenv("CLIENT-ID");
	private String route;
	private Map<String, String> headers;
	private BenRequestHandler request;
	private String parameters;
	private HttpResponse<String> response;

	public UniConnection(Map<String, String> headers, BenRequestHandler benRequestHandler, String host, String route) {
		this.headers = headers;
		this.headers.put("Content-Type", "application/json");
		this.headers.put("client_id", clientId);
		this.headers.put("cache-control", "no-cache");
		this.host = host;
		this.route = route;
		this.request = benRequestHandler;
		this.parameters = new Gson().toJson(benRequestHandler.getParameters());
	}

	public UniConnection(BenRequestHandler benRequestHandler){
		this.request = benRequestHandler;
		this.parameters = new Gson().toJson(benRequestHandler.getParameters());
	}

	public String connectPost() {
		try {
			response = Unirest.post(host + route).headers(headers).body(parameters).asString();
			System.out.println(response.getBody());
			return response.getBody();
		} catch (UnirestException e) {
			e.printStackTrace();
			return "{ Error: [\"" + e.getMessage() + "\"]}";
		}
	}

	public String connectGet() {
		Utils val = new Utils();
		String endpoint = val.generateEndpoint(host + route, request.getParameters());
		System.out.println("ENDPOINT ==>>" + endpoint);
		try {
			response = Unirest.get(endpoint)
				.headers(headers)
				.asString();
			System.out.println(response.getBody());
			return response.getBody();
		} catch(UnirestException e) {
			e.printStackTrace();
			return "{ Error: [\"" + e.getMessage()+"\"]}";
		}
	}

	public String indicacao(){
		Utils val = new Utils();
		parameters = val.generate(request.getParameters());
		try{	
			response = Unirest.post(System.getenv("SALESFORCE-FORM"))
				.header("content-type", "application/x-www-form-urlencoded")
				.body(parameters)
				.asString();
			return response.getBody() + response.getStatus();
			
		}catch(UnirestException e){
			e.printStackTrace();
			return "{ Error: [\"" + e.getMessage()+"\"]}";
		}
	}
}
