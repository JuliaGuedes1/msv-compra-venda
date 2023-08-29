package com.ms.compra.venda.controller;

import com.ms.compra.venda.model.Compra;
import com.ms.compra.venda.model.Livros;
import com.ms.compra.venda.repository.ICompraRepository;
import com.ms.compra.venda.repository.ILivrosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Comprar {

    private final ICompraRepository iCompraRepository;

    private final ILivrosRepository iLivrosRepository;

    Logger logger = LoggerFactory.getLogger(Comprar.class);

    public Comprar(ICompraRepository iCompraRepository, ILivrosRepository iLivrosRepository) {
        this.iCompraRepository = iCompraRepository;
        this.iLivrosRepository = iLivrosRepository;
    }

    @PostMapping("comprar")
    public ResponseEntity comprar(@RequestParam Long idLivro){

        try {
            //id comprador vem do token


            Livros livro = iLivrosRepository.findById(idLivro).get();

            Compra compra = new Compra();
            compra.setIdLivro(idLivro);
            compra.setIdVendedor(livro.getIdVendedor());
            compra.setValor(livro.getValor());
            compra.setIdComprador(1L);

            iCompraRepository.save(compra);

        }catch (Exception e){
            logger.error("Erro ao cadastrar venda", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);

    }

}
