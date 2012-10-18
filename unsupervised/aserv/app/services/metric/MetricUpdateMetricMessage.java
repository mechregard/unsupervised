package services.metric;

import akka.actor.ActorRef;
import services.MessageFoundation;

public class MetricUpdateMetricMessage extends MessageFoundation {
	private static final long serialVersionUID = 1L;
	public String name;
	public Double weight;
	public MetricUpdateMetricMessage(String name, Double weight, ActorRef actor) {
		super();
		this.name = name;
		this.weight = weight;
		this.actor = actor;
	}
}
