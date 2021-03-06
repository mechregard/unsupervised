package models;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.libs.Yaml;
import play.test.FakeApplication;
import play.test.Helpers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;


public class IterationTest {

	public static FakeApplication app;
	public static DdlGenerator ddl;
	public static EbeanServer server;

	@BeforeClass
	public static void setup() {
		app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
		Helpers.start(app);

		server = Ebean.getServer("default");

		ServerConfig config = new ServerConfig();
		config.setDebugSql(true);

		ddl = new DdlGenerator((SpiEbeanServer) server, new H2Platform(), config);
	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}

	@Before
	public void resetDb() throws IOException {

		// drop
		String dropScript = ddl.generateDropDdl();
		ddl.runScript(false, dropScript);

		// create
		String createScript = ddl.generateCreateDdl();
		ddl.runScript(false, createScript);

		// insert data
		Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("ftest.yml");
		Ebean.save(all.get("releases"));
		Ebean.save(all.get("teams"));
		Ebean.save(all.get("iterations"));
		Ebean.save(all.get("burndowns"));
	}


	@Test
	public void getByName() {
		Iteration iteration = Iteration.getByName("2012/06/17");
		assertThat(iteration).isNotNull();
	}
}