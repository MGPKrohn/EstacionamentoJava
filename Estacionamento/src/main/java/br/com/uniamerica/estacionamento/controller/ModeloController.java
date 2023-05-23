package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.*;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.ModeloService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/modelo")
public class ModeloController {

    @Autowired
    ModeloService modeloService;
    @Autowired
    ModeloRepository modeloRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    VeiculoRepository veiculoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.modeloRepository.findById(id));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Modelo> modelos = this.modeloRepository.findByAtivo(ativo);

        if (modelos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(modelos);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Modelo> modelos = this.modeloRepository.findAll();

        if (modelos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(modelos);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid Modelo modelo) {
        this.modeloRepository.save(modelo);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Modelo modelo) {
        Optional<Modelo> modeloExistente = modeloRepository.findById(id);

        if (modeloExistente.isPresent()) {

            Modelo modeloAtualizado = modeloExistente.get();


            modeloService.atualizarModelo(modeloAtualizado.getId(), modelo);

            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {

            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Modelo> optionalModelo = modeloRepository.findById(id);
        Optional<Veiculo> optionalVeiculo = veiculoRepository.findById(id);

        if (optionalModelo.isPresent()) {
            Modelo modelo = optionalModelo.get();

            if (modelo.isAtivo()) {
                modeloRepository.delete(modelo);
                return ResponseEntity.ok("O registro do modelo foi deletado com sucesso");
            } else {
                modelo.setAtivo(false);
                modeloRepository.delete(modelo);
                return ResponseEntity.ok("O modelo estava vinculado a uma ou mais movimentações e foi desativado com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}