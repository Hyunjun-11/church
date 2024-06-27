package com.church.domain.board.dto;

import com.church.domain.board.entity.Files;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilesDto {
    private String fileName;
    private Long fileSize;

    public FilesDto(Files file) {
        this.fileName = file.getFileName();
        this.fileSize = file.getFileSize();
    }
}