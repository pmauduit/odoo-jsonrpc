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

package org.qfast.openerp.rpc.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.qfast.openerp.rpc.exception.OeRpcException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ahmed El-mawaziny
 */
public final class OeUtil {

    private static final Logger LOG = Logger.getLogger(OeUtil.class.getName());

    public static boolean isNULL(Object text) {
        if (text != null) {
            String strTest = text.toString().trim();
            if (!strTest.isEmpty() && !strTest.equalsIgnoreCase("null")) {
                return false;
            }
        }
        return true;
    }

    public static Locale getLocale(String locale) {
        if (!isNULL(locale)) {
            String[] split;
            if (locale.contains("_")) {
                split = locale.split("_");
            } else if (locale.contains("@")) {
                split = locale.split("@");
            } else {
                split = locale.split(locale.charAt(2) + "");
            }

            return new Locale(split[0], split[1]);
        }
        return null;
    }

    public static Object[][] convertTupleStringToArray(String tupleString) {
        Pattern p = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = p.matcher(tupleString);
        List<Object[]> list1 = new ArrayList<Object[]>(3);
        while (matcher.find()) {
            String tuple = matcher.group(1);
            Pattern p2 = Pattern.compile("'(.*?)'");
            Matcher m2 = p2.matcher(tuple);
            List<Object> list2 = new ArrayList<Object>(3);
            while (m2.find()) {
                list2.add(m2.group(1));
            }
            list1.add(list2.toArray(new Object[list2.size()]));
        }

        return list1.toArray(new Object[][]{});
    }

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

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
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
            return new JsonParser().parse(obj.toString());
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

    public static String clearMap(String map) {
        map = map.trim().substring(1, map.length() - 1);
        String[] strings = map.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String coma = "";
        for (String s : strings) {
            if (!isNULL(s)) {
                sb.append(coma).append(s.trim().replace("u'", "'"));
                coma = ",";
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
