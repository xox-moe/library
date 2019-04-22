package moe.xox.library.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    @DateTimeFormat
    @JsonFormat
    private LocalDateTime beginTime;
    @DateTimeFormat
    @JsonFormat
    private LocalDateTime endTime;
    private String message;
    private Long creatorId;
    private LocalDateTime createTime;

}
