/*
 * Copyright 2016 QFast Ahmed El-mawaziny
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
package org.qfast.openerp.rpc.exception;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Ahmed El-mawaziny
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

    public OeRpcException(JSONObject err) {
        this(err.getInt("code"), err.getString("message"), getTypeError(err));
    }

    private static Throwable getTypeError(JSONObject err) {
        if (!err.isNull("data") && !err.getJSONObject("data").isNull("debug")) {
            String traceBack = err.getJSONObject("data").getString("debug").trim();
            return new Throwable(traceBack.substring(traceBack.lastIndexOf('\n')));
        } else {
            return null;
        }
    }

    public static void checkJsonResponse(JSONObject response) throws OeRpcException, JSONException {
        if (response.get("result") != null) {
            JSONObject result = response.getJSONObject("result");
            if (result.has("error")) {
                throw new OeRpcException(result.getString("error"));
            }
        } else if (response.has("error")) {
            throw new OeRpcException(response.getJSONObject("error"));
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
