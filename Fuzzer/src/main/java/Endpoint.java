import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class Endpoint {
    private final Set<String> ALLOWED_TYPES = Set.of("boolean", "int", "String", "long");
    private final Set<String> REQUEST_TYPES = Set.of("GET", "POST", "PUT", "DELETE");

    private String url;
    private String type;
    private String path_var;
    private ArrayList<String[]> headers;
    private boolean authentication = false;
    private String username;
    private String password;

    public Endpoint(JSONObject data) {
        if (data.containsKey("url")) {
            this.url = data.get("url").toString();
            if (this.url.charAt(this.url.length()-1) != '/'){
                this.url += '/';
            }
        } else {
            throw new IllegalArgumentException("Must include URL to test endpoint ");
        }
        if (data.containsKey("type")) {
            this.setType(data.get("type").toString());
        } else {
            throw new IllegalArgumentException("Must include type to test endpoint ");
        }
        if (data.containsKey("path_var")) {
            this.setPathVar(data.get("path_var").toString());
        }
        if (data.containsKey("authentication")) {
            this.authentication = true;
            JSONObject authentication = (JSONObject) data.get("authentication");
            if (authentication.containsKey("username")) {
                this.username = authentication.get("username").toString();
            }
            if (authentication.containsKey("password")) {
                this.password = authentication.get("password").toString();
            }
        }
        this.headers = new ArrayList<String[]>();
        if (data.containsKey("headers")) {
            JSONArray headers = (JSONArray) data.get("headers");
            for (int i=0; i < headers.size(); i++) {
                addHeader((JSONObject) headers.get(i));
            }
        }
    }

    private void setType(String type) {
        if (this.REQUEST_TYPES.contains(type)) {
            this.type = type;
        }
        else {
            throw new IllegalArgumentException("Type must have value \"GET\", \"PUT\", or \"POST\"");
        }
    }

    private void addHeader(JSONObject header) {
        headers.add(new String[]{header.get("name").toString(), header.get("type").toString()});
    }

    private void setPathVar(String path_var) {
        if (this.ALLOWED_TYPES.contains(path_var)) {
            this.path_var = path_var;
        }
        else {
            throw new IllegalArgumentException("Path_var must have value \"String\", \"int\", or \"boolean\"");
        }
    }

    public String getUrl() {
        return this.url;
    }

    public String getPathVar() {
        return this.path_var;
    }

    public String getType() {
        return this.type;
    }

    public boolean getAuthentication() {
        return this.authentication;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<String[]> getHeaders() {
        return this.headers;
    }
}
