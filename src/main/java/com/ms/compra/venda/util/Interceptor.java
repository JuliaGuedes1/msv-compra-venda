package com.ms.compra.venda.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Interceptor {

    Logger logger = LoggerFactory.getLogger(Interceptor.class);


    public boolean validate(String token, String requirement){

        String url = "http://localhost:8080/person/validate";

        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("token", token);

        String jsonToken = convertHashMapToJson(mapUser);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity((jsonToken)));

            logger.info("Acessando end point validade do microservico usuario");
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            logger.info("Resposta do microservico usuario: " + responseString);

            Map<String, String> responseRole = validateRole(responseString);
            if (!responseRole.containsValue(requirement)){
                return false;
            }

            return true;
        }catch (Exception e){
            logger.error("Erro ao chamar o microservico", e);
        }

        return false;
    }


    private String convertHashMapToJson(Map<String, String> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> validateRole(String token){

        JsonObject tokenJson = JsonParser.parseString(token).getAsJsonObject();

        String response = tokenJson.get("role").getAsString();
        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("role", response);

        return mapUser;

    }


}
