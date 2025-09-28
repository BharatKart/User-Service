package com.bharatkart.userservice.Repository;

import com.bharatkart.userservice.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByUsernameAndOtpAndUsedFalse(String username, String otp);
}
