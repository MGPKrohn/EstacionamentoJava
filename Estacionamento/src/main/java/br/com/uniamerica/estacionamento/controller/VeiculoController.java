package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/veiculo")
public class VeiculoController {

    @Autowired
    VeiculoRepository veiculoRepository;

    @Autowired
    VeiculoService veiculoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.veiculoRepository.findById(id));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Veiculo> veiculos = this.veiculoRepository.findByAtivo(ativo);

        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(veiculos);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Veiculo> veiculos = this.veiculoRepository.findAll();

        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(veiculos);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Veiculo veiculo) {
        this.veiculoRepository.save(veiculo);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Veiculo veiculo) {
        Optional<Veiculo> veiculoExiste = veiculoRepository.findById(id);

        if (veiculoExiste.isPresent()) {
            Veiculo veiculoAtualizado = veiculoExiste.get();

            veiculoService.atualizarVeiculo(veiculoAtualizado.getId(), veiculo);
            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {
            return ResponseEntity.badRequest().body("Id não foi encontrado ou não corresponde ao veículo informado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        Optional<Veiculo> optionalVeiculo = veiculoRepository.findById(id);

        if (optionalVeiculo.isPresent()) {
            Veiculo veiculo = optionalVeiculo.get();

            if (veiculo .isAtivo()) {
                veiculoRepository.delete(veiculo);
                return ResponseEntity.ok("O registro do veículo foi deletado com sucesso");
            } else {
                veiculo.setAtivo(false);
                veiculoRepository.save(veiculo);
                return ResponseEntity.ok("O veículo estava vinculado a uma ou mais movimentações e foi desativado com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
