package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/configuracao")
public class ConfiguracaoController {

    @Autowired
    ConfiguracaoService configuracaoService;
    @Autowired
    ConfiguracaoRepository configuracaoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.configuracaoRepository.findById(id));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Configuracao> configuracoes = this.configuracaoRepository.findByAtivo(ativo);

        if (configuracoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(configuracoes);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Configuracao> configuracaos = this.configuracaoRepository.findAll();

        if (configuracaos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(configuracaos);
    }


    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Configuracao configuracao) {
        this.configuracaoRepository.save(configuracao);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Configuracao configuracao) {
        Optional<Configuracao> configuracaoCriado = configuracaoRepository.findById(id);

        if (configuracaoCriado.isPresent()){
            Configuracao configuracaoAtualizado = configuracaoCriado.get();

            configuracaoService.atualizarConfiguracao(configuracaoAtualizado.getId(), configuracao);

            return  ResponseEntity.ok().body("Registro de cadastro atulizado com sucesso");
        }
        else{
            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        Optional<Configuracao> optionalConfiguracao = configuracaoRepository.findById(id);

        if (optionalConfiguracao.isPresent()) {
            Configuracao configuracao = optionalConfiguracao.get();

            if (configuracao.isAtivo()) {
                configuracaoRepository.delete(configuracao);
                return ResponseEntity.ok().body("O Registro de Configuração Foi Deletado com sucesso");
            } else {
                configuracao.setAtivo(false);
                configuracaoRepository.save(configuracao);
                return ResponseEntity.ok().body("A configuração foi desativada com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
