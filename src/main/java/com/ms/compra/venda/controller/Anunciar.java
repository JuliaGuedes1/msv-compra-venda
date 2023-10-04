package com.ms.compra.venda.controller;

import com.ms.compra.venda.model.Anuncio;
import com.ms.compra.venda.model.Livros;
import com.ms.compra.venda.repository.IAnuncioRepository;
import com.ms.compra.venda.repository.ILivrosRepository;
import com.ms.compra.venda.util.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Anunciar {

    private Interceptor interceptor;

    private IAnuncioRepository iAnuncioRepository;

    private ILivrosRepository iLivrosRepository;

    Logger logger = LoggerFactory.getLogger(Anunciar.class);

    public Anunciar(IAnuncioRepository iAnuncioRepository, ILivrosRepository iLivrosRepository) {
        this.iAnuncioRepository = iAnuncioRepository;
        this.iLivrosRepository = iLivrosRepository;
        this.interceptor = new Interceptor();
    }

    @PostMapping("cadastrar-anuncio")
    public ResponseEntity cadastrar(@RequestHeader("Authorization") String token, @RequestBody Anuncio anuncio){

        try {
            if(!interceptor.validate(token, "vendedor")){
                logger.info("Usuario nao eh um vendedor, anuncio nao pode ser cadastrado");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity(iAnuncioRepository.save(anuncio), HttpStatus.OK);

        }catch (Exception e){
            logger.error("Erro ao cadastrar anuncio", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("deletar-anuncio/{id}")
    public ResponseEntity deletar(@PathVariable Long id, @RequestHeader("Authorization") String token){

        try {
            if(!interceptor.validate(token, "vendedor")){
                logger.info("Usuario nao eh um vendedor, anuncio nao pode deletar anuncios");
                return new ResponseEntity((HttpStatus.BAD_REQUEST));
            }

            Anuncio anuncio = iAnuncioRepository.findById(id).get();

            Long idLivro = anuncio.getIdLivro();
            Livros livro = iLivrosRepository.findById(idLivro).get();
            Long idVendendor = livro.getIdVendedor();

            if(!interceptor.validateId(token, idVendendor)){
                logger.info("Anuncio nao pertencente ao vendedor logado, nao foi possivel deletar o anuncio");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            iAnuncioRepository.findById(id).get();
            iAnuncioRepository.deleteById(id);

            logger.info("Anuncio deletado com sucesso");

            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e){
            logger.error("Erro ao deletar anuncio");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

}
