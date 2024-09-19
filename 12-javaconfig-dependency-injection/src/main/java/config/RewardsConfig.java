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

@Configuration
public class RewardsConfig {

	private DataSource dataSource;

    public RewardsConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public RewardNetwork rewardNetwork() {
        return new RewardNetworkImpl(accountRepository(), restaurantRepository(), rewardRepository());
    }

    @Bean
    public AccountRepository accountRepository() {
        JdbcAccountRepository accountRepository = new JdbcAccountRepository();
        accountRepository.setDataSource(dataSource);
        return accountRepository;
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        JdbcRestaurantRepository restaurantRepository = new JdbcRestaurantRepository();
        restaurantRepository.setDataSource(dataSource);
        return restaurantRepository;
    }

    @Bean
    public RewardRepository rewardRepository() {
        JdbcRewardRepository rewardRepository = new JdbcRewardRepository();
        rewardRepository.setDataSource(dataSource);
        return rewardRepository;
    }

}
