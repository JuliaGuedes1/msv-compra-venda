package com.ms.compra.venda.model;

public enum Status {

    APROVADA(0),
    RECUSADA(1),
    AGUARDANDO_PAGAMENTO(2),
    CANCELADA(3);

    public int valorStatus;

    Status(int valor) {
        valorStatus = valor;
    }


}
