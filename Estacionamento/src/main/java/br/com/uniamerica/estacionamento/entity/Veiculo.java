package br.com.uniamerica.estacionamento.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
@Entity
@Table(name = "veiculos", schema = "public")
public class Veiculo extends AbstractEntity {

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @Getter @Setter
    @NotBlank(message = "O campo de placa deve conter 8 Caracteres")
    @Length(max = 8, min = 8)
    @Column(name = "placa", nullable = false, length = 8)
    private String placa;

    @Getter @Setter
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;
    @Getter @Setter
    @NotBlank(message = "O campo de ano modelo deve ser preenchido")
    @Pattern(regexp = "\\d{4}", message = "O campo de ano modelo deve conter apenas dígitos com 4 caracteres")
    @Column(name = "ano_modelo", nullable = false, length = 4)
    private int anoModelo;

    @Getter
    @Setter
    @Column(name = "cor", nullable = false, length = 15)
    private String cor;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "movimentaoca_id")
    private Movimentacao movimentacao;

    public Movimentacao getMovimentacao() {
        return this.movimentacao;
    }

}