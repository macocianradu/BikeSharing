package com.crud.bikeshare.bikeshare.Repository;

import com.crud.bikeshare.bikeshare.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
}
