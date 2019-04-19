package moe.xox.library.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMsgVo  {

    private long bookMessageId;
    private String name;
    private long kindId;
    private String kindName;
    private String author;
    private String publisher;
    private String introduction;
    private boolean status;
    private long creatorId;
    private java.sql.Timestamp createTime;
    private String isbn;
    private int bookNum;


}
