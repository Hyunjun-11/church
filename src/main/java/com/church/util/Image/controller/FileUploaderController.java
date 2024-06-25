package com.church.util.Image.controller;


import com.church.util.gcs.GcsBucketUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class FileUploaderController {

    private final GcsBucketUpload gcsBucketUpload;
    //이미지 업로드
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String fileUpload(@ModelAttribute MultipartFile file) throws IOException {
        return gcsBucketUpload.fileUpload(file);
    }
    @DeleteMapping(value = "/")
    public ResponseEntity<String> deleteFile(@RequestParam String fileUrl) throws IOException {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        boolean deleted = gcsBucketUpload.deleteFile(fileName);
        if (deleted) {
            return ResponseEntity.ok("File deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }


}
