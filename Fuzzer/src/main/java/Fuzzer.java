import org.apache.commons.lang.RandomStringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class Fuzzer {
    private static ArrayList<Integer> ints;
    private static ArrayList<String> strings ;

    public static void testEndpoint(Endpoint endpoint) {
        try {
            String urlString = endpoint.getUrl();
            if (ints == null) {
                ints = generateIntegers();
                strings = generateStrings();
            }
            if (endpoint.getPathVar() != null) {
                for (int i = 0; i < ints.size(); i++) {
                    sendRequest(new URL(urlString + ints.get(i)), endpoint);
                }
            }
            else {
                for(int i = 0; i < 10; i++) {
                    sendRequest(new URL(urlString), endpoint);
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendRequest(URL url, Endpoint endpoint) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(endpoint.getType());
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
            if (endpoint.getAuthentication()) {
                String auth = endpoint.getUsername() + ":" + endpoint.getPassword();

                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

                String authHeaderValue = "Basic " + new String(encodedAuth);

                con.setRequestProperty("Authorization", authHeaderValue);
            }
            ArrayList<String[]> headers = endpoint.getHeaders();

            //set each header to random int and then check response code
            for(int i = 0; i < headers.size(); i++) {
                String[] header = headers.get(i);
                con.setRequestProperty(header[0], String.valueOf(ints.get((int) (Math.random() * ints.size()))));
            }
            System.out.println(con.getResponseCode());

            //set each header to random string and then check response code
            for(int i = 0; i < headers.size(); i++) {
                String[] header = headers.get(i);
                con.setRequestProperty(header[0], strings.get((int) (Math.random() * strings.size())));
            }

            System.out.println(con.getResponseCode());
        }
        catch (IOException e) {

        }
    }

    private static ArrayList<Integer> generateIntegers() {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        integers.add(0);
        integers.add(1);
        integers.add(-1);
        integers.add(Integer.MIN_VALUE);
        integers.add(Integer.MAX_VALUE);
        for (int i = 0; i <= 10; i++) {
            double random = Math.random() * (double) Integer.MAX_VALUE;
            integers.add((int) random);
            integers.add(-1 * (int) random);
        }
        return integers;
    }

    private static ArrayList<String> generateStrings() {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("");
        strings.add("0");
        strings.add("-1");

        // adapted from https://www.baeldung.com/java-random-string
        int length = 0;
        while (length < 100) {
            strings.add(RandomStringUtils.randomAlphabetic(length));

            strings.add(RandomStringUtils.randomAlphanumeric(length));

            byte[] array = new byte[length];
            new Random().nextBytes(array);
            strings.add(new String(array, Charset.forName("UTF-8")));
            if (length < 20) {
                length++;
            }
            else {
                length += 10;
            }
        }

        return strings;
    }
}
