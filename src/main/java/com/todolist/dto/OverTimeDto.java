package com.todolist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OverTimeDto {
    private int count;
    private List<String> titles;

    public OverTimeDto() {
        titles = new ArrayList<>();
    }
    public void addTitle(String title) {titles.add(title);}
}
