package reports;

import java.util.List;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import play.Logger;

import models.Iteration;

/**
 * object model representing a burndown report- consumable by generators
 * 
 * @author dlange
 *
 */
public class TeamReport extends Report {
	// how far back to track averages
	public static final int HISTORICAL_ITERATIONS = 5;
	
	public String project;
	public int avgVelocity;
	public int oneStdDevVelocity;
	public int avgPrecision;
	public int avgStoryPts;
	public List<Iteration> iterations;
	public BurndownReport burndown;
	
	public TeamReport(Iteration iteration) {
		// set info for passed in (current) iteration
		this.project = iteration.team.name;
		this.burndown = new BurndownReport(iteration);

		// initialize the historical data (last 'n' iterations)
		initializeHistoricalData(iteration.team.id, HISTORICAL_ITERATIONS);
	}

	/**
	 * Answer back last n iterations
	 * @param team
	 * @return
	 */
	private void initializeHistoricalData(Long teamId, int n) {
		int totalVelocity = 0;
		int totalPlanned = 0;
		int totalStories = 0;
		
		List<Iteration> iterations = Iteration.getLastNTeamIterations(n, teamId);
		if (iterations.size() > 0) {
			// pop off last one
			iterations.remove(iterations.size()-1);
			// sum up totals for previous iterations
			for (Iteration iteration : iterations) {
				totalVelocity += iteration.completedPoints;
				totalPlanned += iteration.totalPoints;
				totalStories += iteration.totalStories;
			}
		}
		// keep iterator
		this.iterations = iterations;
		// calc averages. TODO precision calc is not a good precision
		this.avgVelocity = (iterations.size() > 0)? (int) (totalVelocity / iterations.size()) : 0;
		this.avgPrecision = (totalPlanned > 0)? (int) ((totalVelocity * 100) / totalPlanned) : 0;
		this.avgStoryPts = (totalStories > 0) ? (int)(totalPlanned / totalStories) : 0 ;
		this.oneStdDevVelocity = (int)oneStdDev(iterations);
	}
	
	public double oneStdDev(List<Iteration> iterations) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		// Add the data from the array
		for(Iteration iteration : iterations) {
		    stats.addValue(iteration.completedPoints);
		}
		return stats.getPercentile(68);
	}
	
	public Report generate() {
		htmlReport = views.html.teamreport.render(this).body();
		generated = true;
		return this;
	}
	
}
