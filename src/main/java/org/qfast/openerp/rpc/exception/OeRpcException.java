/*
 * Copyright 2014 QFast Ahmed El-mawaziny.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qfast.openerp.rpc.exception;

import javax.json.JsonObject;
import javax.json.JsonValue.ValueType;

/**
 * @author Ahmed El-mawaziny
 */
public class OeRpcException extends Exception {

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
        this(err.getInt("code"), err.getString("message"), getTypeError(err));
    }

    private static Throwable getTypeError(JsonObject err) {
        if (!err.isNull("data") && !err.getJsonObject("data").isNull("debug")) {
            String traceback = err.getJsonObject("data").getString("debug").trim();
            return new Throwable(traceback.substring(traceback.lastIndexOf('\n')));
        } else {
            return null;
        }
    }

    public int getCode() {
        return code;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public static void checkJsonResponse(JsonObject response) throws OeRpcException {
        if (response.get("result") != null 
                && response.get("result").getValueType() == ValueType.OBJECT) {
            JsonObject result = response.getJsonObject("result");
            if (result.containsKey("error")) {
                throw new OeRpcException(result.getString("error"));
            }
        } else if (response.containsKey("error")) {
            throw new OeRpcException(response.getJsonObject("error"));
        }
    }
    private static final long serialVersionUID = 3646608250089159259L;
}
