package moe.xox.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "borrow_info")
public class BorrowInfo {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long borrowId;
  private Long userId;
  private Long bookId;
  private Boolean ifReturn;
  private java.sql.Timestamp outTime;
  private Long outQuality;
  private java.sql.Timestamp backTime;
  @Column(name = "bakcQuality")
  private Long backQuality;

}
