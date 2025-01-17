package br.com.teste.digio.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProdutoResponse {

  private Integer codigo;
  @JsonProperty("tipo_vinho")
  private String tipoVinho;
  private Double preco;
  private String safra;
  @JsonProperty("ano_compra")
  private Integer anoCompra;

}
