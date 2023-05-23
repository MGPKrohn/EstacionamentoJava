package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/condutor")
public class CondutorController {

    @Autowired
    CondutorService condutorService;
    @Autowired
    CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.condutorRepository.findById(id));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Condutor> condutores = this.condutorRepository.findByAtivo(ativo);

        if (condutores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(condutores);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Condutor> condutores = this.condutorRepository.findAll();

        if (condutores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(condutores);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Condutor condutor) {
        this.condutorRepository.save(condutor);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Condutor condutor) {
        Optional<Condutor> condutorExistente = condutorRepository.findById(id);

        if (condutorExistente.isPresent()) {

            Condutor condutorAtualizado = condutorExistente.get();


            condutorService.atualizarCondutor(condutorAtualizado.getId(), condutor);

            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {

            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        Optional<Condutor> optionalCondutor = condutorRepository.findById(id);

        if (optionalCondutor.isPresent()) {
            Condutor condutor = optionalCondutor.get();
            Movimentacao movimentacao = condutor.getMovimentacao();

            if (condutor.isAtivo()) {
                condutorRepository.delete(condutor);
                return ResponseEntity.ok().body("O registro do condutor foi deletado com sucesso");
            } else {
                condutor.setAtivo(false);
                condutorRepository.save(condutor);
                return ResponseEntity.ok().body("O condutor estava vinculado a uma ou mais movimentações e foi desativado com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


