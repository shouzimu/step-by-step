package com.dh.handler;

public class Event {
	private String eventId;
	private String eventDt;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventDt() {
		return eventDt;
	}

	public void setEventDt(String eventDt) {
		this.eventDt = eventDt;
	}

	public Event() {
	}

	public Event(String eventId, String eventDt) {
		this.eventId = eventId;
		this.eventDt = eventDt;
	}
}

