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
@Table(name = "borrow_info")
public class BorrowInfo {
  @Id
  private long borrowId;
  private long userId;
  private long bookId;
  private String ifReturn;
  private java.sql.Timestamp outTime;
  private long outQuality;
  private java.sql.Timestamp backTime;
  private long bakcQuality;

}
