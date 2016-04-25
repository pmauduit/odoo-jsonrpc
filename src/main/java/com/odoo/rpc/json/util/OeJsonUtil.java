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

package com.odoo.rpc.json.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.odoo.rpc.exception.OeRpcException;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Ahmed El-mawaziny on 4/7/16.
 */
public final class OeJsonUtil {

    private static final Logger LOG = Logger.getLogger(OeJsonUtil.class.getName());

    public static Map<String, Object> convertStringToMap(String jsonStr) {
        return convertJsonToMap(new JsonObject().getAsJsonObject(jsonStr));
    }

    public static Map<String, Object> convertJsonToMap(JsonObject jsonObject) {
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        Map<String, Object> convertedMap = new HashMap<String, Object>(entries.size());
        for (Map.Entry<String, JsonElement> entry : entries) {
            convertedMap.put(entry.getKey(), entry.getValue().toString());
        }
        return convertedMap;
    }

    public static <T> T[] convertJsonArray(JsonArray jsonArray, Class<T[]> tArr) {
        return new Gson().fromJson(jsonArray, tArr);
    }

    public static Map<String, Object>[] convertJsonArrayToMapArray(JsonArray jsonArray) {
        int length = jsonArray.size();
        @SuppressWarnings("unchecked")
        Map<String, Object>[] mapArr = new HashMap[length];
        for (int i = 0; i < length; i++) {
            mapArr[i] = convertJsonToMap(jsonArray.get(i).getAsJsonObject());
        }
        return mapArr;
    }

    public static JsonObject getCallWith(JsonObject params) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("jsonrpc", "2.0");
        jsonObject.addProperty("method", "call");
        jsonObject.add("params", params);
        return jsonObject;
    }

    public static JsonObject postRequest(String url, JsonObject json) throws OeRpcException {
        LOG.info("Hit: " + url);
        return HttpClient.postHttp(url, json);
    }

    public static JsonArray parseAsJsonArray(Object obj) {
        return parseAsJsonElement(obj).getAsJsonArray();
    }

    public static JsonObject parseAsJsonObject(Object obj) {
        return parseAsJsonElement(obj).getAsJsonObject();
    }

    public static JsonElement parseAsJsonElement(Object obj) {
        try {
            if (!(obj instanceof String)) {
                obj = new Gson().toJson(obj);
            }
            Reader reader = new StringReader(obj.toString());
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);
            return new JsonParser().parse(jsonReader);
        } catch (JsonParseException e) {
            throw new Error(obj.toString(), e);
        }
    }

    public static JsonObject margeJsonObject(JsonObject with, JsonObject into) {
        Set<Map.Entry<String, JsonElement>> entries = with.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            into.add(entry.getKey(), entry.getValue());
        }
        return into;
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
            if (response.get("error").isJsonObject()) {
                throw new OeRpcException(convertJsonToMap(response.getAsJsonObject("error")));
            } else {
                throw new OeRpcException(response.get("error").getAsString());
            }
        }
    }
}
