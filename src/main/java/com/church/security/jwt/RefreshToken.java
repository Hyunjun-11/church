package com.church.security.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class RefreshToken {

    // getters and setters
    @Id
    private String memberId;
    private String token;

}