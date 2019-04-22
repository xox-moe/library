package moe.xox.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice")
public class Notice {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long noticeId;
  private java.sql.Timestamp beginTime;
  private java.sql.Timestamp endTime;
  private String message;
  private Long creatorId;
  private java.sql.Timestamp createTime;

}
