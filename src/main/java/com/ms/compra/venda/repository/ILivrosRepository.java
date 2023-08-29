package com.ms.compra.venda.repository;

import com.ms.compra.venda.model.Livros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILivrosRepository extends JpaRepository<Livros, Long> {
}
