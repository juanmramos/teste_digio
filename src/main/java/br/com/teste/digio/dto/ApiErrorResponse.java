package br.com.instivo.agenda.infrastructure.controlleradvice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class ApiErrorResponse {

  @Schema(description = "Detalhes do erro")
  private String details;

  @Schema(description = "HTTP status code")
  private int status;

  @Schema(description = "URI da requisição", example = "/v1/some/path")
  private String path;

  @Schema(description = "Data e hora da requisição")
  private LocalDateTime timestamp;

  @Schema(description = "Lista de erros")
  private Collection<ValidationError> errors;

  private ApiErrorResponse(String details, int status, String path, Collection<ValidationError> errors) {
    this.details = details;
    this.status = status;
    this.path = path;
    this.timestamp = LocalDateTime.now();
    this.errors = errors;
  }

  public record ValidationError(String field, String message) {

  }

  public static ApiErrorResponse createApiErrorResponse(String message, int status, String path) {
    return createApiErrorResponse(message, status, path, new ArrayList<>());
  }

  public static ApiErrorResponse createApiErrorResponse(String message, int status, String path, Collection<ValidationError> errors) {
    return new ApiErrorResponse(message, status, path, errors);
  }
}
