package br.com.teste.digio.service;

import br.com.teste.digio.dto.response.ClienteFielResponse;
import br.com.teste.digio.dto.response.ClienteResponse;
import br.com.teste.digio.dto.response.CompraDetalhadaResponse;
import br.com.teste.digio.dto.response.CompraResponse;
import br.com.teste.digio.dto.response.ProdutoResponse;
import br.com.teste.digio.endpoint.ClientesRest;
import br.com.teste.digio.endpoint.ProdutosRest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DigioService {

  private final ProdutosRest produtosRest;
  private final ClientesRest clientesRest;

  public List<CompraDetalhadaResponse> compras() {
    List<ClienteResponse> clientes = consumoClientes();
    List<CompraDetalhadaResponse> comprasDetalhadas = new ArrayList<>();
    List<ProdutoResponse> produtoResponses = produtosRest.consumoProdutos();
    for (ClienteResponse cliente : clientes) {
      for (CompraResponse compra : cliente.getCompras()) {
        produtoResponses.forEach( produtoResponse -> {
          double valorTotal = compra.getQuantidade() * produtoResponse.getPreco();
          comprasDetalhadas.add(new CompraDetalhadaResponse(
              cliente.getNome(),
              cliente.getCpf(),
              produtoResponse,
              compra.getQuantidade(),
              valorTotal
          ));
        });
      }
    }
    comprasDetalhadas.sort(Comparator.comparingDouble(CompraDetalhadaResponse::getValorTotal));
    return comprasDetalhadas;
  }

  public CompraDetalhadaResponse maiorCompraAno(Integer ano) {
    List<ClienteResponse> clientes = consumoClientes();
    CompraDetalhadaResponse maiorCompra = new CompraDetalhadaResponse();
    List<ProdutoResponse> produtoResponses = produtosRest.consumoProdutos();
    for (ClienteResponse cliente : clientes) {
      for (CompraResponse compra : cliente.getCompras()) {
        produtoResponses.forEach(produtoResponse -> {
          CompraDetalhadaResponse compraDetalhadaResponse = maiorCompra(ano, cliente, compra,
              produtoResponse, maiorCompra);
          if (Objects.nonNull(compraDetalhadaResponse)) {
            produtoResponse.setAnoCompra(compraDetalhadaResponse.getProduto().getAnoCompra());
            produtoResponse.setPreco(compraDetalhadaResponse.getProduto().getPreco());
            produtoResponse.setCodigo(compraDetalhadaResponse.getProduto().getCodigo());
            produtoResponse.setSafra(compraDetalhadaResponse.getProduto().getSafra());
            maiorCompra.setQuantidade(compraDetalhadaResponse.getQuantidade());
            maiorCompra.setCpfCliente(compraDetalhadaResponse.getCpfCliente());
            maiorCompra.setNomeCliente(compraDetalhadaResponse.getNomeCliente());
            maiorCompra.setValorTotal(compraDetalhadaResponse.getValorTotal());
            maiorCompra.setProduto(produtoResponse);
          }
        });
      }
    }
    return maiorCompra;
  }

  private CompraDetalhadaResponse maiorCompra(Integer ano, ClienteResponse cliente, CompraResponse compra,
      ProdutoResponse produtoResponse, CompraDetalhadaResponse maiorCompra) {
    CompraDetalhadaResponse compraDetalhada = null;
    if (Objects.equals(produtoResponse.getAnoCompra(), ano)) {
      double valorTotal = compra.getQuantidade() * produtoResponse.getPreco();
      compraDetalhada = new CompraDetalhadaResponse(
          cliente.getNome(),
          cliente.getCpf(),
          produtoResponse,
          compra.getQuantidade(),
          valorTotal
      );
    }
    return compraDetalhada;
  }

  public List<ClienteFielResponse> clientesFieis() {
    List<ClienteResponse> clientes = clientesRest.consumoClientes();
    List<ClienteFielResponse> clientesFieis = new ArrayList<>();
    List<ProdutoResponse> produtoResponses = produtosRest.consumoProdutos();
    for (ClienteResponse cliente : clientes) {
      double valorTotal = 0;
      int totalCompras = 0;

      for (CompraResponse compra : cliente.getCompras()) {
        ProdutoResponse produtoResponse = produtoResponses.stream()
            .filter(produto -> produto.getCodigo().equals(compra.getCodigo()))
            .findFirst()
            .orElse(null);

        if (produtoResponse != null) {
          totalCompras += compra.getQuantidade() * produtoResponse.getPreco();
        }
      }

      clientesFieis.add(new ClienteFielResponse(
          cliente.getNome(),
          cliente.getCpf(),
          totalCompras,
          valorTotal
      ));
    }

    return clientesFieis.stream()
        .sorted(Comparator.comparingInt(ClienteFielResponse::getTotalCompras).reversed()
            .thenComparingDouble(ClienteFielResponse::getValorTotal).reversed())
        .limit(3)
        .collect(Collectors.toList());
  }

  public ProdutoResponse recomendacaoVinhoTipoClienteCompra(String cpf) {
    // Busca cliente pelo CPF
    ClienteResponse cliente = buscarClientePorCpf(cpf);
    if (cliente == null) {
      throw new IllegalArgumentException("Cliente não encontrado");
    }

    // Mapeia os tipos de vinho e suas frequências
    Map<String, Integer> tipoVinhoFrequencia = new HashMap<>();
    for (CompraResponse compra : cliente.getCompras()) {
      ProdutoResponse produto = buscarProdutoPorCodigo(compra.getCodigo());
      tipoVinhoFrequencia.merge(produto.getTipoVinho(), compra.getQuantidade(), Integer::sum);
    }

    // Encontra o tipo de vinho mais comprado
    String tipoMaisComprado = tipoVinhoFrequencia.entrySet().stream()
        .max(Comparator.comparingInt(Map.Entry::getValue))
        .map(Map.Entry::getKey)
        .orElse(null);

    if (tipoMaisComprado == null) {
      throw new IllegalArgumentException("Não há recomendações disponíveis para este cliente");
    }

    // Retorna um vinho do tipo mais comprado
    return buscarProdutoPorTipo(tipoMaisComprado);
  }

  private ClienteResponse buscarClientePorCpf(String cpf) {
    // Simula a busca de cliente (substituir com lógica real)
    List<ClienteResponse> clientes = clientesRest.consumoClientes();
    return clientes.stream()
        .filter(cliente -> cliente.getCpf().equals(cpf))
        .findFirst()
        .orElse(null);
  }

  private ProdutoResponse buscarProdutoPorCodigo(String codigo) {
    // Simula a busca de produto por código (substituir com lógica real)
    return produtosRest.consumoProdutos().stream()
        .filter(produto -> produto.getCodigo().equals(Integer.parseInt(codigo)))
        .findFirst()
        .orElse(null);
  }

  private ProdutoResponse buscarProdutoPorTipo(String tipoVinho) {
    // Simula a recomendação de um produto do mesmo tipo
    return produtosRest.consumoProdutos().stream()
        .filter(produto -> produto.getTipoVinho().equals(tipoVinho))
        .findFirst()
        .orElse(null);
  }

  private List<ClienteResponse> consumoClientes() {
    return clientesRest.consumoClientes();
  }

  private List<ProdutoResponse> consumoProdutos() {
    return produtosRest.consumoProdutos();
  }
}
