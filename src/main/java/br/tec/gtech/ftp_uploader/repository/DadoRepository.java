package br.tec.gtech.ftp_uploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.tec.gtech.ftp_uploader.entity.Dado;

@Repository
public interface DadoRepository extends JpaRepository<Dado, Long> {
    
}
