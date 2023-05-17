package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> buscarMarcas() {
        return marcaRepository.findAll();
    }

    public Marca buscarMarcaPorId(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

    public Marca criarMarca(Marca novaMarca) {
        return marcaRepository.save(novaMarca);
    }

    public Marca atualizarMarca(Long id, Marca marcaAtualizada) {
        Marca marcaExistente = marcaRepository.findById(id).orElse(null);
        if (marcaExistente == null) {
            return null;
        } else {
            marcaExistente.setNomeMarca(marcaAtualizada.getNomeMarca());
            return marcaRepository.save(marcaExistente);
        }
    }

    public boolean excluirMarca(Long id) {
        Marca marcaExistente = marcaRepository.findById(id).orElse(null);
        if (marcaExistente == null) {
            return false;
        } else {
            marcaRepository.delete(marcaExistente);
            return true;
        }
    }

    public void delete(Long id) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
        marcaRepository.delete(marca);
    }
}
@Configuration
class AppConfig {
    @Bean
    public Marca marca() {
        return new Marca();
    }
}

