package br.com.teste.digio.controller;

import br.com.teste.digio.dto.ApiErrorResponse;
import br.com.teste.digio.dto.response.ClienteFielResponse;
import br.com.teste.digio.dto.response.CompraDetalhadaResponse;
import br.com.teste.digio.dto.response.ProdutoResponse;
import br.com.teste.digio.service.DigioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/digio")
public class DigioController {

private final DigioService digioService;

  @Operation(
      summary = "Busca uma lista de comprar ordenadas de forma crescente por valor",
      description = "Busca uma lista de comprar ordenadas de forma crescente por valor",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Operação realizada com sucesso!",
              useReturnTypeSchema = true
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Campo(s) com valor(es) inválido(s) ou a ausência campo(s)",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "422",
              description = "Dados inválidos para buscar a lista de compras",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Erro inesperado do servidor",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          )
      }
  )
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/compras")
  public List<CompraDetalhadaResponse> compras() {
    return digioService.compras();
  }

  @Operation(
      summary = "Buscar pela maior comprar",
      description = "Buscar pela maior comprar",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Operação realizada com sucesso!",
              useReturnTypeSchema = true
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Campo(s) com valor(es) inválido(s) ou a ausência campo(s)",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "422",
              description = "Dados inválidos para busca da maior comprar",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Erro inesperado do servidor",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          )
      }
  )
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/maior-compra/{ano}")
  public CompraDetalhadaResponse maiorCompraAno(@PathVariable("ano") Integer ano) {
    return digioService.maiorCompraAno(ano);
  }

  @Operation(
      summary = "Busca - Retornar o Top 3 cliente mais fieis",
      description = "Retornar o Top 3 cliente mais fieis",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Operação realizada com sucesso!",
              useReturnTypeSchema = true
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Campo(s) com valor(es) inválido(s) ou a ausência campo(s)",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "422",
              description = "Dados inválidos para busca do top 3 de clientes fieis",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Erro inesperado do servidor",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          )
      }
  )
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/clientes-fieis")
  public List<ClienteFielResponse> clientesFieis() {
    return digioService.clientesFieis();
  }

  @Operation(
      summary = "Busca - Retorna uma recomendação de vinho, baseado no tipos de vinhos que o cliente mais compra",
      description = "Busca - Retorna uma recomendação de vinho, baseado no tipos de vinhos que o cliente mais compra",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Operação realizada com sucesso!",
              useReturnTypeSchema = true
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Campo(s) com valor(es) inválido(s) ou a ausência campo(s)",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "422",
              description = "Dados inválidos para busca de recomendações",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Erro inesperado do servidor",
              content = {
                  @Content(
                      schema = @Schema(implementation = ApiErrorResponse.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE
                  )
              }
          )
      }
  )
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/recomendacao/cliente/tipo/{cpf}")
  public ProdutoResponse recomendacaoVinhoTipoClienteCompra(@PathVariable String cpf) {
    return digioService.recomendacaoVinhoTipoClienteCompra(cpf);
  }

}
