package com.merak.urlshortener.repository;

import com.merak.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCodeAndExpiresAtGreaterThan(String shortCode, LocalDateTime now);
}