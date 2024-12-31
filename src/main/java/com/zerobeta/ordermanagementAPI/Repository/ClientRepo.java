package com.zerobeta.ordermanagementAPI.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobeta.ordermanagementAPI.Model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    Optional<Client> findById(Long id);

    Client save(Client user);

    List<Client> findAll();



}
