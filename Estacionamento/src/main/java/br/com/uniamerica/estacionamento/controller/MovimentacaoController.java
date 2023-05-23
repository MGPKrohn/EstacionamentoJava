package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    MovimentacaoRepository movimentacaoRepository;
    @Autowired
    MovimentacaoService movimentacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.movimentacaoRepository.findById(id));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Movimentacao> movimentacaos = this.movimentacaoRepository.findByAtivo(ativo);

        if (movimentacaos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(movimentacaos);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Movimentacao> movimentacaos = this.movimentacaoRepository.findAll();

        if (movimentacaos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(movimentacaos);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Movimentacao movimentacao) {
        this.movimentacaoRepository.save(movimentacao);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Movimentacao movimentacao) {
        Optional<Movimentacao> movimentacaoExiste = movimentacaoRepository.findById(id);

        if (movimentacaoExiste.isPresent()){
            Movimentacao movimentacaoAtualizado = movimentacaoExiste.get();

            movimentacaoService.atualizarMovimentacao(movimentacaoAtualizado.getId(), movimentacao);

            return  ResponseEntity.ok().body("Registro de Movimetnacao atulizado com sucesso");
        }
        else{
            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        Optional<Movimentacao> optionalMovimentacao = movimentacaoRepository.findById(id);

        if (optionalMovimentacao.isPresent()) {
            Movimentacao movimentacao = optionalMovimentacao.get();

            if (movimentacao.isAtivo()) {
                movimentacaoRepository.delete(movimentacao);
                return ResponseEntity.ok().body("O Registro de Movimentacao Foi Deletado com sucesso");
            } else {
                movimentacao.setAtivo(false);
                movimentacaoRepository.save(movimentacao);
                return ResponseEntity.ok().body("A movimentacao foi desativada com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
