package com.church.util.message;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message<T> {
    private String message;
    private T data;

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
