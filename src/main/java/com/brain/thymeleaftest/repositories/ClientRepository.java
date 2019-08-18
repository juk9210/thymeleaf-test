package com.brain.thymeleaftest.repositories;

import com.brain.thymeleaftest.entities.Client;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findByName(String name);

}
