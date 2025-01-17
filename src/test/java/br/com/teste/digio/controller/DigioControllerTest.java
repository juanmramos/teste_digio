package br.com.teste.digio.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.teste.digio.dto.response.ClienteResponse;
import br.com.teste.digio.dto.response.CompraDetalhadaResponse;
import br.com.teste.digio.dto.response.CompraResponse;
import br.com.teste.digio.dto.response.ProdutoResponse;
import br.com.teste.digio.endpoint.ClientesRest;
import br.com.teste.digio.endpoint.ProdutosRest;
import br.com.teste.digio.service.DigioService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class DigioControllerTest {

  @InjectMocks
  private DigioController digioController;

  @Mock
  private DigioService digioService;

  @Mock
  private ProdutosRest produtosRest;

  @Mock
  private ClientesRest clientesRest;

  @Mock
  RestClient restClient = RestClient.create();

  @Test
  public void test_compras_returns_detailed_purchases() {
    // Arrange
    ClienteResponse cliente = ClienteResponse.builder()
        .nome("João")
        .cpf("123")
        .compras(Set.of(CompraResponse.builder()
            .codigo("1")
            .quantidade(2)
            .build()))
        .build();

    ProdutoResponse produto = ProdutoResponse.builder()
        .codigo(1)
        .preco(100.0)
        .tipoVinho("Tinto")
        .build();

    when(clientesRest.consumoClientes()).thenReturn(List.of(cliente));
    when(produtosRest.consumoProdutos()).thenReturn(List.of(produto));

    // Act
    List<CompraDetalhadaResponse> result = digioService.compras();

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    CompraDetalhadaResponse compra = result.get(0);
    assertEquals("João", compra.getNomeCliente());
    assertEquals("123", compra.getCpfCliente());
    assertEquals(2, compra.getQuantidade());
    assertEquals(200.0, compra.getValorTotal());
  }

  @Test
  public void test_compras_with_empty_client_list() {
    // Arrange
    when(clientesRest.consumoClientes()).thenReturn(Collections.emptyList());
    when(produtosRest.consumoProdutos()).thenReturn(Collections.emptyList());

    // Act
    List<CompraDetalhadaResponse> result = digioService.compras();

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(clientesRest).consumoClientes();
    verify(produtosRest).consumoProdutos();
  }
}