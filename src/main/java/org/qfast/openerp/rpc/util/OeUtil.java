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
package org.qfast.openerp.rpc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.qfast.openerp.rpc.exception.OeRpcException;

/**
 * @author Ahmed El-mawaziny
 */
public final class OeUtil {

    public static Date getDate(Object o) throws ParseException {
        if (o != null) {
            String s = o.toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (s.length() == 10) {
                return sdf.parse(s);
            } else {
                sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                return sdf.parse(s);
            }
        }
        return null;
    }

    public static boolean isNULL(Object text) {
        if (text != null) {
            String strTest = text.toString().trim();
            if (!strTest.isEmpty() && !strTest.equalsIgnoreCase("null")) {
                return false;
            }
        }
        return true;
    }

    public static void writeBase64(byte[] btDataFile, String filePath)
            throws FileNotFoundException, IOException {

        File of = new File(filePath);
        FileOutputStream osf = new FileOutputStream(of);
        osf.write(btDataFile);
        osf.flush();
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

    public static Object[][] convertTuplesStringToArray(String tuplesString) {
        Pattern p = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = p.matcher(tuplesString);
        List<Object[]> list1 = new ArrayList<Object[]>(3);
        while (matcher.find()) {
            String tuple = matcher.group(1);
            Pattern p2 = Pattern.compile("'(.*?)'");
            Matcher m2 = p2.matcher(tuple);
            List<Object> list2 = new ArrayList<Object>(3);
            while (m2.find()) {
                list2.add(m2.group(1));
            }
            list1.add(list2.toArray(new Object[]{}));
        }

        return list1.toArray(new Object[][]{});
    }

    public static Map<String, Object> convertStringToMap(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return convertJsonToMap(jsonObject);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertJsonToMap(JSONObject jSONObject) {
        Set<String> keySet = jSONObject.keySet();
        Map<String, Object> convetedMap = new HashMap<String, Object>(keySet.size());
        for (String key : keySet) {
            convetedMap.put(key, jSONObject.get(key).toString());
        }
        return convetedMap;
    }

    public static <T> T[]
            convertJsonArray(JsonArray jsonArray, Class<T[]> tArr) {
        JSONArray jSONArray = new JSONArray(jsonArray.toString());
        Object[] arr = new Object[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            arr[i] = jSONArray.get(i);
        }
        return Arrays.copyOf(arr, arr.length, tArr);
    }

    public static JsonObject
            convertObjectListToJson(List<Object> searchCriteria) {
        JSONObject jSONObject = new JSONObject();
        for (Object criteria : searchCriteria) {
            Object[] sc = (Object[]) criteria;
            jSONObject.put(sc[0].toString(), sc[1]);
        }
        return convertJSONObjectToJsonObject(jSONObject);
    }

    public static JsonArray
            convertObjectListToJsonArr(List<Object> searchCriteria) {
        JSONArray jSONArray = new JSONArray();
        for (Object criteria : searchCriteria) {
            Object[] sc = (Object[]) criteria;
            jSONArray.put(sc);
        }
        return convertJSONArrayToJsonArr(jSONArray);
    }

    public static JsonObject
            convertJSONObjectToJsonObject(JSONObject jSONObject) {
        JsonReader reader = null;
        try {
            reader
                    = Json.createReader(new StringReader(jSONObject.toString()));
            return reader.readObject();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static JsonArray convertJSONArrayToJsonArr(JSONArray jSONArray) {
        JsonReader reader = null;
        try {
            reader
                    = Json.createReader(new StringReader(jSONArray.toString()));
            return reader.readArray();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object>[] convertJsonArrayToMapArray(JSONArray jSONArray) {
        int length = jSONArray.length();
        Map<String, Object>[] mapArr = new HashMap[length];
        for (int i = 0; i < length; i++) {
            mapArr[i] = convertJsonToMap(jSONArray.getJSONObject(i));
        }
        return mapArr;
    }

    public static Map<String, Object>[] convertJsonArrayToMapArray(JsonArray jsonArray) {
        return convertJsonArrayToMapArray(new JSONArray(jsonArray.toString()));
    }

    public static JsonObject getCallWith(JsonObject params) {
        JsonObject json = Json.createObjectBuilder()
                .add("jsonrpc", "2.0")
                .add("method", "call")
                .add("params", params)
                .build();
        return json;
    }

    public static JsonObject getCallWith(JSONObject params) {
        return getCallWith(convertJSONObjectToJsonObject(params));
    }

    public static JsonObject postRequest(Client client, String url,
            JsonObject json) throws OeRpcException {
        try {
            return client
                    .target(url)
                    .request()
                    .post(Entity.json(json), JsonObject.class);
        } catch (Exception e) {
            throw new OeRpcException(e);
        }
    }

    public static JsonArray postRequestArr(Client client, String url,
            JsonObject json) throws OeRpcException {
        try {
            return client
                    .target(url)
                    .request()
                    .post(Entity.json(json), JsonArray.class);
        } catch (Exception e) {
            throw new OeRpcException(e);
        }
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }
}
