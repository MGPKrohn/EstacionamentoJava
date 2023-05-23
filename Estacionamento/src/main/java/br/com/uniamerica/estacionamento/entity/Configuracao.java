package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "configuracoes", schema = "public")
public class Configuracao extends AbstractEntity{
    @Getter @Setter
    @NotBlank(message = "O campo Valor de Hora deve ser preenchido")
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;
    @Getter @Setter
    @NotBlank(message = "O Campo de Valor Minuto Multa Deve ser Preenchido")
    @Column(name = "valor_minuto_multa", nullable = false)
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @NotBlank(message = "o Campo de inicio de expediente deve ser preenchido")
    @Column(name = "inicio_expediente", nullable = false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @NotBlank(message = "O campo de fim de expediente deve ser preenchido")
    @Column(name = "fim_expediente", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @NotBlank(message = "o Campo tempo para desconto deve ser preenchido")
    @Column(name = "tempo_para_desconto", nullable = false)
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @NotBlank(message = "O campo de tempo Desconto deve ser Preenchido")
    @Column(name = "tempo_desconto",nullable = false)
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerar_desconto")
    private boolean gerarDesconto;
    @Getter @Setter
    @NotBlank(message = "O campo de Vagas deve ser preenchido caso não haja vagas deve ser cadastrado com 0")
    @Column(name = "vagas_moto", nullable = false)
    private int vagasMoto;
    @Getter @Setter
    @NotBlank(message = "O campo de Vagas deve ser preenchido caso não haja vagas deve ser cadastrado com 0")
    @Column(name = "vagas_carro", nullable = false)
    private int vagasCarro;
    @Getter @Setter
    @NotBlank(message = "O campo de Vagas deve ser preenchido caso não haja vagas deve ser cadastrado com 0")
    @Column(name = "vagas_van", nullable = false)
    private int vagasVan;

}
