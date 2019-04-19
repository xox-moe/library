package moe.xox.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "collection")
public class Collection {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long collectionId;
  private long userId;
  private long bookMessageId;
  private java.sql.Timestamp createTime;

}
