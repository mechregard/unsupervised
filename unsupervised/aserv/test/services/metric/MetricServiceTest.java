package services.metric;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.FakeApplication;
import play.test.Helpers;
import services.metric.MetricServiceEndpoint;

public class MetricServiceTest {
	public static FakeApplication app;

	@BeforeClass
	public static void setup() {
		app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
		try {
		Helpers.start(app);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void add() {
		MetricServiceEndpoint endpoint = new MetricServiceEndpoint();
		endpoint.addMetric("first", 0.1);
	}

//	@Test
	public void update() {
		MetricServiceEndpoint endpoint = new MetricServiceEndpoint();
		String name = endpoint.updateMetric("second", 0.2);
		assertThat(name).isEqualTo("second");
	}

}