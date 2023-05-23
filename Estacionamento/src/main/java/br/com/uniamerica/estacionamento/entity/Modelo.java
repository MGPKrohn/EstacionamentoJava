package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "modelos", schema = "public")
public class Modelo extends AbstractEntity {
    @Getter @Setter
    @NotBlank(message = "O nome do modelo é obrigatório")
    @Length(max = 15, message = "O nome do modelo deve ter no máximo 15 caracteres")
    @Column(name = "nome_modelo", nullable = false, unique = true, length = 15)
    private String nomeModelo;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "marca_id", unique = true, nullable = false)
    private Marca marca;
}