package psm.percentile.web.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import psm.percentile.common.model.user.ApplicationUser;

import java.util.List;

public interface AdminRepository extends MongoRepository<ApplicationUser, String> {

    List <ApplicationUser> findAll();

    ApplicationUser findByUsername(String username);

    ApplicationUser findById(ObjectId id);

    List <ApplicationUser> findAllByAuthoritiesContaining(String authority);

    ApplicationUser findByUsernameAndAuthoritiesContaining(String username,String authority);

    ApplicationUser save(ApplicationUser applicationUser);

    void deleteByUsernameAndAuthoritiesContaining(String username,String authority);

}




