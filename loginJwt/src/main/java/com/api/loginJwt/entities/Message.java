package com.api.loginJwt.entities;

//Clase utilizada para mostrar los mensajes enviados en el controller
public class Message {

	private String infoMessage;

	public Message(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}
}
