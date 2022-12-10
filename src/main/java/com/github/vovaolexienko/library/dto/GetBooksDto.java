package com.github.vovaolexienko.library.dto;

import lombok.Data;

@Data
public class GetBooksDto {

    private int page;

    private Long genre;

    private String authorOrTitle;
}
