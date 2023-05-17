package br.com.uniamerica.estacionamento.entity;


import com.fasterxml.jackson.annotation.JacksonInject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "modelos", schema = "public")
public class Modelo extends AbstractEntity {

    @Column(name = "nome_modelo", nullable = false, unique = true)
    @Getter @Setter
    private String nomeModelo;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @Getter
    @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;
}

