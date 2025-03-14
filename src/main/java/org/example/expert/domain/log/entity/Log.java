package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class Log extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String requestUrl;
    private Long requestId;
    private Long managerId;
    private Long todoId;

    public Log(String message, String requestUrl, Long requestId, Long managerId, Long todoId) {
        this.message = message;
        this.requestUrl = requestUrl;
        this.requestId = requestId;
        this.managerId = managerId;
        this.todoId = todoId;
    }
}
