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
@Table(name = "notice")
public class Notice {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long noticeId;
  private LocalDateTime beginTime;
  private LocalDateTime endTime;
  private String message;
  private Long creatorId;
  private LocalDateTime createTime;

}
