package br.com.ben.site.utils;

import com.google.gson.Gson;
import br.com.ben.site.model.ErrorHandler;
import org.apache.http.client.utils.URIBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
	
	URIBuilder endpoint = null;
	Map<String,String> defaultHeaders = null;
	URL url = null;
	HttpURLConnection connection = null;
	Gson gson;
	Reader response = null;
	ErrorHandler errorHandler = null;

	public String generateEndpoint(String host, Map<String, String> parameters) {
		try {
			endpoint = new URIBuilder(host);
			if (parameters != null)
				parameters.forEach((key,value) -> endpoint.addParameter(key, value));
			return endpoint.toString().replaceAll("\\+", "%20");
		} 
		catch (URISyntaxException e) {
			System.out.println("\"Error\":{\"Generate_Endpoint\":{ \"Message\":\""+e.getMessage()+"\"}}");
			e.printStackTrace();
			return null;
		}
	}
	public String generate(Map<String, String> parameters){
		endpoint = new URIBuilder();
		if (parameters != null)
			parameters.forEach((key,value) -> endpoint.addParameter(key, value));
		String response = endpoint.toString().replaceAll("\\+", "%20");
		return response.substring(1, response.length());
	}
	
	@SuppressWarnings("unchecked")
	public Object doRequest(Map<String, Object> requestDescription, String endpointDescription, Class<?> object_reflection) {
		try {
			url = new URL(((String)requestDescription.get("endpoint")));
			System.out.println("URL ->" + url);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod((String)requestDescription.get("method"));
			
			if ((Object)requestDescription.get("headers") != null)
				((Map<String, String>)requestDescription.get("headers")).forEach((key,value) -> connection.addRequestProperty(key, value));		
			defaultHeaders.forEach((key,value) -> connection.setRequestProperty(key, value));
			

			Gson gson = new Gson();
			response = new InputStreamReader(connection.getInputStream());
	
			return gson.fromJson(response, Class.forName(object_reflection.getName())); 
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
			return normalizeResponseError(endpointDescription, String.format("\"Error\":{\"URLFormatError\":{\"%s\":{\"Message\": \"Verifique se a url %s está correta !\"}}}",endpointDescription, (String)requestDescription.get("endpoint")));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return normalizeHttpResponseError(connection, endpointDescription);
		}
		catch (ProtocolException e) {
			e.printStackTrace();
			return normalizeResponseError(endpointDescription, String.format("\"Error\":{\"ProtocolError\":{\"%s\":{\"Message\": \"%s\"}}}",endpointDescription, "Método HTTP inválido para o endpoint !"));
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return normalizeResponseError(endpointDescription, String.format("\"Error\":{\"GsonParserError\":{\"%s\":{\"Message\": \"%s\"}}}", endpointDescription, e.getMessage()));	
		}
		catch (IOException e) {
			e.printStackTrace();
			try {
				if (connection.getResponseCode() == -1)
					return normalizeResponseError(endpointDescription, String.format("\"Error\":{\"ConnectionError\":{\"%s\":{\"Message\": \"%s\"}}}", endpointDescription, e.getMessage()));
				else 
					return normalizeHttpResponseError(connection, endpointDescription);
			} 
			catch (IOException e1) {
				e1.printStackTrace();
				return normalizeResponseError(endpointDescription, String.format("\"Error\":{\"ReadInputStreamError\":{\"%s\":{\"Message\": \"%s\"}}}",endpointDescription, e.getMessage()));
			}
		}
	}

	private Object normalizeResponseError(String endpointDescription, String messageError) {
		errorHandler = new ErrorHandler();
		errorHandler.setMessages(new ArrayList<>());
		errorHandler.getMessages().add(0, messageError);
		return errorHandler;
	}
	
	private Object normalizeHttpResponseError(HttpURLConnection connection, String endpointDescription) {
		try {
			gson = new Gson();
			errorHandler = new ErrorHandler();
			errorHandler.setMessages(new ArrayList<>());
			errorHandler.setStatusCode(connection.getResponseCode());
			String text = null;
			
			if (connection.getResponseCode() == 400) {
				try {
					response = new InputStreamReader(connection.getErrorStream());
					errorHandler = gson.fromJson(response, ErrorHandler.class);
					errorHandler.setStatusCode(connection.getResponseCode());
					text = errorHandler.getMessages().get(0);
					
					if (text.contains("{"))
						text = text.substring(text.indexOf(":")+3, text.lastIndexOf("}")-2);
					
					errorHandler.getMessages().add(0, String.format("\"Error\":{\"HttpError\":{\"%s\":{\"StatusCode\":%d, \"Message\": \"%s\"}}}", 
							endpointDescription, errorHandler.getStatusCode(), text));	
				}
				catch(Exception e) {
					errorHandler.getMessages().add(0, String.format("\"Error\":{\"HttpError\":{\"%s\":{\"StatusCode\":%d, \"Message\": \"%s\"}}}",
							endpointDescription, errorHandler.getStatusCode(), 
							"Requisição invÃ¡lida, verifique se os dados enviados estÃ£o corretos !"));
				}
			}
			else if (connection.getResponseCode() == 403) 
				errorHandler.getMessages().add(0, String.format("\"Error\":{\"HttpError\":{\"%s\":{\"StatusCode\":%d, \"Message\": \"%s\"}}}",endpointDescription, errorHandler.getStatusCode(), "Credenciais nÃ£o autorizadas, verifique se estÃ£o corretas !"));
			
			else if (connection.getResponseCode() == 404)
				errorHandler.getMessages().add(0, String.format("\"Error\":{\"HttpError\":{\"%s\":{\"StatusCode\":%d, \"Message\": \"%s\"}}}", endpointDescription, errorHandler.getStatusCode(),"Recurso nÃ£o encontrado"));
			
			else if (connection.getResponseCode() == 500)
				errorHandler.getMessages().add(0, String.format("\"Error\":{\"HttpError\":{\"%s\":{\"StatusCode\":%d, \"Message\": \"%s\"}}}", endpointDescription, errorHandler.getStatusCode(),"Erro interno no servidor da Conductor"));	
			
			else {
				try {
					response = new InputStreamReader(connection.getErrorStream());
					errorHandler = gson.fromJson(response, ErrorHandler.class);
					errorHandler.setStatusCode(connection.getResponseCode());
					text = errorHandler.getMessages().get(0);
					
					if (text.contains("{"))
						text = text.substring(text.indexOf(":")+3, text.lastIndexOf("}")-2);
					
					errorHandler.getMessages().add(0, String.format("\"Error\":{\"HttpError\":{\"%s\":{\"StatusCode\":%d, \"Message\": \"%s\"}}}", 
							endpointDescription, errorHandler.getStatusCode(), text));	
				}
				catch(Exception e) {
					errorHandler.getMessages().add(0, String.format("\"Error\":{\"HttpError\":{\"%s\":{\"StatusCode\":%d, \"Message\": \"%s\"}}}", 
							endpointDescription, errorHandler.getStatusCode(), e.getMessage()));
				}
			}
			
			return errorHandler;
		} 
		catch (Exception e) {
			e.printStackTrace();
			String error = gson.fromJson(response, String.class);
			System.out.println(error);
			return null;
		}
	}
	
	public void clearMappers(Map<?, ?>...mappers) {
		for(Map<?,?> map: mappers)
			map.clear();
	}
	
	public Utils() {
		defaultHeaders = new HashMap<>();
		defaultHeaders.put("Content-Type", "application/json");
		defaultHeaders.put("Accept-Charset", "utf-8");
	}

	
}
