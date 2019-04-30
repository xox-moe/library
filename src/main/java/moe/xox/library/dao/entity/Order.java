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
@Table(name = "`order`")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  private Long bookMessageId;
  private Long userId;
  private LocalDateTime orderTime;
  @Column(name = "`code`")
  private String code;
  private boolean ifTakeAway;
  private LocalDateTime takeAwayTime;
  private boolean status;

}
