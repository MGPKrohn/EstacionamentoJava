package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    public List<Condutor> buscarTodosCondutores() {
        return condutorRepository.findAll();
    }

    public Condutor buscarCondutorPorId(Long id) {
        return condutorRepository.findById(id).orElse(null);
    }

    public Condutor criarCondutor(Condutor condutor) {
        return condutorRepository.save(condutor);
    }

    public Condutor atualizarCondutor(Long id, Condutor condutorAtualizado) {
        Condutor condutorExistente = condutorRepository.findById(id).orElse(null);
        if (condutorExistente == null) {
            return null;
        } else {
            condutorExistente.setNomeCondutor(condutorAtualizado.getNomeCondutor());
            condutorExistente.setCpf(condutorAtualizado.getCpf());
            condutorExistente.setTelefone(condutorAtualizado.getTelefone());
            condutorExistente.setTempoDesconto(condutorAtualizado.getTempoDesconto());
            condutorExistente.setTempoPago(condutorAtualizado.getTempoPago());
            return condutorRepository.save(condutorExistente);
        }
    }

    public void excluirCondutor(Long id) {
        condutorRepository.deleteById(id);
    }
}
