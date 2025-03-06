package com.merak.urlshortener.service;

import com.merak.urlshortener.model.Url;
import com.merak.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // 生成短链接
    public String shortenUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        
        // 先生成一个临时ID用于生成shortCode
        long tempId = System.currentTimeMillis();
        // 将临时ID转换为62进制
        String shortCode = encodeBase62(tempId);
        url.setShortCode(shortCode);

        // 保存URL实体
        Url savedUrl = urlRepository.save(url);
        return savedUrl.getShortCode();
    }

    // 获取原始URL
    public Optional<String> getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCodeAndExpiresAtGreaterThan(shortCode, LocalDateTime.now())
                .map(Url::getOriginalUrl);
    }

    // 将数字转换为62进制
    private String encodeBase62(Long number) {
        if (number == 0) {
            return String.valueOf(ALPHABET.charAt(0));
        }

        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(ALPHABET.charAt((int) (number % BASE)));
            number = number / BASE;
        }

        return sb.reverse().toString();
    }
}