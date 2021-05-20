package app.repository;

import org.springframework.data.repository.CrudRepository;

import app.model.Party;

public interface PartyRepository extends CrudRepository<Party, String> {

}
