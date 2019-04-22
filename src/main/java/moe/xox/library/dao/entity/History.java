package moe.xox.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history")
public class History {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long historyId;
  private Long userId;
  private Long bookId;
  private java.sql.Timestamp createTime;

}
