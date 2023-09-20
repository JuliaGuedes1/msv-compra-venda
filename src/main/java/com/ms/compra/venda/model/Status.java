package com.ms.compra.venda.model;

public enum Status {

    APROVADA(1),
    RECUSADA(2),
    AGUARDANDO_PAGAMENTO(3),
    CANCELADA(4);

    public int valorStatus;

    Status(int valor) {
        valorStatus = valor;
    }


}
