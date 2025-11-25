package com.listaVip.cadastro.aluno.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String primeiroNome;
    @Setter
    private String sobrenome;
    @Setter
    private String curso;
    @Setter
    private String telefone;
    @Setter
    private Long usuario_id;

    public Aluno(){}

    @Override
    public String toString() {
        return "Pessoa{" +
                "Id='" + id + '\'' +
                "primeiroNome='" + primeiroNome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", curso='" + curso + '\'' +
                ", telefone='" + telefone + '\'' +
                ", responsável='"+usuario_id+'\''+
                '}';
    }
}
