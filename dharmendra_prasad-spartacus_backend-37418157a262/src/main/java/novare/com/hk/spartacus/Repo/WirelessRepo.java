package novare.com.hk.spartacus.Repo;

import novare.com.hk.spartacus.model.Wireless;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WirelessRepo extends JpaRepository<Wireless, Long> {
}
