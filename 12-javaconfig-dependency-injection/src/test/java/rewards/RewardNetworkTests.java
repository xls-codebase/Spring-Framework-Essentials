package rewards;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

class RewardNetworkTests {

    private RewardNetwork rewardNetwork;

    @BeforeEach
    void setUp() {
        ApplicationContext context =  SpringApplication.run(TestInfrastructureConfig.class);
        rewardNetwork = context.getBean(RewardNetwork.class);
    }

}