package moe.xox.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long roleId;
  private String roleName;
  private long status;

}
