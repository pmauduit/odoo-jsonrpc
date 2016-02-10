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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.qfast.openerp.rpc.exception.OeRpcException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ahmed El-mawaziny
 */
public final class OeUtil {

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
            list1.add(list2.toArray(new Object[list2.size()]));
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
        Map<String, Object> convertedMap = new HashMap<String, Object>(keySet.size());
        for (String key : keySet) {
            convertedMap.put(key, jSONObject.get(key).toString());
        }
        return convertedMap;
    }

    public static <T> T[] convertJsonArray(JSONArray jsonArray, Class<T[]> tArr) {
        JSONArray jSONArray = new JSONArray(jsonArray.toString());
        Object[] arr = new Object[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            arr[i] = jSONArray.get(i);
        }
        return Arrays.copyOf(arr, arr.length, tArr);
    }

    public static JSONObject convertObjectListToJson(List<Object> searchCriteria) {
        JSONObject jSONObject = new JSONObject();
        for (Object criteria : searchCriteria) {
            Object[] sc = (Object[]) criteria;
            jSONObject.put(sc[0].toString(), sc[1]);
        }
        return jSONObject;
    }

    public static JSONArray convertObjectListToJsonArr(List<Object> searchCriteria) {
        JSONArray jSONArray = new JSONArray();
        for (Object criteria : searchCriteria) {
            Object[] sc = (Object[]) criteria;
            jSONArray.put(sc);
        }
        return jSONArray;
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

    public static JSONObject getCallWith(JSONObject params) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("jsonrpc", "2.0");
        jSONObject.put("method", "call");
        jSONObject.put("params", params);
        return jSONObject;
    }

    public static JSONObject postRequest(String url, JSONObject json) throws OeRpcException {
        return HttpClient.SendHttpPost(url, json);
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }
}
