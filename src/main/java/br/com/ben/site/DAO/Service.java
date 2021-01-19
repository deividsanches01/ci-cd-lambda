package br.com.ben.site.DAO;

import br.com.ben.site.model.ErrorHandler;
import br.com.ben.site.model.ServiceEnderecos;
import br.com.ben.site.model.ServiceEstabelecimentos;
import br.com.ben.site.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Service {

	String host = System.getenv("HEIMDALL-HOST");
	Utils utils = new Utils();
	Map<String,Object> requestDescription = new HashMap<>();
	
	public ServiceEstabelecimentos getEstabelecimentos(Map<String,String> parameters, Map<String,String> headers) {
		//Pega as informações dos estabelecimentos
		System.out.println("[Warning]... Get_Estabelecimentos ...");
		try {
			requestDescription.put("endpoint", utils.generateEndpoint(String.format("%s%s", host, System.getenv("GET-ESTABELECIMENTOS-ROUTE")),parameters));
			requestDescription.put("method", "GET");
			requestDescription.put("headers", headers);
			
			System.out.println(String.format("[Warning] Endpoint: %s",(String)requestDescription.get("endpoint")));
			Object response = utils.doRequest(requestDescription, "Get_Estabelecimento", ServiceEstabelecimentos.class);
			utils.clearMappers(requestDescription, parameters);
			ServiceEstabelecimentos estabelecimentos;
			
			if (response instanceof ServiceEstabelecimentos && response != null)
				return (ServiceEstabelecimentos) response;
			
			else {
				estabelecimentos = new ServiceEstabelecimentos();
				estabelecimentos.setError((ErrorHandler)response);
				return estabelecimentos;
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public ServiceEnderecos getEnderecos(Map<String,String> parameters, Map<String,String> headers) {
		System.out.println("[Warning]... Get_Enderecos ...");
		try {
			requestDescription.put("endpoint", utils.generateEndpoint(String.format("%s%s", host, System.getenv("GET-ESTABELECIMENTOS-ENDERECOS-ROUTE")),parameters));
			requestDescription.put("method", "GET");
			requestDescription.put("headers", headers);
			
			System.out.println(String.format("[Warning] Endpoint: %s",(String)requestDescription.get("endpoint")));
			Object response = utils.doRequest(requestDescription, "Get_Endereços", ServiceEnderecos.class);
			utils.clearMappers(requestDescription, parameters);
			ServiceEnderecos enderecos;
			
			if (response instanceof ServiceEnderecos && response != null)
				return (ServiceEnderecos) response;
			
			else {
				enderecos = new ServiceEnderecos();
				enderecos.setError((ErrorHandler)response);
				return enderecos;
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
