package ca.uqam.repositories;

import ca.uqam.model.Client;
import ca.uqam.model.Location;
import ca.uqam.model.Vehicule;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long>{
    List<Location> findAll();

	Iterable<Location> findByClient(Client client);

	Iterable<Location> findByVehicule(Vehicule voiture);

	Location findByVehicule(Vehicule voiture, Date date);
}
