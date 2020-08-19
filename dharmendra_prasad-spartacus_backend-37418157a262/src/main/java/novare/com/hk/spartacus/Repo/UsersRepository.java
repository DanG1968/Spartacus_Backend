package novare.com.hk.spartacus.Repo;

import novare.com.hk.spartacus.domain.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

  Users findByName(String username);
  
  Users findByEmail(String email);
}
