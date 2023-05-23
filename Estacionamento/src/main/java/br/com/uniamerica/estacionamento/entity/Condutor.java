package br.com.uniamerica.estacionamento.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;
@Entity
@Table(name = "condutores", schema = "public")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @NotBlank(message = "o campo do nome do condutor deve ser preenchido")
    @Length(max = 100, message = "o campo deve ter até no maximo 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nomeCondutor;
    @Getter @Setter
    @Pattern(regexp = "\\d{15}", message = "o campo deve ter 15 campos numericos")
    @Column(name = "cpf", nullable = false, unique = true, length = 15)
    private String cpf;
    @Getter @Setter
    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{17}",message = "o campo de telefone deve conter 17 caracteres numericos para ser comleto")
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
