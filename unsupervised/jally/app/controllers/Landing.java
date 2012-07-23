package controllers;

import reports.Dashboard;
import jobs.DashboardType;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author dlange
 *
 */
public class Landing extends Controller {
    
	/**
	 * Draw the master dashboard page
	 * @return
	 */
    public static Result dashboard() {
    	return ok(views.html.dashboard.render(new Dashboard(DashboardType.Master).generate()));
    }

    /**
     * Draw the scrum dashboard
     * @return
     */
    public static Result scrum() {
    	return ok(views.html.dashboard.render(new Dashboard(DashboardType.Scrum).generate()));
    }

}
