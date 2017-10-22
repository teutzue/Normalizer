import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.XML;

import javax.json.Json;
import javax.json.JsonObject;

public class NormalizerService {

    //here we get messages from the 4 banks
    //they will be in two formats

    //JSON: {"interestRate":5.5,"ssn":1605789787}
    //XML: <LoanResponse> <interestRate>4.5600000000000005</interestRate> <ssn>12345678</ssn></LoanResponse>


    public String normalizeMessage(String message) {
        //parse from XML to JSON

        JSONObject xmlJSONObj = XML.toJSONObject(message);
        String jsonMessage = xmlJSONObj.toString();
        if (jsonMessage.equals("{}")) {
            System.out.println("was jos: " + message);
            return message;
        }
        System.out.println("was xml" + jsonMessage);
        //this message is an xml, normalize it to JSON and remove the loan req wrapper
        JSONObject jsonedXML = new JSONObject(jsonMessage);

        //strip it from the dash and make it an integer
        JSONObject withoutWrapper = jsonedXML.getJSONObject("LoanResponse");
        int ssn = withoutWrapper.getInt("ssn");
        System.out.println("SSN stripped: " + ssn );

        Double interestRate = withoutWrapper.getDouble("interestRate");
        String bank = withoutWrapper.getString("bank");

        //add LoanRequest rapper so you have the correct format
        JsonObject messageTo = Json.createObjectBuilder()
                .add("ssn", ssn)
                .add("interestRate", interestRate)
                .add("bank", bank)
                .build();

        System.out.println("XML converted to JSON: " + messageTo.toString());

        return messageTo.toString();
    }

}
