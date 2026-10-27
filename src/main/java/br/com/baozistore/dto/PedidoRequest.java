package br.com.baozistore.dto;

/**
 * Corpo JSON recebido no POST /pedidos.
 * Exemplo: { "clienteId": 1, "produtoId": 1, "quantidade": 6 }
 */
public record PedidoRequest(Long clienteId, Long produtoId, Integer quantidade) {
}
