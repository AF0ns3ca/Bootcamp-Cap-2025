package com.domains.contracts.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

}
