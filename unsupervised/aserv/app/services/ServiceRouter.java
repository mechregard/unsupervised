package services;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
/**
 * Actor for performing various notifications
 * @author dlange
 *
 */
public class ServiceRouter extends UntypedActor {
	  LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	 
	  /**
	   */
	  public void onReceive(Object message) throws Exception {
		  log.info("ServiceRouter:onReceive msg: {}", message);
		  if (message instanceof MessageFoundation) {
			  MessageFoundation src = (MessageFoundation)message;
			  src.reply = sender();
			  src.getRemote().tell(src, getSelf());
			  log.info("route message to {}", src.getRemote().toString());
		  } else {
		    	unhandled(message);
		  }
	  }
}
