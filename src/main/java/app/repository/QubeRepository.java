package app.repository;

import org.springframework.data.repository.CrudRepository;

import app.model.Qube;

public interface QubeRepository extends CrudRepository<Qube, Long> {

}
