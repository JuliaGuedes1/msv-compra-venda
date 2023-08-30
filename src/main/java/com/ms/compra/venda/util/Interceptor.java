package com.ms.compra.venda.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class Interceptor {

    Logger logger = LoggerFactory.getLogger(Interceptor.class);

    private Client client = ClientBuilder.newClient();

    public boolean validate(String token, String requirement){

        String url = "http://localhost:8080/person/validate";

        //Client client = ClientBuilder.newClient();
        WebTarget webTarget = this.client.target(url);

        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("token", token);

        try {
            Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(mapUser, MediaType.APPLICATION_JSON_TYPE));
            int status = response.getStatus();
            System.out.println(status);
            return true;
        }catch (Exception e){
            logger.error("Erro ao chamar o microservico");
        }

        logger.info("Acessando end point validade do microservico usuario");
        return true;
    }


}
