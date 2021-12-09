
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            File jsonFile = new File(Main.class.getResource("/data.json").getFile());

            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(jsonFile));

            JSONArray jsonEndpoints = (JSONArray) jsonObject.get("endpoints");

            for (int i=0; i < jsonEndpoints.size(); i++) {
                Fuzzer.testEndpoint(new Endpoint((JSONObject) jsonEndpoints.get(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testEndpoint(JSONObject endpoint) {

    }
}
