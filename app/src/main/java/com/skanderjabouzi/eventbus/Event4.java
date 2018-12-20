package com.skanderjabouzi.eventbus;

public class Event4 {

    String eventMessage;
    String eventText;

    public Event4(String message, String text) {
        eventMessage = message;
        eventText = text;
    }

    public String getMessage() {
        return eventMessage;
    }

    public String getText() { return eventText; }
}
