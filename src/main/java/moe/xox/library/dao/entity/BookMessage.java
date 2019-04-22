package moe.xox.library.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_message")
public class BookMessage {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long bookMessageId;
  private String name;
  private Long kindId;
  private String author;
  private String publisher;
  private String introduction;
  private Boolean status;
  private Long creatorId;
  private LocalDateTime createTime;
  private String isbn;

}
