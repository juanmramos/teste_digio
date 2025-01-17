package br.com.teste.digio.endpoint;

import br.com.teste.digio.dto.response.ProdutoResponse;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

@Configuration
public class ProdutosRest {

  RestClient restClient = RestClient.create();

  public List<ProdutoResponse> consumoProdutos() {
    return restClient.get()
        .uri("https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json")
        .retrieve()
        .body(new ParameterizedTypeReference<List<ProdutoResponse>>() {});
  }

}
