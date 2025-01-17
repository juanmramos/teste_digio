package br.com.teste.digio.dto.response;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponse {

  private String nome;
  private String cpf;
  private Set<CompraResponse> compras;

}
