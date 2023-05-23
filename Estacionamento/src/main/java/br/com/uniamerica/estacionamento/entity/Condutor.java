package br.com.uniamerica.estacionamento.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
@Entity
@Table(name = "condutores", schema = "public")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 100)
    private String nomeCondutor;
    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true, length = 15)
    private String cpf;
    @Getter @Setter
    @NotBlank(message = "O telefone é obrigatório")
    @Length(max = 17, message = "O telefone deve ter 17 caracteres")
    @Column(name = "telefone", nullable = false, length = 17)
    private String telefone;
    @Getter @Setter
    @Column(name = "tempod_esconto")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "tempo_pago")
    private  LocalTime tempoPago;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "movimentacao_id",updatable = true)
    private Movimentacao movimentacao;
}
