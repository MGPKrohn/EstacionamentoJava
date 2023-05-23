package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private ConfiguracaoService configuracaoService;

    public List<Movimentacao> buscarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }

    public Movimentacao buscarMovimentacaoPorId(Long id) {
        return movimentacaoRepository.findById(id).orElse(null);
    }

    public Movimentacao registrarEntrada(Condutor condutor, Veiculo veiculo) {
        Configuracao configuracao = configuracaoService.buscarConfiguracao();
        if (configuracao == null) {
            return null;
        } else {
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setCondutor(condutor);
            movimentacao.setVeiculo(veiculo);
            Date data = new Date();
            Instant instant = data.toInstant();
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            movimentacao.setDataEntrada(localDateTime);
            movimentacao.setValorHora(configuracao.getValorHora());
            movimentacaoRepository.save(movimentacao);
            return movimentacao;
        }
    }

    public Movimentacao registrarSaida(Long id, Date dataSaida) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        Configuracao configuracao = configuracaoService.buscarConfiguracao();
        if (movimentacao == null || configuracao == null) {
            return null;
        } else {
            //Lembrete para tentar concertar essa bagunca pq não consegui, pedir ajuda do professor
            //pra essa caceta de conta que nem eu mais entendo !!!!!!!!!!!!!!!!!
         /*   Date dataSaida = new Date();
            Instant instant = dataSaida.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
            movimentacao.setDataSaida(localDateTime);
            long diff = dataSaida.getTime() - movimentacao.getDataEntrada().getTime();
            long diffHours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            long diffMinutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            diffMinutes -= diffHours * 60;
            long tempo = diffHours * 60 + diffMinutes;
            movimentacao.setTempo(tempo);
            if (tempo > configuracao.getTempoParaDesconto()) {
                long tempoDesconto = tempo - configuracao.getTempoDeDesconto();
                movimentacao.setTempoDesconto(tempoDesconto);
                movimentacao.setValorDesconto(configuracao.getgerarDesconto() ? (tempoDesconto * configuracao.getValorHora() / 2) : 0);
            }
            movimentacao.setValorTotal(movimentacao.getValorHora() * tempo - movimentacao.getValorDesconto() + movimentacao.getValorMulta());
            movimentacaoRepository.save(movimentacao);
            return movimentacao;*/
        }
        return movimentacao;
    }

    public boolean registrarMulta(Long id, Date dataMulta) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        Configuracao configuracao = configuracaoService.buscarConfiguracao();
        if (movimentacao == null || configuracao == null) {
            return false;
        } else {
            long diff = dataMulta.getTime() - movimentacao.getDataEntrada().getHour();
            long diffHours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            long diffMinutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            diffMinutes -= diffHours * 60;
            LocalTime tempoMulta;
            LocalTime horaSaida = movimentacao.getDataSaida().toLocalTime();
            LocalTime horaAtual = LocalTime.now();
            long minutos = ChronoUnit.MINUTES.between(horaSaida, horaAtual) - movimentacao.getTempoDesconto().getTime();
            if (minutos > 0) {
                BigDecimal valorMulta = configuracao.getValorMinutoMulta().multiply(BigDecimal.valueOf(minutos));
                movimentacao.setValorMulta(valorMulta);
            }

            LocalDateTime dataHoraSaida = movimentacao.getDataSaida();
            LocalDateTime agora = LocalDateTime.now();
            LocalDateTime dataHoraMulta = dataHoraSaida.withHour(agora.getHour())
                    .withMinute(agora.getMinute())
                    .withSecond(agora.getSecond())
                    .withNano(agora.getNano());
            Duration duracaoMulta = Duration.between(dataHoraMulta, dataHoraSaida);
            long minutosMulta = duracaoMulta.toMinutes();
            if (minutosMulta > 0) {
                BigDecimal valorMulta = configuracao.getValorMinutoMulta().multiply(BigDecimal.valueOf(minutosMulta));
                movimentacao.setValorMulta(valorMulta);
            }

            //Lembrete quando a operação envolver dois bigdecimal's o operador de mais não ira funcionar
            //ao inves de + usar .add
            movimentacao.setValorTotal(movimentacao.getValorTotal().add(movimentacao.getValorMulta()));
            movimentacaoRepository.save(movimentacao);
                return true;
            }
        }


    public List<Movimentacao> buscarMovimentacoesPorCondutor(Long idCondutor) {
        Condutor condutor = condutorService.buscarCondutorPorId(idCondutor);
        if (condutor == null) {
            return null;
        } else {
            return movimentacaoRepository.findByAtivo(condutor.isAtivo());
        }
    }

    public List<Movimentacao> buscarMovimentacoesPorVeiculo(Long idVeiculo) {
        Veiculo veiculo = veiculoService.buscarPorId(idVeiculo);
        if (veiculo == null) {
            return null;
        } else {
            return null;
        }
    }

    public Movimentacao atualizarMovimentacao(Long id, Movimentacao movimentacaoAtualizado) {
        Movimentacao movimentacaoExistente = movimentacaoRepository.findById(id).orElse(null);
        if (movimentacaoExistente == null) {
            return null;
        } else {
            movimentacaoExistente.setCondutor(movimentacaoAtualizado.getCondutor());
            movimentacaoExistente.setTempo(movimentacaoAtualizado.getTempo());
            movimentacaoExistente.setDataEntrada(movimentacaoAtualizado.getDataEntrada());
            movimentacaoExistente.setDataSaida(movimentacaoAtualizado.getDataSaida());
            movimentacaoExistente.setVeiculo(movimentacaoAtualizado.getVeiculo());
            movimentacaoExistente.setTempoDesconto(movimentacaoAtualizado.getTempoDesconto());
            movimentacaoExistente.setTempoMulta(movimentacaoAtualizado.getTempoMulta());
            movimentacaoExistente.setValorDesconto(movimentacaoAtualizado.getValorDesconto());
            movimentacaoExistente.setValorHora(movimentacaoAtualizado.getValorHora());
            movimentacaoExistente.setValorTotal(movimentacaoAtualizado.getValorTotal());
            movimentacaoExistente.setValorMulta(movimentacaoAtualizado.getValorMulta());
            movimentacaoExistente.setValorHoraMulta(movimentacaoAtualizado.getValorHoraMulta());
            return movimentacaoRepository.save(movimentacaoExistente);
        }
    }
}

