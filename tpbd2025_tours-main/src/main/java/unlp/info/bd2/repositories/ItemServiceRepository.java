package unlp.info.bd2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;

import java.util.List;

@Repository
public interface ItemServiceRepository extends CrudRepository<ItemService, Long> {



}
