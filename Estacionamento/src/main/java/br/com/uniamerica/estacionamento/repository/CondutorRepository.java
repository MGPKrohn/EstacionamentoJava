package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    @Query("From Condutor where ativo = :ativo")
    public List<Condutor> findByAtivo(@Param("ativo")final boolean ativo);

    @Query("SELECT c FROM Condutor c WHERE c.id = :id")
    Optional<Condutor> findById(@Param("id") Long id);

}
