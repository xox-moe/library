package moe.xox.library.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {
  @Id
  private long bookId;
  private long bookMessageId;
  private long bookStatusId;
  private long quality;
  private String creatorId;
  private java.sql.Timestamp createTime;
  private String status;

}
