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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qfast.openerp.rpc.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ahmed El-mawaziny
 */
public final class HttpClient {

    private static final Logger LOG = Logger.getLogger(HttpClient.class.getName());

    public static JSONObject SendHttpPost(String URL, JSONObject jsonObjSend) {
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            try {
                HttpPost httpPostRequest = new HttpPost(URL);
                StringEntity se = new StringEntity(jsonObjSend.toString());
                httpPostRequest.setEntity(se);
                httpPostRequest.setHeader("Accept", "application/json");
                httpPostRequest.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(httpPostRequest);

                HttpEntity entity = response.getEntity();
                if (entity != null) {

                    InputStream in = entity.getContent();
                    String resultString = convertStreamToString(in);

                    return new JSONObject(resultString);
                }
            } finally {
                if (httpclient != null)
                    httpclient.close();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
        } catch (IllegalStateException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
        } catch (JSONException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return null;
    }

    private static String convertStreamToString(InputStream in) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
        StringBuilder sb = new StringBuilder();
        try {
            while (reader.ready()) {
                sb.append(reader.readLine()).append("\n");
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
        } finally {
            try {
                if (in != null)
                    in.close();
                if (reader != null)
                    reader.close();
            } catch (IOException ignored) {
            }
        }
        return sb.toString();
    }
}
