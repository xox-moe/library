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
@Table(name = "borrow_info")
public class BorrowInfo {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long borrowId;
  private Long userId;
  private Long bookId;
  private Boolean ifReturn;
  private LocalDateTime outTime;
  private Long outQuality;
  private Boolean ifXu;
  private LocalDateTime backTime;
//  @Column(name = "bakcQuality")
  private Long backQuality;

}
