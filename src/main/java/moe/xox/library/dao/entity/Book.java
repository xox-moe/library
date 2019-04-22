package moe.xox.library.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long bookId;
  private Long bookMessageId;
  private Long bookStatusId;
  private Long quality;
  private String creatorId;
  private LocalDateTime createTime;
  private Boolean status;

}
