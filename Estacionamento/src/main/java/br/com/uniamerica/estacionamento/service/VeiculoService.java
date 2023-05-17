package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Cor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Tipo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloService modeloService;
/*
    @Autowired
    private Tipo tipo;
*/
    public List<Veiculo> buscarTodos() {
        return veiculoRepository.findAll();
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id).orElse(null);
    }

    public Veiculo salvar(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public boolean deletar(Long id) {
        Veiculo veiculo = buscarPorId(id);
        if (veiculo != null) {
            veiculoRepository.delete(veiculo);
            return true;
        } else {
            return false;
        }
    }

    public Veiculo criarVeiculo(String placa, Long idModelo, String cor, Tipo tipo, int anoModelo) {
        Modelo modelo = modeloService.buscarModeloPorId(idModelo);
        if (modelo == null) {
            return null;
        } else {
            Veiculo veiculo = new Veiculo();
            veiculo.setPlaca(placa);
            veiculo.setModelo(modelo);
            veiculo.setCor(String.valueOf(Cor.valueOf(cor)));
            veiculo.setTipo(tipo);
            veiculo.setAnoModelo(anoModelo);
            return veiculoRepository.save(veiculo);
        }
    }

    public List<Veiculo> buscarVeiculosPorModelo(Long idModelo) {
        Modelo modelo = modeloService.buscarModeloPorId(idModelo);
        if (modelo == null) {
            return null;
        } else {
            return veiculoRepository.findByModelo(modelo);
        }
    }


    }


