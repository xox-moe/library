package moe.xox.library.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherDto {
    private String teacherNumber;
    private String name;
    private String img;
    private String departmentId;
    private String departmentName;
    private String major;
    private long sex;
}
