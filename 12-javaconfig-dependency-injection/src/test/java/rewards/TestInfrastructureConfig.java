package rewards;

import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

/**
 * TODO-10: We can test the setup by running an empty test.
 * - If your IDE automatically generated a @Test method, rename it
 *   testRewardForDining. Delete any code in the method body.
 * - Otherwise add a testRewardForDining method & annotate it with
 *   @Test (make sure the @Test is from org.junit.jupiter.api.Test ).
 * - Run the test. If your setup() is working, you get a green bar.
 *
 * TODO-11: Finally run a real test.
 * - Copy the unit test (the @Test method) from
 *   RewardNetworkImplTests#testRewardForDining() under
 *   rewards.internal test package - we are testing
 *   the same code, but using a different setup.
 * - Run the test - it should pass if you have configured everything
 *   correctly. Congratulations, you are done.
 * - If your test fails - did you miss the import in TO DO 7 above?
 *
 */
@Configuration
@Import(RewardsConfig.class)
public class TestInfrastructureConfig {

	/**
	 * Creates an in-memory "rewards" database populated
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource() {
		return (new EmbeddedDatabaseBuilder()) //
				.addScript("classpath:rewards/testdb/schema.sql") //
				.addScript("classpath:rewards/testdb/data.sql") //
				.build();
	}
}
