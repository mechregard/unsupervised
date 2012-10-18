package services;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Foundation class for services
 * default implementations for some hooks
 * 
 * @author dlange
 *
 */
public abstract class ServiceFoundation extends UntypedActor {
	  LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	 
	  /**
	   * Message handler
	   */
	  public void onReceive(Object message) throws Exception {
		  log.info("ServiceFoundation:onReceive Received a service msg: {}", message);
		  if (message instanceof MessageFoundation) {
			  receiveMessage((MessageFoundation)message);
		  } else {
			  log.info("ServiceFoundation:onReceive non-Message");
			  unhandled(message);
		  }
	  }
	  
	  /**
	   * Each service implementation must provide message processing
	   * 
	   * @param message
	   */
	  protected abstract void receiveMessage(MessageFoundation message);
	  
	  /**
	   * Lifecycle hooks
	   */
	  
	  @Override
	  public void preStart() {
		  log.info("ServiceFoundation:preStart");
	  }

	  @Override
	  public void postStop() {
		  log.info("ServiceFoundation:postStop");
	  }
	  
}
