package com.listaVip.cadastro.model;

import jakarta.persistence.*;

    @Entity
    public class Papel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private Papeis name;

    }

