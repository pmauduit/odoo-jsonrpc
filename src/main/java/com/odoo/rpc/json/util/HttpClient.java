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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odoo.rpc.json.util;

import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.odoo.rpc.json.util.OeJUtil.parseAsJsonObject;

/**
 * Utility class for Http connection
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class HttpClient {

    private static final Logger LOG = Logger.getLogger(HttpClient.class.getName());

    /**
     * static method to wrap the request data with some properties
     *
     * @param url  URL to post
     * @return json object wrap the request data
     * @see #postWithParams(String, JsonObject)
     */
    public static JsonObject postWithParams(String url) {
        return postWithParams(url, new JsonObject());
    }

    /**
     * static method to wrap the request data with some properties
     *
     * @param url  URL to post
     * @param data request json object
     * @return json object wrap the request data
     */
    public static JsonObject postWithParams(String url, JsonObject data) {
        JsonObject wrapper = new JsonObject();
        wrapper.addProperty("jsonrpc", "2.0");
        wrapper.addProperty("method", "call");
        wrapper.add("params", data);
        return post(url, wrapper);
    }

    /**
     * static method for http post
     *
     * @param url  url to post
     * @param data json data to post
     * @return response as json object
     */
    public static JsonObject post(String url, JsonObject data) {
        LOG.info("Hit: " + url);
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            try {
                HttpPost httpPostRequest = new HttpPost(url);

                httpPostRequest.setHeader("Accept", "application/json");
                httpPostRequest.setHeader("Content-type", "application/json");

                StringEntity se = new StringEntity(data.toString());
                httpPostRequest.setEntity(se);

                HttpResponse response = httpclient.execute(httpPostRequest);

                HttpEntity entity = response.getEntity();
                if (entity != null) {

                    InputStream in = entity.getContent();
                    String resultString = convertStreamToString(in);

                    return parseAsJsonObject(resultString);
                }
            } finally {
                if (httpclient != null)
                    httpclient.close();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
        } catch (IllegalStateException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * static method to convert the response stream to string
     *
     * @param in input stream
     * @return string converted from input stream
     * @throws IOException if anything happened during input/output for conversion
     */
    private static String convertStreamToString(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        if (in != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
            } finally {
                in.close();
                reader.close();
            }
        }
        return sb.toString();
    }
}
