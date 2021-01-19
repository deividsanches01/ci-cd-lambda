package br.com.ben.site.function;

import br.com.ben.site.DAO.Authentication;
import br.com.ben.site.DAO.Service;
import br.com.ben.site.DAO.UniConnection;
import br.com.ben.site.model.BenRequestHandler;
import br.com.ben.site.model.ErrorHandler;
import br.com.ben.site.model.ServiceEnderecos;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        LambdaLogger logger = context.getLogger();
        context.getLogger().log("Java HTTP trigger processed a request.");

        String postJson = request.getBody();

        // Parse query parameter
        if (postJson == null || postJson.isEmpty()){
            postJson = request.getQueryStringParameters().get("name");
        }

        BenRequestHandler benRequestHandler;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            benRequestHandler = gson.fromJson(postJson, BenRequestHandler.class);
        } catch (com.google.gson.JsonSyntaxException e) {
            return createResponse(HttpStatus.SC_BAD_REQUEST, "\"Error\":{\"BodyGsonSintaxError\":{\"StatusCode\":400, \"Message\": \"" + e + "\"}}");
        }

        String request_json;
        try {
            request_json = handler(benRequestHandler);
        } catch (UnsupportedEncodingException e) {
            return createResponse(HttpStatus.SC_BAD_REQUEST, e.getMessage());
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("content-Type", "application/json;charset=utf-8");
        if (request_json.contains("Erro") || request_json.contains("exception")) {
            if (request_json.contains("400")) {
                return createResponse(HttpStatus.SC_BAD_REQUEST, request_json, headers);
            } else if (request_json.contains("404"))
                return createResponse(HttpStatus.SC_NOT_FOUND, request_json, headers);
            else if (request_json.contains("500"))
                return createResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, request_json, headers);
            else
                return createResponse(HttpStatus.SC_BAD_REQUEST, request_json, headers);
        }
        return createResponse(HttpStatus.SC_OK,request_json, headers);
    }

    private APIGatewayProxyResponseEvent createResponse(int statusCode, String body, Map<String, String> headers) {
        APIGatewayProxyResponseEvent response = createResponse(statusCode, body);
        response.setHeaders(headers);
        return response;
    }

    private APIGatewayProxyResponseEvent createResponse(int statusCode, String body) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }

    public String handler(BenRequestHandler benRequestHandler) throws UnsupportedEncodingException {
        long start, end;
        String json_return;
        System.out.println("[Warning] .... Iniciando ....");
        System.out.println("---------------------------------------------");
        System.out.println(String.format("[Success] Request Input Json => %s\n", new Gson().toJson(benRequestHandler)));
        start = System.currentTimeMillis();

        Service services = new Service();
        Object response_auhentication = new Authentication().doAuthentication();
        Map<String, String> headers;

        if (response_auhentication instanceof Map<?, ?> && response_auhentication != null)
            headers = (Map<String, String>) response_auhentication;
        else {
            json_return = String.format("{%s}",
                    ((ErrorHandler) response_auhentication).getMessages().get(0));
            System.out.println(((ErrorHandler) response_auhentication).getMessages().get(0));
            String txtUTF8 = new String(json_return.getBytes("ISO-8859-1"), "UTF-8");
            return txtUTF8;
        }
        if (benRequestHandler.getResourcePath().equals("estabelecimentos")) {
            UniConnection apiConnect = new UniConnection(headers, benRequestHandler, System.getenv("HEIMDALL-HOST"), System.getenv("GET-ESTABELECIMENTOS-ROUTE"));

            System.out.println("[Warning] .... CONNECTING TO ESTABELECIMENTOS ....");

            String response = apiConnect.connectGet();

            end = System.currentTimeMillis();
            System.out.println("[SUCCESS] .... ACCESS COMPLETE ....");
            System.out.println("--------------------------------------------------");
            System.out.println("\nStart: " + start + " ms");
            System.out.println("End: " + end + " ms");
            System.out.println("Runtime: " + String.valueOf(end - start) + " ms\n\n");
            return response;
        }
        if (benRequestHandler.getResourcePath().equals("blue-api")) {
            headers.put("access_token", System.getenv("BLUE-ACCESS-TOKEN"));
            UniConnection apiConnect = new UniConnection(headers, benRequestHandler, System.getenv("BLUEAPI-HOST"), System.getenv("BLUE-ROUTE"));

            System.out.println("---------------------------------------------");
            System.out.println("[Warning] .... CONNECTING TO BLUE API ....");

            String response = apiConnect.connectPost();

            end = System.currentTimeMillis();
            System.out.println("[SUCCESS] .... ACCESS COMPLETE ....");
            System.out.println("\nStart: " + start + " ms");
            System.out.println("End: " + end + " ms");
            System.out.println("Runtime: " + String.valueOf(end - start) + " ms\n\n");
            return response;

        }
        if (benRequestHandler.getResourcePath().equals("indicator-ec")) {
            UniConnection apiConnect = new UniConnection(headers, benRequestHandler, System.getenv("REC-EC-HOST"), System.getenv("INDICA-EC-ROUTE"));

            System.out.println("[Warning] .... CONNECTING TO INDICATOR EC API ....");

            String response = apiConnect.connectPost();

            end = System.currentTimeMillis();
            System.out.println("[SUCCESS] .... ACCESS COMPLETE ....");
            System.out.println("--------------------------------------------------");
            System.out.println("\nStart: " + start + " ms");
            System.out.println("End: " + end + " ms");
            System.out.println("Runtime: " + String.valueOf(end - start) + " ms\n\n");
            return response;

        }
        if (benRequestHandler.getResourcePath().equals("endereco")) {
            ServiceEnderecos response = services.getEnderecos(benRequestHandler.getParameters(), headers);

            if (response.getError() != null) {
                json_return = String.format("{%s}", response.getError().getMessages().get(0));
                System.out.println((response).getError().getMessages().get(0));
            } else {
                json_return = new Gson().toJson(response);
                System.out.println(String.format("[Success] Response API => %s", json_return));
            }

            String txtUTF8 = new String(json_return.getBytes("ISO-8859-1"), "UTF-8");
            return txtUTF8;
        }
        if (benRequestHandler.getResourcePath().equals("faq")) {
            UniConnection apiConnect = new UniConnection(headers, benRequestHandler, System.getenv("REC-EC-HOST"), "/faq");

            System.out.println("[Warning] .... CONNECTING TO FAQ ....");

            String response = apiConnect.connectGet();

            end = System.currentTimeMillis();
            System.out.println("[SUCCESS] .... ACCESS COMPLETE ....");
            System.out.println("--------------------------------------------------");
            System.out.println("\nStart: " + start + " ms");
            System.out.println("End: " + end + " ms");
            System.out.println("Runtime: " + String.valueOf(end - start) + " ms\n\n");
            return response;
        }
        if (benRequestHandler.getResourcePath().equals("indicacao")) {
            UniConnection apiConnect = new UniConnection(benRequestHandler);

            System.out.println("[Warning] .... CONNECTING TO SALESFORCE ....");

            String response = apiConnect.indicacao();

            end = System.currentTimeMillis();
            System.out.println("[SUCCESS] .... ACCESS COMPLETE ....");
            System.out.println("--------------------------------------------------");
            System.out.println("\nStart: " + start + " ms");
            System.out.println("End: " + end + " ms");
            System.out.println("Runtime: " + String.valueOf(end - start) + " ms\n\n");
            return response;

        } else {
            String response = "{\"Error\":{\"ProtocolError\":{\"UnknowResourcePath\":{\"Message\": \"Coloque o ResourcePath valido!!\"}}}}";

            end = System.currentTimeMillis();

            System.out.println("\nStart: " + start + " ms");
            System.out.println("End: " + end + " ms");
            System.out.println("Runtime: " + String.valueOf(end - start) + " ms\n\n");

            return response;
        }
    }

}
