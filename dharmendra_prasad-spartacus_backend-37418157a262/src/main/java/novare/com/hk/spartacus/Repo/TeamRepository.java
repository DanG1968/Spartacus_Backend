package novare.com.hk.spartacus.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import novare.com.hk.spartacus.domain.Team;

@Repository
public interface TeamRepository  extends CrudRepository<Team, Long>{

}
