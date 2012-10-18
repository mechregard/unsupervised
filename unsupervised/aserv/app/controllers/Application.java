package controllers;

import play.*;
import play.mvc.*;
import services.metric.MetricServiceEndpoint;

import views.html.*;

public class Application extends Controller {
  private static MetricServiceEndpoint metricServiceEndpoint = null;
  
  public static Result index() {
	  //getMetricServiceEndpoint().addMetric("cpu", 90.0);
	  getMetricServiceEndpoint().updateMetric("mem", 2.0);
	  return ok(index.render("Metric added and updated"));
  }
  
  // Inject endopint some day
  protected static MetricServiceEndpoint getMetricServiceEndpoint() {
	  if (null == metricServiceEndpoint) {
		  metricServiceEndpoint = new MetricServiceEndpoint();
	  }
	  return metricServiceEndpoint;
  }
  
}