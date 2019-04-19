package moe.xox.library.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_message")
public class BookMessage {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long bookMessageId;
  private String name;
  private long kindId;
  private String author;
  private String publisher;
  private String introduction;
  private boolean status;
  private long creatorId;
  private java.sql.Timestamp createTime;
  private String isbn;

}
