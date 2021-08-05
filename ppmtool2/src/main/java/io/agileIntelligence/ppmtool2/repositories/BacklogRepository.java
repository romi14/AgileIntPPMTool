package io.agileIntelligence.ppmtool2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.agileIntelligence.ppmtool2.domain.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog,Long>{

	Backlog findByProjectIdentifier(String Identifier);
}
