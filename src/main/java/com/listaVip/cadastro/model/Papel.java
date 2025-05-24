package com.listaVip.cadastro.model;

import jakarta.persistence.*;

    @Entity
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

