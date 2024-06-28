package com.church.domain.board.entity;

public enum Category {
    SAMUEL("사무엘"),
    DANIEL("다니엘"),
    ESTHER("에스더"),
    YOUTH("청년부"),
    WOMEN("여전도회"),
    MEN1("남전도회1"),
    MEN2("남전도회2"),
    GOWITH("예수님과동행"),
    DEVELOP("개발");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
