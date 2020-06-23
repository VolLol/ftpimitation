package net.example.ftpimitation.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class UserEntity {

    @Getter
    private Long id;

    @Getter
    private String username;

    @Getter
    private String cleanPassword;

    public UserEntity() {
    }
}
