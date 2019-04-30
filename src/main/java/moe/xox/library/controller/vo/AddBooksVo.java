package moe.xox.library.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBooksVo {

    Long bookMessageId;
    Long quality;
    int count;

}
