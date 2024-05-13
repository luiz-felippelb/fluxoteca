package br.com.fluxoteca.backend.dto.Emprestimo;



import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CriacaoEmprestimoDto(
    @NotNull
    Long livro,
    @NotNull
    Long leitor,
    @NotNull
    LocalDate dataDevolucao
) {
    
}
