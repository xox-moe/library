package moe.xox.library.dao;

import moe.xox.library.dao.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection,Long> {

    Collection findCollectionByUserIdAndBookMessageId(Long userId, Long bookMessageId);

    Collection findCollectionByCollectionId(Long collectionId);
}
