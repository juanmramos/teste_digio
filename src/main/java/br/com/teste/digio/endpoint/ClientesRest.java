package br.com.teste.digio.endpoint;

import br.com.teste.digio.dto.response.ClienteResponse;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientesRest {

  RestClient restClient = RestClient.create();

  public List<ClienteResponse> consumoClientes() {
    return restClient.get()
        .uri("https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
        .retrieve()
        .body(new ParameterizedTypeReference<List<ClienteResponse>>() {});
  }
}
