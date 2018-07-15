package psm.percentile.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import psm.percentile.common.model.user.ApplicationUser;

import java.util.List;

/**
 * Created by Patrycja on 01.09.2017.
 */
public interface UsersRepository extends MongoRepository<ApplicationUser, String> {

    ApplicationUser findByUsername(String username);

    ApplicationUser findByUsernameAndPassword(String username, String password);

    ApplicationUser save(ApplicationUser applicationUser);

    List <ApplicationUser> findAll();

    List <ApplicationUser> findAllByAuthoritiesContaining(String authority);

}
