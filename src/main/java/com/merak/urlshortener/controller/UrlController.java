package com.merak.urlshortener.controller;

import com.merak.urlshortener.service.UrlService;
import com.merak.urlshortener.model.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;

@Controller

public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 处理URL缩短请求的POST方法。
     * 该方法接收一个长URL，生成一个短URL，并将其返回给客户端。
     *
     * @param url 需要缩短的长URL
     * @param model 用于存储模型数据，以便在视图中使用
     * @param request HTTP请求对象，用于获取服务器信息
     * @return 返回视图名称，通常为"index"
     */
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url, Model model, HttpServletRequest request) {
        // 调用urlService的shortenUrl方法生成短URL
        String shortCode = urlService.shortenUrl(url);

        // 获取服务器的协议、主机名和端口号
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        // 生成短URL
        String shortUrl = serverUrl + "/" + shortCode;

        // 将生成的短URL添加到模型中
        model.addAttribute("shortUrl", shortUrl);

        // 返回视图名称，通常为"index"
        return "index";
    }


    /**
     * 处理短链接重定向请求的GET映射。
     * 根据提供的短链接代码，查找并重定向到原始URL。
     *
     * @param shortCode 短链接代码
     * @return 重定向到原始URL的响应
     */
    @GetMapping("/{shortCode}")
    public String redirectToOriginalUrl(@PathVariable String shortCode) {
        // 调用urlService获取原始URL
        return urlService.getOriginalUrl(shortCode)
                // 如果找到原始URL，返回重定向响应
                .map(url -> "redirect:" + url.trim())
                // 如果未找到原始URL，重定向到根路径
                .orElse("redirect:/");
    }

}