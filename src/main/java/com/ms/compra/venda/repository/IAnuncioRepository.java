package com.ms.compra.venda.repository;

import com.ms.compra.venda.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnuncioRepository extends JpaRepository<Anuncio, Long> {

}
