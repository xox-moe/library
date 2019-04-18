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
@Table(name = "user")
public class User {

  @Id
  private long userId;
  private String email;
  private String nickName;
  private String password;
//  private long roleId;
  private java.sql.Timestamp birthday;
  private String realName;
  private long grade;
  private String department;
  private String major;
  private long sex;

}
