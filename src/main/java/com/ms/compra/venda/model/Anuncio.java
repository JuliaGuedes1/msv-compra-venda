package com.ms.compra.venda.model;

import jakarta.persistence.*;

@Entity
@Table(name = "anuncio")
public class Anuncio {

    //sera implementada associacao

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCompra;

    private Long idLivro;

    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
