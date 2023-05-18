package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "marcas", schema = "public")
public class Marca extends AbstractEntity {

    @Getter @Setter
    @NotBlank(message = "O nome da marca é obrigatório")
    @Length(max = 25, message = "O nome da marca deve ter no máximo 25 caracteres")
    @Column(name = "nome_marca", nullable = false, unique = true, length = 25)
    private String nomeMarca;
}