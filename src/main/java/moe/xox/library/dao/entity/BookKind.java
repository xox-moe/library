package moe.xox.library.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_kind")
public class BookKind {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long kindId;
  private String kindName;
  private boolean status;

}
