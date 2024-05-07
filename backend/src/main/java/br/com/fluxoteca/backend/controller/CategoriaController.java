package br.com.fluxoteca.backend.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fluxoteca.backend.dto.Categoria.AtualizacaoCategoriaDto;
import br.com.fluxoteca.backend.dto.Categoria.CategoriaResponseDto;
import br.com.fluxoteca.backend.dto.Categoria.CriacaoCategoriaDto;
import br.com.fluxoteca.backend.model.Categoria;
import br.com.fluxoteca.backend.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaResponseDto> criar(@RequestBody @Valid CriacaoCategoriaDto data, UriComponentsBuilder uriBuilder){
        Categoria categoria = new Categoria();
        BeanUtils.copyProperties(data, categoria);

        categoriaRepository.save(categoria);

        var uri = uriBuilder.path("categorias/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).body(new CategoriaResponseDto(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listar(){

        var categoriasList = categoriaRepository.findAll().stream().map(CategoriaResponseDto::new).toList();

        return ResponseEntity.ok(categoriasList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<CategoriaResponseDto> atualizar(@RequestBody @Valid AtualizacaoCategoriaDto data){
        var categoria = categoriaRepository.getReferenceById(data.id());
        categoria.atualizarInformacao(null);

        return ResponseEntity.ok(new CategoriaResponseDto(categoria));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        var categoria = categoriaRepository.getReferenceById(id);
        categoria.inativar();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public void reativar(@PathVariable Long id){
        var categoria = categoriaRepository.getReferenceById(id);
        categoria.ativar();
    }
}
