package io.qmeta.wps.controller;

import cn.hutool.core.map.MapUtil;
import io.qmeta.wps.yunfile.WpsYunFileService;
import io.qmeta.wps.yunfile.exception.YunException;
import io.qmeta.wps.yunfile.response.CreateCommitFileResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class WpiYunFileController {

    @Autowired
    WpsYunFileService yunFileService;

    @PostMapping("wps/yunfile")
    public ResponseEntity<CreateCommitFileResponse> updateFile(@RequestParam("file") MultipartFile file,
                                                               @RequestParam(value = "openFileId", required = false) String openFileId) {

        try {
            CreateCommitFileResponse resp = yunFileService.createOrUpdateFile(file, openFileId);
            return ResponseEntity.ok(resp);
        } catch (IOException | YunException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("wps/yunfile/{openFileId}/share")
    public ResponseEntity getSharedFileLink(@PathVariable("openFileId") String openFileId){
        String writeSharedUrl = yunFileService.getWriteSharedLinkUrl(openFileId);
        return ResponseEntity.ok(MapUtil.builder().put("url",writeSharedUrl).build());
    }

    @GetMapping("wps/yunfile/{openFileId}/download")
    public ResponseEntity getDownloadFileLink(@PathVariable("openFileId") String openFileId){
        String downloadUrl = yunFileService.getDownloadUrl(openFileId);
        return ResponseEntity.ok(MapUtil.builder().put("url",downloadUrl).build());
    }
}
