package services;

import com.typesafe.config.ConfigFactory;

import play.libs.Akka;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

/**
 * 
 * @author dlange
 *
 */
public class Endpoint {
	protected LoggingAdapter log = Logging.getLogger(Akka.system(), this);
	private ActorSystem system;
	private ActorRef ref;
	private ActorRef remoteRef;
	private Timeout timeout = new Timeout(Duration.parse("5 seconds"));
	
	public Endpoint(ServiceConfig config) {
		// use our own local system rather then play akka system and create local actor from this
        system = ActorSystem.create("ApplicationSystem", ConfigFactory.load().getConfig("router"));
        this.ref = system.actorOf(new Props(ServiceRouter.class));
        this.remoteRef = system.actorFor(config.getUri());	
        log.info("Endpoint() router created:{} and lookup for remote:{}", ref, config.getUri());
	}
	
	public void send(MessageFoundation message) {
		ref.tell(message, ref);
	}
	
	public MessageFoundation invoke(MessageFoundation message) {
		MessageFoundation result = null;
	    Future<Object> future = Patterns.ask(ref, message, timeout);
	    try {
			result = (MessageFoundation) Await.result(future, timeout.duration());
			result.statusCode = MessageFoundation.STATUS_OK;
		} catch (Exception e) {
			result.statusCode = MessageFoundation.STATUS_OK;
			log.error("Endpoint:invoke exception:",e);
		}
		return result;
	}

	public ActorRef ref() {
		return ref;
	}
	public ActorRef remoteRef() {
		return remoteRef;
	}	
}
