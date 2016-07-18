/*
 * Copyright 2016 QFast
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.odoo.rpc.exception;

import com.google.gson.JsonObject;
import com.odoo.rpc.json.util.OeJUtil;

import java.util.Map;

/**
 * Exception class to handel Odoo error
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class OeRpcException extends Exception {

    private static final long serialVersionUID = 3646608250089159259L;
    private final int code;
    private final Throwable cause;

    public OeRpcException(String message) {
        this(500, message, null);
    }

    public OeRpcException(String message, Throwable cause) {
        this(500, message, cause);
    }

    public OeRpcException(Throwable cause) {
        this(500, cause.getMessage(), cause);
    }

    public OeRpcException(int code, String message, Throwable cause) {
        super(message);
        this.code = code;
        this.cause = cause;
    }

    public OeRpcException(Map<String, Object> err) {
        this(getCodeNumber(err), getMessageErr(err), getTypeError(err));
    }

    /**
     * static method to get error code number
     *
     * @param err map with error
     * @return code number
     */
    private static int getCodeNumber(Map<String, Object> err) {
        if (err.containsKey("code")) {
            Object code = err.get("code");
            if (code instanceof Number) {
                return (Integer) code;
            }
        }
        return 0;
    }

    /**
     * static method to get error massage
     *
     * @param err map with error
     * @return error message
     */
    private static String getMessageErr(Map<String, Object> err) {
        if (err.containsKey("message")) {
            return String.valueOf(err.get("message"));
        }
        return null;
    }

    /**
     * convert Odoo error response to java {@link Throwable()}
     *
     * @param err map with error
     * @return throwable with error data
     */
    private static Throwable getTypeError(Map<String, Object> err) {
        if (err.containsKey("data")) {
            Object data = err.get("data");
            if (data != null) {
                Map dataJson = OeJUtil.convertJsonToMap(OeJUtil.parseAsJsonObject(data));
                if (dataJson.containsKey("debug")) {
                    Object debug = dataJson.get("debug");
                    if (debug != null) {
                        String traceBack = debug.toString().trim();
                        return new Throwable(traceBack);
                    }
                }
            }
        }
        return null;
    }

    /**
     * static method to check json response if it has error key or not, if yes convert this error to java exception
     *
     * @param response Json Object with Odoo response
     * @throws OeRpcException if the response has error thrown
     */
    public static void checkJsonResponse(JsonObject response) throws OeRpcException {
        if (response.has("result")) {
            if (response.get("result").isJsonObject()) {
                JsonObject result = response.getAsJsonObject("result");
                if (result.has("error")) {
                    throw new OeRpcException(result.get("error").getAsString());
                }
            }
        } else if (response.has("error")) {
            if (response.get("error").isJsonObject()) {
                throw new OeRpcException(OeJUtil.convertJsonToMap(response.getAsJsonObject("error")));
            } else {
                throw new OeRpcException(response.get("error").getAsString());
            }
        }
    }

    public int getCode() {
        return code;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
