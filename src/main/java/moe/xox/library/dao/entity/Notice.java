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
@Table(name = "notice")
public class Notice {
  @Id
  private long noticeId;
  private java.sql.Timestamp beginTime;
  private java.sql.Timestamp endTime;
  private String message;
  private long creatorId;
  private java.sql.Timestamp createTime;

}
