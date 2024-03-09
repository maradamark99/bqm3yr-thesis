package com.maradamark99.thesis.auth.verification;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.maradamark99.thesis.user.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationToken {

    private static final int EXPIRATION_TIME_IN_HOURS = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_token_sequence")
    @SequenceGenerator(name = "verification_token_sequence")
    private long id;

    @Column(unique = true, nullable = false)
    private String key;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Builder.Default
    private LocalDateTime expiryDate = LocalDateTime.now().plusHours(EXPIRATION_TIME_IN_HOURS);

    public static VerificationToken withUUIDKey() {
        var key = UUID.randomUUID().toString();
        return VerificationToken.builder()
                .key(key)
                .build();
    }

}
