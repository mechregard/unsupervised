package services;

import java.util.Collection;
import java.util.Map;

import services.metric.MetricService;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class ServiceRegistry {
	private static final ServiceRegistry registry = new ServiceRegistry();
	private Map<String,ServiceConfig> serviceConfigMap;
	private Multimap<String,ServiceConfig> systemConfigMap;
	
	private ServiceRegistry() {
		if (registry != null) {
			throw new IllegalStateException("Instance exists");
		}
		// populate map
		ServiceConfig config = new ServiceConfig("metricService", "ContainerSystem", "localhost", 2550, MetricService.class);
		ServiceConfig localConfig = new ServiceConfig("router", "ApplicationSystem", "localhost", 2551, ServiceRouter.class);
		serviceConfigMap = Maps.newHashMap();
		serviceConfigMap.put("metricService", config);
		serviceConfigMap.put("router", localConfig);
		systemConfigMap = ArrayListMultimap.create();
		systemConfigMap.put("ContainerSystem", config);
	}
	
	public ServiceConfig getConfig(String name) {
		return serviceConfigMap.get(name);
	}
	public Collection<ServiceConfig> getConfigListForContainer(String container) {
		return systemConfigMap.get(container);
	}
	public Collection<ServiceConfig> getAll() {
		return serviceConfigMap.values();
	}
	public static final ServiceRegistry getInstance() {
		return registry;
	}
}
