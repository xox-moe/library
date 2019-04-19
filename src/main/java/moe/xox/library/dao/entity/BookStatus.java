package moe.xox.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_status")
public class BookStatus {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long bookStatusId;
  private String bookStatusName;
  private boolean status;

}
