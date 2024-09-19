package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

/**
 * TODO-00: In this lab, you are going to exercise the following:
 * - Creating Spring configuration class
 * - Defining bean definitions within the configuration class
 * - Specifying the dependency relationships among beans
 * - Injecting dependencies through constructor injection
 * - Creating Spring application context in the test code
 *   (WITHOUT using Spring testContext framework)
 */

@Configuration
public class RewardsConfig {

	private DataSource dataSource;

    public RewardsConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public RewardNetwork rewardNetwork(AccountRepository accountRepository, RestaurantRepository restaurantRepository, RewardRepository rewardRepository) {
        return new RewardNetworkImpl(accountRepository, restaurantRepository, rewardRepository);
    }

    @Bean
    public AccountRepository accountRepository(DataSource dataSource) {
        JdbcAccountRepository accountRepository = new JdbcAccountRepository();
        accountRepository.setDataSource(dataSource);
        return accountRepository;
    }

    @Bean
    public RestaurantRepository restaurantRepository(DataSource dataSource) {
        JdbcRestaurantRepository restaurantRepository = new JdbcRestaurantRepository();
        restaurantRepository.setDataSource(dataSource);
        return restaurantRepository;
    }

    @Bean
    public RewardRepository rewardRepository(DataSource dataSource) {
        JdbcRewardRepository rewardRepository = new JdbcRewardRepository();
        rewardRepository.setDataSource(dataSource);
        return rewardRepository;
    }

}
