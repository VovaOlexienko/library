package com.github.vovaolexienko.library.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SaveBookDto {

    private Long id;

    private String name;

    private List<Long> genres;

    private List<Long> authors;

    private MultipartFile imageParam;

    private MultipartFile contentParam;
}
