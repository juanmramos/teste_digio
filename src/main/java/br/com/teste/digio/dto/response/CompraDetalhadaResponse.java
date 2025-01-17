package br.com.teste.digio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraDetalhadaResponse {
  private String nomeCliente;
  private String cpfCliente;
  private ProdutoResponse produto = new ProdutoResponse();
  private Integer quantidade;
  private Double valorTotal;
}
