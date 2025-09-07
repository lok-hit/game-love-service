module game.love.service {
    requires com.fasterxml.jackson.annotation;
    requires com.hazelcast.core;
    requires io.swagger.v3.oas.annotations;
    requires io.swagger.v3.oas.models;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires kafka.clients;
    requires static lombok;
    requires micrometer.core;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.actuator.autoconfigure;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires spring.kafka;
    requires spring.retry;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.oauth2.jose;
    requires spring.security.oauth2.resource.server;
    requires spring.security.web;
    requires spring.tx;
    requires spring.web;

    // Exports

    exports controller;
    exports service;
    exports dto;
    exports entity;
    exports mapper;
    exports repository;
    exports exception;
    exports kafka;


}