package services;

import akka.actor.Address;

public class ServiceConfig {
	public String name;
	public String system;
	public String host;
	public int port;
	public Class serviceClass;
	public ServiceConfig(String name, String system, String host, int port,	Class serviceClass) {
		this.name = name;
		this.system = system;
		this.host = host;
		this.port = port;
		this.serviceClass = serviceClass;
	}
	
	public String getUri() {
		return "akka://"+system+"@"+host+":"+port+"/user/"+name;
	}
	
	public Address getAddress() {
		return new Address("akka", system, host, port);
	}
}
