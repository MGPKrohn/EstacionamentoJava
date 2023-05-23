package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.*;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/marca")
public class MarcaController {
    @Autowired
    MarcaService marcaService;
    @Autowired
    MarcaRepository marcaRepository;
    @Autowired
    ModeloRepository modeloRepository;
    @Autowired
    VeiculoRepository veiculoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Marca> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.marcaRepository.findById(id).orElse(new Marca()));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Marca> marcas = this.marcaRepository.findByAtivo(ativo);

        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(marcas);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Marca> marcas = this.marcaRepository.findAll();

        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(marcas);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid Marca marca) {
        this.marcaRepository.save(marca);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Marca marca) {
        Optional<Marca> marcaExiste = marcaRepository.findById(id);

        if (marcaExiste.isPresent()) {

            Marca marcaAtualizado = marcaExiste.get();


            marcaService.atualizarMarca(marcaAtualizado.getId(), marca);

            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {

            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deletar (@RequestParam("id") final Long id){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

        try{
            this.marcaService.deletar(marcaBanco);
            return ResponseEntity.ok("Registro deletado");
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }
    }

}