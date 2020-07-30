package bade.com.mx.cassandrakeyspaces.repository.domain;

import bade.com.mx.cassandrakeyspaces.entity.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * {@link UserRepository}
 */
public interface UserRepository extends CrudRepository<User, String> {

}
