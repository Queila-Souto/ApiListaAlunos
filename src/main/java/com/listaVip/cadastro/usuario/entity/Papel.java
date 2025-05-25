package com.listaVip.cadastro.usuario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public class Papel {

        public Papeis getNome() {
            return nome;
        }

        public Long getId() {
            return id;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private Papeis nome;

    }

