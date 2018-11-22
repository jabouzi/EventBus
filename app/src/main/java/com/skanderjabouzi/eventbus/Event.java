package com.skanderjabouzi.eventbus;

public class Event {

    String eventMessage;

    public Event(String message) {
        eventMessage = message;
    }

    public String getMessage() {
        return eventMessage;
    }
}
