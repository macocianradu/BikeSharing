package com.crud.bikeshare.bikeshare.Repository;

import com.crud.bikeshare.bikeshare.Model.BikeStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BikeStationRepository extends CrudRepository<BikeStation, Long> {
}
