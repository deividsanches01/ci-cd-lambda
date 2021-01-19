package br.com.ben.site.DAO;

import br.com.ben.site.model.Code;
import br.com.ben.site.model.CredentialsEncrypt;
import br.com.ben.site.model.ErrorHandler;
import br.com.ben.site.utils.Utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

public class Authentication {
	
	String host = System.getenv("HEIMDALL-HOST");
	String publicKey = null;
	Utils utils = new Utils();
	Map<String,Object> requestDescription = new HashMap<>();
	Map<String, String> headers = new HashMap<>(), parameters = new HashMap<>();


	public String generateCredential() throws Exception {
		System.out.println("[Warning]... Genetare_Credential ...");
		Date time = Calendar.getInstance().getTime(); 
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		
		System.out.println("[Warning] Data: "+format.format(time));
		System.out.println("[Warning] Timestamp: "+String.valueOf(time.getTime()));
		
		String credentialTime = String.valueOf(time.getTime());
		byte[] credentialTime64 = Base64.getEncoder().encode(credentialTime.getBytes());
		
		System.out.println(String.format("[Warning] Timestamp Base64Encoded: %s",new String(credentialTime64)));
		byte[] credential = encryptRSA(credentialTime64); 
		
		return Base64.getEncoder().encodeToString(credential);
	}

	private byte[] encryptRSA(byte[] data) throws Exception {
		PublicKey pubKey = getPublicKey(publicKey);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.PUBLIC_KEY, pubKey);
		return cipher.doFinal(data);
	}

	private PublicKey getPublicKey(String pubKey64) throws NoSuchAlgorithmException, InvalidKeySpecException {
		pubKey64 = pubKey64.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");//Remove o cabeçalho e rodapé
		KeyFactory kf = KeyFactory.getInstance("RSA");
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(pubKey64)));
		return pubKey;
	}

	public Code getCode() {
		System.out.println("[Warning]... Get_Code ....");
		System.out.println(String.format("[Warning] Client_ID: %s",System.getenv("CLIENT-ID")));
		requestDescription.put("endpoint", utils.generateEndpoint(String.format("%s%s", host, System.getenv("GET-CODE-ROUTE")),null));
		requestDescription.put("method", "POST");
		headers.put("client_id", System.getenv("CLIENT-ID"));
		requestDescription.put("headers", headers);

		Object response =  utils.doRequest(requestDescription, "Get_Code", Code.class);
		Code code;
		
		if (response instanceof Code && response != null) {
			code = (Code) response;
			System.out.println(String.format("[Success] Code: %s\n---------------------------------------------", code.getCode()));
			utils.clearMappers(requestDescription, headers);
			return code;
		}
		else {
			code = new Code();
			code.setError((ErrorHandler)response);
			return code;
		}
	}
	
	public CredentialsEncrypt getAccessTokenAndPublicKey(String code) {
		System.out.println("[Warning]... Get_Access_Token_And_PublicKey ...");
		System.out.println(String.format("[Warning] Client_ID: %s",System.getenv("CLIENT-ID")));
		System.out.println(String.format("[Warning] Code: %s",code));
		System.out.println("[Warning] Grant_Type: authorization_code");
		
		requestDescription.put("endpoint", utils.generateEndpoint(String.format("%s%s", host, System.getenv("GET-ACCESS-TOKEN-AND-PUBLIC-KEY-ROUTE")),null));
		requestDescription.put("method", "POST");
		headers.put("client_id", System.getenv("CLIENT-ID"));
		headers.put("code", code);
		headers.put("grant_type", "authorization_code");
		requestDescription.put("headers", headers);

		Object response = utils.doRequest(requestDescription, "Get_Access_Token_And_PublicKey", CredentialsEncrypt.class);
		CredentialsEncrypt credentials;
		
		if (response instanceof CredentialsEncrypt && response != null) {
			credentials = (CredentialsEncrypt) response;
			System.out.println(String.format("[Success] Access Token: %s",credentials.getAccessToken()));
			System.out.println(String.format("[Success] Public Key: %s\n---------------------------------------------", credentials.getPublicKey()));
			utils.clearMappers(requestDescription, headers);
			return credentials;
		}
		else {
			credentials = new CredentialsEncrypt();
			credentials.setError((ErrorHandler)response);
			return credentials;
		}
	}
	
	public Object doAuthentication() {
		try {
			Code code = getCode();
			
			if (code.getError() == null) {
				CredentialsEncrypt credentials = getAccessTokenAndPublicKey(code.getCode());
				
				if(credentials.getError() == null) {
					publicKey = credentials.getPublicKey();
					String credential = generateCredential();
					System.out.println(String.format("[Success] Credential: %s\n---------------------------------------------",credential));
					
					Map<String,String> response = new HashMap<>();
					response.put("Authorization", String.format("Bearer %s", credentials.getAccessToken()));
					response.put("credential", credential);
					return response;
				}
				else 
					return credentials.getError();
			}
			else 
				return code.getError();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
}
