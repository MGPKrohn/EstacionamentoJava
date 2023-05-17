package br.com.uniamerica.estacionamento.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "marcas", schema = "public")
public class Marca extends AbstractEntity {
    @Getter @Setter
    @Column(name = "nome_marca", nullable = false, unique = true, length = 15)
    private String nomeMarca;

    @OneToMany(mappedBy = "marca")
    private List<Modelo> modelos;

}

