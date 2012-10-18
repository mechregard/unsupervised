package services;

import java.io.Serializable;

import akka.actor.ActorRef;

public class MessageFoundation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int STATUS_OK = 200;
	public static final int STATUS_INVALID = 300;
	
	public int statusCode;
	
	protected ActorRef actor;
	protected ActorRef reply;
	
	public ActorRef getRemote() {
		return actor;
	}
	
	public ActorRef getReplyTo() {
		return reply;
	}
}
