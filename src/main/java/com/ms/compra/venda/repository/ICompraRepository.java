package com.ms.compra.venda.repository;

import com.ms.compra.venda.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompraRepository extends JpaRepository<Compra, Long> {

}
