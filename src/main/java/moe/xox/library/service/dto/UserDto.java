package moe.xox.library.service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private long studentId;
    private String email;
    private String head;
    private String nickname;
    private String password;
    private String phone;
    private long roleId;

}
