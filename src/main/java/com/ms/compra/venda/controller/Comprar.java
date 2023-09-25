package com.ms.compra.venda.controller;

import com.ms.compra.venda.model.Compra;
import com.ms.compra.venda.model.Livros;
import com.ms.compra.venda.model.Status;
import com.ms.compra.venda.repository.ICompraRepository;
import com.ms.compra.venda.repository.ILivrosRepository;
import com.ms.compra.venda.util.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Comprar {

    private final ICompraRepository iCompraRepository;

    private final ILivrosRepository iLivrosRepository;

    private Interceptor interceptor;

    Logger logger = LoggerFactory.getLogger(Comprar.class);

    public Comprar(ICompraRepository iCompraRepository, ILivrosRepository iLivrosRepository) {
        this.iCompraRepository = iCompraRepository;
        this.iLivrosRepository = iLivrosRepository;
        this.interceptor = new Interceptor();
    }

    @PostMapping("comprar")
    public ResponseEntity comprar(@RequestParam Long idLivro, @RequestHeader("Authorization") String token){

        try {
            //id comprador vem do token
            if(!interceptor.validate(token, "comprador")){
                logger.info("Usuario nao eh um comprador, compra nao pode ser realizada");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            Livros livro = iLivrosRepository.findById(idLivro).get();

            Compra compra = new Compra();
            compra.setIdLivro(idLivro);
            compra.setIdVendedor(livro.getIdVendedor());
            compra.setValor(livro.getValor());
            compra.setIdComprador(1L);
            compra.setStatus(Status.AGUARDANDO_PAGAMENTO);

            iCompraRepository.save(compra);

        }catch (Exception e){
            logger.error("Erro ao cadastrar venda", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);

    }

    @PutMapping("atualizar-compra")
    public ResponseEntity atualizarCompra(@RequestBody Compra compra, @RequestHeader("Authorization") String token) {


        try {

            if (!interceptor.validate(token, "comprador")) {
                logger.info("Usuario nao eh um comprador, compra nao pode ser realizada");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            Compra compraAtualizada = iCompraRepository.findById(compra.getId()).get();
            compraAtualizada.setValor(compra.getValor());
            compraAtualizada.setIdLivro(compra.getIdLivro());
            compraAtualizada.setStatus(Status.APROVADA);

            logger.info("Compra atualizada com sucesso");

            return new ResponseEntity(iCompraRepository.save(compraAtualizada), HttpStatus.OK);


        } catch (Exception e) {
            logger.error("Erro ao atualizar a compra", e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("cancelar-compra/{id}")
    public ResponseEntity cancelarCompra(@PathVariable Long id, @RequestHeader("Authorization") String token){

        try {
            if(!interceptor.validate(token, "comprador")){
                logger.info("Usuario nao eh um comprador, compra nao pode ser realizada");
                return  new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            Compra compra = iCompraRepository.findById(id).get();
            compra.setStatus(Status.CANCELADA);
            logger.info("Compra cancelada");

            return new ResponseEntity(iCompraRepository.save(compra), HttpStatus.OK);

        }catch (Exception e){
            logger.error("Nao foi possivel cancelar a compra");
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
