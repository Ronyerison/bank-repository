package br.com.rony.util;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String type;
	private String content;
	
	public Message() {
	}
	
	public Message(String title, String type, String content) {
		this.title = title;
		this.type = type;
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message [title=" + title + ", type=" + type + ", content="
				+ content + "]";
	}
	
}