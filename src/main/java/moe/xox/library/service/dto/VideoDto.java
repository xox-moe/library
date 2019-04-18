package moe.xox.library.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoDto {

    private long lessonId;
    private String intro;
    private String introduction;
    private String img;
    private String title;
    private String url;
    private String sourceUrl;
    private boolean status;

}
