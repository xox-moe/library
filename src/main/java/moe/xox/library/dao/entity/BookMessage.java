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
@Table(name = "book_message")
public class BookMessage {
  @Id
  private long bookMessageId;
  private String name;
  private long kindId;
  private String author;
  private String publisher;
  private String introduction;
  private String status;
  private long creatorId;
  private java.sql.Timestamp createTime;
  private String isbn;

}
