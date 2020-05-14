package es.raulsanmartin.postit.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CaptchaValidationService {

    @Value("${google.recaptcha.api.endpoint}") 
    private String GOOGLE_RECAPTCHA_API_ENDPOINT;

    @Value("${google.recaptcha.key.secret}") 
    private String GOOGLE_RECAPTCHA_KEY_SECRET;

    public boolean validateCaptcha(String clientRecaptchaResponse) throws IOException, ParseException {
        if (clientRecaptchaResponse == null || "".equals(clientRecaptchaResponse)) {
			return false;
        }
        
        URL reCaptchaEndpoint = new URL(GOOGLE_RECAPTCHA_API_ENDPOINT);
        HttpsURLConnection connection = (HttpsURLConnection) reCaptchaEndpoint.openConnection();

        connection.setRequestMethod("POST");

        String reqParam =
				"secret=" + GOOGLE_RECAPTCHA_KEY_SECRET +
                "&response=" + clientRecaptchaResponse;
                
        // Send post request to google recaptcha server
		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(reqParam);
		wr.flush();
        wr.close();
        
        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            return false;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
		StringBuffer recaptchaResponse = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			recaptchaResponse.append(inputLine);
		}
        in.close();
        
        JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(recaptchaResponse.toString());

        Boolean success = (Boolean) json.get("success");
        Double score = (Double) json.get("score");

        return (success && (score >= 0.5));
    }
    
}