package com.mavro.services;

import com.mavro.entities.RefreshToken;
import com.mavro.exceptions.RefreshTokenNotFoundException;
import com.mavro.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken generateRefreshToken() {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(LocalDateTime.now());

        return refreshTokenRepository.save(refreshToken);
    }

   public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Token was not found."));
    }

   public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
   }
}