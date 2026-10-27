package br.com.baozistore.controller;

import br.com.baozistore.dto.PedidoRequest;
import br.com.baozistore.model.Cliente;
import br.com.baozistore.model.Pedido;
import br.com.baozistore.model.Produto;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.PedidoRepository;
import br.com.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody PedidoRequest req) {
        Pedido pedido = new Pedido();
        preencher(pedido, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody PedidoRequest req) {
        return pedidoRepository.findById(id).map(pedido -> {
            preencher(pedido, req);
            return ResponseEntity.ok(pedidoRepository.save(pedido));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void preencher(Pedido pedido, PedidoRequest req) {
        if (req.clienteId() == null || req.produtoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "clienteId e produtoId são obrigatórios");
        }
        if (req.quantidade() == null || req.quantidade() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantidade deve ser maior que zero");
        }
        Cliente cliente = clienteRepository.findById(req.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente " + req.clienteId() + " não encontrado"));
        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto " + req.produtoId() + " não encontrado"));
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(req.quantidade());
    }
}
