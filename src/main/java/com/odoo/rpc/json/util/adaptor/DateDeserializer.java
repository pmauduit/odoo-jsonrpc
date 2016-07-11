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

package com.odoo.rpc.json.util.adaptor;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Gson deserializer implementation for Date
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json != null && !json.isJsonNull()) {
            if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isBoolean()) {
                return null;
            }

            try {
                String dateStr = json.getAsString();
                if (dateStr.length() == 10) {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                } else if (dateStr.length() == 19) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
                }
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
        return new Gson().fromJson(json, Date.class);
    }
}
