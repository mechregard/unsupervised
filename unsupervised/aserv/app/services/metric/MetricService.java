package services.metric;

import services.MessageFoundation;
import services.ServiceFoundation;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Metric service
 * example service
 * 
 * @author dlange
 *
 */
public class MetricService extends ServiceFoundation {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	protected void receiveMessage(MessageFoundation message) {
		log.info("MetricService:receiveMessage msg:{}", message.toString());
		if (message instanceof MetricAddMetricMessage) {
			log.info("MetricService:receiveMessage MetricAddMetricMessage msg: no reploy this: {}", getSelf().path().name());
		} else if (message instanceof MetricUpdateMetricMessage) {
			log.info("MetricService:receiveMessage MetricUpdateMetricMessage msg:send reply this: {}", getSelf().path().name());
			MetricUpdateMetricMessage src = (MetricUpdateMetricMessage) message; 
			MetricUpdateMetricMessage reply = new MetricUpdateMetricMessage(src.name, src.weight, src.getReplyTo());
			sender().tell(reply);
		} else {
			unhandled(message);
		}
	}
}
