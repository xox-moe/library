package moe.xox.library.dao;

import moe.xox.library.dao.entity.BookMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMsgRepository extends JpaRepository<BookMessage,Long> {

}
