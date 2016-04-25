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

import com.odoo.rpc.json.util.OeJsonUtil;

import java.util.Map;

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

    public OeRpcException(Map<String, Object> err) {
        this(getCodeNumber(err), getMessageErr(err), getTypeError(err));
    }

    private static int getCodeNumber(Map<String, Object> err) {
        if (err.containsKey("code")) {
            Object code = err.get("code");
            if (code instanceof Number) {
                return (Integer) code;
            }
        }
        return 0;
    }

    private static String getMessageErr(Map<String, Object> err) {
        if (err.containsKey("message")) {
            return String.valueOf(err.get("message"));
        }
        return null;
    }

    private static Throwable getTypeError(Map<String, Object> err) {
        if (err.containsKey("data")) {
            Object data = err.get("data");
            if (data != null) {
                Map dataJson = OeJsonUtil.convertJsonToMap(OeJsonUtil.parseAsJsonObject(data));
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

    public int getCode() {
        return code;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
