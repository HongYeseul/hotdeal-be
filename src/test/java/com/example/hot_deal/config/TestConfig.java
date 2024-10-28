package com.example.hot_deal.config;

import com.redis.testcontainers.RedisContainer;
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


@TestConfiguration
public class TestConfig {

    private static final Network NETWORK = Network.newNetwork();

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("hot_deal")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true)
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort())  // 포트 준비까지 대기
            .waitingFor(Wait.forLogMessage(".*ready for connections.*\\s", 1));

    @Container
    private static RedisContainer redisContainer = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG));

    @Container
    private static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"))
            .withReuse(true)
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
        // MySQL과 Redis 컨테이너를 같은 네트워크에 연결
        mysqlContainer.withNetwork(NETWORK);
        redisContainer.withNetwork(NETWORK);
        kafkaContainer.withNetwork(NETWORK); // Kafka도 네트워크에 연결

        // 컨테이너 시작
        mysqlContainer.start();
        redisContainer.start();
        kafkaContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = mysqlContainer.getJdbcUrl();
        String redisHost = redisContainer.getHost();
        Integer redisPort = redisContainer.getMappedPort(6379);

        System.out.println("MySQL JDBC URL: " + jdbcUrl);
        System.out.println("Redis Host: " + redisHost);
        System.out.println("Redis Port: " + redisPort);

        registry.add("spring.datasource.url", () -> mysqlContainer.getJdbcUrl() + "?useSSL=false&allowPublicKeyRetrieval=true");
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mysqlContainer::getDriverClassName);

        // Redis 속성 설정
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));

        // Kafka 속성 설정
        System.out.println("Kafka Bootstrap 카프카 서버 Servers: " + kafkaContainer.getBootstrapServers());
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

}
