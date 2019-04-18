package moe.xox.library.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LessonDto {
    private long teacherId;
    private String lessonName;
    private String typeId;
    private String type;
    private String img;
    private String intro;
    private String introduction;
}
