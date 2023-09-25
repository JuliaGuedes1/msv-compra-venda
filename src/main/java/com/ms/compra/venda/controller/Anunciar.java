package com.ms.compra.venda.controller;

import com.ms.compra.venda.model.Anuncio;
import com.ms.compra.venda.repository.IAnuncioRepository;
import com.ms.compra.venda.util.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Anunciar {

    private Interceptor interceptor;

    private IAnuncioRepository iAnuncioRepository;

    Logger logger = LoggerFactory.getLogger(Anunciar.class);

    public Anunciar(IAnuncioRepository iAnuncioRepository) {
        this.iAnuncioRepository = iAnuncioRepository;
        this.interceptor = new Interceptor();
    }

    @PostMapping("cadastrar-anuncio")
    public ResponseEntity cadastrar(@RequestHeader("Authorization") String token, @RequestBody Anuncio anuncio){

        try {
            if(!interceptor.validate(token, "vendedor")){
                logger.info("Usuario nao eh um vendedor, anuncio nao pode ser cadastrado");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            //JsonObject anuncioJson = JsonParser.parseString(anuncio.toString()).getAsJsonObject();

            return new ResponseEntity(iAnuncioRepository.save(anuncio), HttpStatus.OK);

        }catch (Exception e){
            logger.error("Erro ao cadastrar anuncio", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

}
