package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaService marcaService;

    public List<Modelo> buscarModelos() {
        return modeloRepository.findAll();
    }

    public Modelo buscarModeloPorId(Long id) {
        return modeloRepository.findById(id).orElse(null);
    }

    public Modelo criarModelo(Modelo novoModelo) {
        Marca marca = marcaService.buscarMarcaPorId(novoModelo.getMarca().getId());
        if (marca == null) {
            return null;
        } else {
            novoModelo.setMarca(marca);
            return modeloRepository.save(novoModelo);
        }
    }

    public Modelo atualizarModelo(Long id, Modelo modeloAtualizado) {
        Modelo modeloExistente = modeloRepository.findById(id).orElse(null);
        Marca marcaAtualizada = marcaService.buscarMarcaPorId(modeloAtualizado.getMarca().getId());
        if (modeloExistente == null || marcaAtualizada == null) {
            return null;
        } else {
            modeloExistente.setNomeModelo(modeloAtualizado.getNomeModelo());
            modeloExistente.setMarca(marcaAtualizada);
            return modeloRepository.save(modeloExistente);
        }
    }

    public boolean excluirModelo(Long id) {
        Modelo modeloExistente = modeloRepository.findById(id).orElse(null);
        if (modeloExistente == null) {
            return false;
        } else {
            modeloRepository.delete(modeloExistente);
            return true;
        }
    }
}
