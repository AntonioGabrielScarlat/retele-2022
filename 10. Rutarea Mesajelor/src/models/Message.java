package models;

import java.io.Serializable;

import server.TCPServer;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String sender;
	private String destination;
	private String subject;
	private String text;
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return destination+", ai primit un mesaj de la "+sender+":\n"+"Subiect: "+subject+"\n"+"Mesaj:"+text;
	}
}