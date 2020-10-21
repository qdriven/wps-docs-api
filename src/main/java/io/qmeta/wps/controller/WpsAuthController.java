package io.qmeta.wps.controller;

import cn.hutool.core.map.MapUtil;
import io.qmeta.wps.auth.WpsAuthService;
import io.qmeta.wps.common.WpsApiKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class WpsAuthController {
    @Autowired
    WpsApiKeyHolder apiKeyHolder;
    @Autowired
    WpsAuthService service;

    @Value("${wps.codeSecrete}")
    private String codeSecrete;

    @GetMapping("/code/callback")
    public ResponseEntity receiveCodeRedirectRequest(HttpServletRequest request) {
        String code = request.getParameter("code");
        if (code == null) return ResponseEntity.badRequest().build();
        apiKeyHolder.setCode(code);
        String accessToken = service.getAccessToken();
        apiKeyHolder.setAccessToken(accessToken);
        return ResponseEntity.ok("set code successfully and refresh the access Token");
    }

    @GetMapping("/code/{secrete}")
    public ResponseEntity getRequestUrl(@PathVariable(value = "secrete") String secrete) {
        if (secrete.equalsIgnoreCase(codeSecrete)) {
            String url = service.getRequestUrl();
            return ResponseEntity.ok(MapUtil.builder().put("url", url).build());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/code")
    public ResponseEntity setWpsAuthorizationCode(@RequestBody Map<String, String> body) {

        String code = body.getOrDefault("code", null);
        if (code == null) return ResponseEntity.badRequest().build();
        apiKeyHolder.setCode(code);
        String accessToken = service.getAccessToken();
        if (accessToken == null) return ResponseEntity.badRequest().build();
        apiKeyHolder.setAccessToken(accessToken);
        return ResponseEntity.ok("set code successfully and refresh the access Token");
    }
}
