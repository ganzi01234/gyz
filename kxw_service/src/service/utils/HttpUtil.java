package service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
	public static Map<String, Object> getResult(boolean success, Object data, int count, String errorMessage) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        if (data != null) {
            result.put("data", data);
        }
        result.put("count", count);
        if (errorMessage != null) {
            result.put("msg", errorMessage);
        }
        return result;
    }
	
}
