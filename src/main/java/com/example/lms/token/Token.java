package com.example.lms.token;

import com.example.lms.owner.LibraryOwner;
import com.example.lms.patron.Patron;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue()
    private Long id;

    @Column(unique = true, columnDefinition = "TEXT")
    private String accessToken;

    public boolean revoked;

    public boolean expired;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

}