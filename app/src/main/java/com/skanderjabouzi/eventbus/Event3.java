package com.skanderjabouzi.eventbus;

public class Event3 {

    String eventMessage;
    Person eventPerson;

    public Event3(String message, Person person) {
        eventMessage = message;
        eventPerson = person;
    }

    public String getMessage() {
        return eventMessage;
    }
    public Person getPerson() { return eventPerson; }
}
