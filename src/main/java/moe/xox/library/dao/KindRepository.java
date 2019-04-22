package moe.xox.library.dao;

import moe.xox.library.dao.entity.BookKind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindRepository extends JpaRepository<BookKind,Long> {
}
