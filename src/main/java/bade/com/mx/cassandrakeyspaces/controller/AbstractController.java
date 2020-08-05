package bade.com.mx.cassandrakeyspaces.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link AbstractController}
 */
public abstract class AbstractController {

    public Map<String, Object> ok(Object data) {
        Map<String, Object> toHttpResponse = new HashMap<>();
        toHttpResponse.put("code", 200);
        toHttpResponse.put("data", data);

        return toHttpResponse;
    }

}
