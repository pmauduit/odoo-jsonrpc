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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

    public OeRpcException(JsonObject err) {
        this(getCodeNumber(err), getMessageErr(err), getTypeError(err));
    }

    private static int getCodeNumber(JsonObject err) {
        if (err.has("code")) {
            JsonElement code = err.get("code");
            if (code.isJsonPrimitive() && code.getAsJsonPrimitive().isNumber()) {
                return code.getAsInt();
            }
        }
        return 0;
    }

    private static String getMessageErr(JsonObject err) {
        if (err.has("message")) {
            JsonElement message = err.get("message");
            if (message.isJsonPrimitive() && message.getAsJsonPrimitive().isString()) {
                return message.getAsString();
            } else {
                return message.toString();
            }
        }
        return null;
    }

    private static Throwable getTypeError(JsonObject err) {
        if (err.has("data")) {
            JsonElement data = err.get("data");
            if (!data.isJsonNull() && data.isJsonObject()) {
                JsonObject dataJson = data.getAsJsonObject();
                if (dataJson.has("debug")) {
                    JsonElement debug = dataJson.get("debug");
                    if (!debug.isJsonNull()) {
                        String traceBack = debug.getAsString().trim();
                        return new Throwable(traceBack.substring(traceBack.lastIndexOf('\n')));
                    }
                }
            }
        }
        return null;
    }

    public static void checkJsonResponse(JsonObject response) throws OeRpcException {
        if (response.has("result")) {
            if (response.get("result").isJsonObject()) {
                JsonObject result = response.getAsJsonObject("result");
                if (result.has("error")) {
                    throw new OeRpcException(result.get("error").getAsString());
                }
            }
        } else if (response.has("error")) {
            if (response.get("result").isJsonObject()) {
                throw new OeRpcException(response.getAsJsonObject("error"));
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
