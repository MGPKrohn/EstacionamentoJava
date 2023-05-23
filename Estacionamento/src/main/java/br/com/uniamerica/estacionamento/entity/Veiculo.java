package br.com.uniamerica.estacionamento.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Entity
@Table(name = "veiculos", schema = "public")
public class Veiculo extends AbstractEntity {

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @Getter
    @Setter
    @Column(name = "placa", nullable = false, length = 8)
    private String placa;

    @Getter @Setter
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano_modelo", nullable = false, length = 5)
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