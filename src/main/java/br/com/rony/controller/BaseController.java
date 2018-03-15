package br.com.rony.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.rony.util.Message;


public abstract class BaseController{
	
	protected List<Message> messages;
	@Inject
	protected Result result;
	
	public BaseController() {
		this.messages = new ArrayList<Message>();
	}

	public void addErrorMessage(String title, String content){
		this.messages.add(new Message(title, "error", content));
	}
	
	public void addErrorMessage(String content){
		this.messages.add(new Message("", "error", content));
	}
	
	public void addWarningMessage(String title, String content){
		this.messages.add(new Message(title, "warning", content));
	}
	
	public void addWarningMessage(String content){
		this.messages.add(new Message("", "warning", content));
	}
	
	public void addSucessMessage(String title, String content){
		this.messages.add(new Message(title, "sucess", content));
	}
	
	public void addSucessMessage(String content){
		this.messages.add(new Message("", "sucess", content));
	}

		
}
