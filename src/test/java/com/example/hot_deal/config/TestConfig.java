package com.example.hot_deal.config;

import com.redis.testcontainers.RedisContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import java.time.Duration;
import java.util.Map;


@Slf4j
@TestConfiguration
public class TestConfig {

    private static final Network NETWORK = Network.newNetwork();

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName(System.getProperty("test.db.name", "hot_deal"))
            .withUsername(System.getProperty("test.db.username", "test"))
            .withPassword(System.getProperty("test.db.password", "test"))
            .withReuse(true)
            .withStartupTimeout(Duration.ofMinutes(2))
            .withStartupAttempts(3)
            .waitingFor(Wait.forListeningPort())
            .waitingFor(Wait.forLogMessage(".*ready for connections.*\\s", 1));

    @Container
    private static RedisContainer redisContainer = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG))
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort());

    @Container
    private static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"))
            .withReuse(true)
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort());

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(Map.of("bootstrap.servers", kafkaContainer.getBootstrapServers()));
    }

    @Bean
    public NewTopic appliedUsersTopic() {
        return TopicBuilder.name("applied-users")
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(
            redisContainer.getHost(),
            redisContainer.getMappedPort(6379)
        );
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


    static {
        try {
            mysqlContainer.withNetwork(NETWORK);
            redisContainer.withNetwork(NETWORK);
            kafkaContainer.withNetwork(NETWORK);

            startContainers();
        } catch (Exception e) {
            log.error("컨테이너 시작 중 오류 발생", e);
            throw new IllegalStateException("테스트 환경 설정 실패", e);
        }
    }

    private static void startContainers() {
        log.info("테스트용 컨테이너 시작 중 ...");

        // 컨테이너 시작
        mysqlContainer.start();
        redisContainer.start();
        kafkaContainer.start();
        log.info("모든 테스트용 컨테이너가 성공적으로 시작되었습니다.");
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // MySQL DB 속성 설정
        setDatabaseProperties(registry);
        // Redis 속성 설정
        setRedisProperties(registry);
        // Kafka 속성 설정
        setKafkaProperties(registry);
    }

    private static void setDatabaseProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = mysqlContainer.getJdbcUrl();
        log.info("MySQL JDBC URL: " + jdbcUrl);

        registry.add("spring.datasource.url", () -> mysqlContainer.getJdbcUrl() + "?useSSL=false&allowPublicKeyRetrieval=true");
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mysqlContainer::getDriverClassName);
    }

    private static void setRedisProperties(DynamicPropertyRegistry registry) {
        log.info("Redis Host: {}, Port: {}", redisContainer.getHost(), redisContainer.getMappedPort(6379));

        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    private static void setKafkaProperties(DynamicPropertyRegistry registry) {
        log.info("Kafka Bootstrap Servers: {}", kafkaContainer.getBootstrapServers());
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }
}
