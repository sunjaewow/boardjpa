package com.example.miniboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class Board {
    private int boardId;
    private String title;
    private String content;
    private int userId;
    private LocalDateTime regdate;
    private int viewCnt;
    private String name;

}
