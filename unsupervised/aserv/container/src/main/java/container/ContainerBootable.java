package container;

import org.h2.util.StringUtils;

import play.libs.Akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.kernel.Bootable;
import com.typesafe.config.ConfigFactory;

import services.ServiceConfig;
import services.ServiceRegistry;

public class ContainerBootable implements Bootable {
    private static final String systemName = "ContainerSystem";
    public ContainerBootable() {
        for (ServiceConfig config : ServiceRegistry.getInstance().getConfigListForContainer(systemName)) {
    		ActorSystem system = ActorSystem.create(config.system, ConfigFactory.load().getConfig(config.name));
            ActorRef actor = system.actorOf(new Props(config.serviceClass), config.name);
        }
    }

    public void startup() {
    }

    // TODO go through all configs
    public void shutdown() {
    }
}