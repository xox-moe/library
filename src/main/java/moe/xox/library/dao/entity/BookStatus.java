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
@Table(name = "book_status")
public class BookStatus {
  @Id
  private long bookStatusId;
  private String bookStatusName;
  private String status;

}
