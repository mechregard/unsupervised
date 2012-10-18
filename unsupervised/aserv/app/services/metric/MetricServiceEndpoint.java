package services.metric;

import services.RoutedEndpoint;
import services.ServiceRegistry;

public class MetricServiceEndpoint extends RoutedEndpoint {

	public MetricServiceEndpoint() {
		super(ServiceRegistry.getInstance().getConfig("metricService"));
	}

	public void addMetric(String name, Double weight) {
		send(new MetricAddMetricMessage(name, weight, remoteRef()));
		log.info("MetricServiceEndpoint:addMetric name:{}", name);
	}
	
	public String updateMetric(String name, Double weight) {
		MetricUpdateMetricMessage result = (MetricUpdateMetricMessage) invoke(new MetricUpdateMetricMessage(name, weight, remoteRef()));
		log.info("MetricServiceEndpoint:updateMetric name:{}", name);
		return result.name;
	}
}
