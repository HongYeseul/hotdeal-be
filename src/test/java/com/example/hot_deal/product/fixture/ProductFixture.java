package com.example.hot_deal.product.fixture;

import com.example.hot_deal.product.domain.entity.Product;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.customizer.InnerSpec;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductFixture {

    static InnerSpec innerSpec = new InnerSpec()
            .property("id", id -> id.postCondition(Long.class, it -> it > 0))
            .property("stockQuantity", stock -> stock.postCondition(Long.class, it -> it > 10));

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .build();

    public static Product createProduct() {
        return fixtureMonkey.giveMeBuilder(Product.class)
                .setInner(innerSpec)
                .sample();
    }

    public static Product productFixture() {
        return new Product(
                "제품",
                new BigDecimal("10000"),
                100L,
                LocalDateTime.now()
        );
    }
}
