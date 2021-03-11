package com.diplomska.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="token", schema="wat")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @CreationTimestamp
    private OffsetDateTime date_creation;

    private OffsetDateTime date_expiration;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
