package novare.com.hk.spartacus.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import novare.com.hk.spartacus.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}
