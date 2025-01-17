package br.com.teste.digio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteFielResponse {
  private String nome;
  private String cpf;
  private int totalCompras;
  private double valorTotal;
}
