package services;

import com.typesafe.config.ConfigFactory;

import play.libs.Akka;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.routing.RemoteRouterConfig;
import akka.routing.RoundRobinRouter;
import akka.util.Duration;
import akka.util.Timeout;

/**
 * 
 * @author dlange
 *
 */
public class RoutedEndpoint {
	protected LoggingAdapter log = Logging.getLogger(Akka.system(), this);
	private ActorSystem system;
	private ActorRef ref;
	private ActorRef remoteRef;
	private Timeout timeout = new Timeout(Duration.parse("5 seconds"));
	
	public RoutedEndpoint(ServiceConfig config) {
		// use our own local system rather then play akka system and create local actor from this
        system = ActorSystem.create("ApplicationSystem", ConfigFactory.load().getConfig("router"));
        Address[] addresses = new Address[] { config.getAddress() };
        this.ref = system.actorOf(new Props(config.serviceClass).withRouter(new RemoteRouterConfig(new RoundRobinRouter(5), addresses)));
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
