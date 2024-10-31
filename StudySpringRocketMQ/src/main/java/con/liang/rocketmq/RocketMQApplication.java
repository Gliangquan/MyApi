package con.liang.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName：RocketMQApplication
 * @Author: liangquan
 * @Date: 2024/10/31 10:37
 * @Description: RocketMQApplication
 */
@SpringBootApplication
@Slf4j
public class RocketMQApplication {
    private static void main(String[] args) {
        SpringApplication.run(RocketMQApplication.class, args);
        log.info("--------RocketMQApplication----------");
    }
}
