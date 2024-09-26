package rewards;

import common.money.MonetaryAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A system test that verifies the components of the RewardNetwork application
 * work together to reward for dining successfully. Uses Spring to bootstrap the
 * application for use in a test environment.
 */

/*
 * TODO-00: In this lab, you are going to exercise the following:
 * - Using annotation(s) from Spring TestContext Framework for
 *   creating application context for the test
 * - Using profiles in the test
 *
 * TODO-01: Use Spring TestContext Framework
 * - Read through Spring document on Spring TestContext Framework
 *   (https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/testing.html#testcontext-framework)
 */

/* TODO-03: Assign the 'jdbc' profile to all Jdbc*Repository classes
 * - Using the @Profile annotation, assign the 'jdbc' profile to all Jdbc*Repository classes
 *   (such as JdbcAccountRepository).  (Be sure to annotate the actual repository classes in
 *   src/main/java, not the test classes in src/main/test!)
 * - In the same way, assign the 'stub' profile to all Stub*Repository classes
 *   (such as StubAccountRepository)
 * - Add @ActiveProfiles to this test class (below) and specify the "stub" profile.
 * - Run the current test, it should pass.
 * - Examine the logs, they should indicate "stub" repositories were used.
 */

/* TODO-04: Change active-profile to "jdbc".
 * - Rerun the test, it should pass.
 * - Check which repository implementations are being used now.
 */

/* TODO-05: Assign beans to the "local" profile
 * - Go to corresponding step in TestInfrastructureLocalConfig class.
 */

/* TODO-06: Use "jdbc" and "local" as active profiles
 * - Now that the bean 'dataSource' is specific to the local profile, should we expect
 * 	 this test to be successful?
 * - Make the appropriate changes so the current test uses 2 profiles ('jdbc' and 'local').
 * - Rerun the test, it should pass.
 */

/* TODO-07: Use "jdbc" and "jndi" as active profiles
 * - Open TestInfrastructureJndiConfig and note the different datasource that will be
 * 	 used if the profile = 'jndi'.
 * - Now update the current test so it uses profiles 'jdbc' and 'jndi'.
 * - Rerun the test, it should pass.
 */

/* TODO-08 (Optional): Create an inner static class from TestInfrastructureConfig
 * - Once inner static class is created, remove configuration
 *   class reference to TestInfrastructureConfig class from the annotation
 *   you added to this class in TO DO-01 above. (For more detailed on, refer tp
 *   lab document.)
 * - Run the test again.
 */

@SpringJUnitConfig(classes=TestInfrastructureConfig.class)
public class RewardNetworkTests {

	
	/**
	 * The object being tested.
	 */
	@Autowired
	private RewardNetwork rewardNetwork;

	@Test
	@DisplayName("Test if reward computation and distribution works")
	public void testRewardForDining() {
		// create a new dining of 100.00 charged to credit card
		// '1234123412341234' by merchant '123457890' as test input
		Dining dining = Dining.createDining("100.00", "1234123412341234",
				"1234567890");

		// call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
		RewardConfirmation confirmation = rewardNetwork
				.rewardAccountFor(dining);

		// assert the expected reward confirmation results
		assertNotNull(confirmation);
		assertNotNull(confirmation.getConfirmationNumber());

		// assert an account contribution was made
		AccountContribution contribution = confirmation
				.getAccountContribution();
		assertNotNull(contribution);

		// the contribution account number should be '123456789'
		assertEquals("123456789", contribution.getAccountNumber());

		// the total contribution amount should be 8.00 (8% of 100.00)
		assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());

		// the total contribution amount should have been split into 2
		// distributions
		assertEquals(2, contribution.getDistributions().size());

		// The total contribution amount should have been split into 2 distributions
		// each distribution should be 4.00 (as both have a 50% allocation).
		// The assertAll() is from JUnit 5 to group related checks together.
		assertAll("distribution of reward",
				() -> assertEquals(2, contribution.getDistributions().size()),
				() -> assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle").getAmount()),
				() -> assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan").getAmount()));
	}
}