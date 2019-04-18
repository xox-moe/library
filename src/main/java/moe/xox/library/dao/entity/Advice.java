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
@Table(name = "advice")
public class Advice {
  @Id
  private long adviceId;
  private long userId;
  private String message;
  private java.sql.Timestamp createTime;

}
