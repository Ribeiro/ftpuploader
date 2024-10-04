package br.tec.gtech.ftp_uploader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dados")
public class Dado {
    @Id
    private Long id;
    private String nome;
    private String valor;
}
