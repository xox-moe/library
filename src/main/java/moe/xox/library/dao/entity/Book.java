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
  private Long bookId;
  private Long bookMessageId;
  private Long bookStatusId;
  private Long quality;
  private String creatorId;
  private java.sql.Timestamp createTime;
  private Boolean status;

}
