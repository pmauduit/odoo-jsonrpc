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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.odoo.rpc.exception.OeRpcException;

/**
 * @author Ahmed El-mawaziny on 7/11/16.
 */
public class OeJsonObject {

    private final JsonObject jsonObject;

    public OeJsonObject(JsonObject jsonObject) throws OeRpcException {
        OeRpcException.checkJsonResponse(jsonObject);
        this.jsonObject = jsonObject;
    }

    public JsonElement get(String key) {
        return jsonObject.get(key);
    }

    public JsonObject getAsJsonObject(String key) {
        return jsonObject.getAsJsonObject(key);
    }

    public JsonArray getAsJsonArray(String key) {
        return jsonObject.getAsJsonArray(key);
    }
}
