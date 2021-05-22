package app.repository;

import org.springframework.data.repository.CrudRepository;

import app.model.Guest;

public interface GuestRepository extends CrudRepository<Guest, String> {

}
