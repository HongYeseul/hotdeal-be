package com.example.hot_deal.member.fixture;

import com.example.hot_deal.member.domain.entity.Member;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberFixture {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    public static Member createMember() {
        return fixtureMonkey.giveMeOne(Member.class);
    }

    public static Member memberFixture() {
        String encodedPassword = encoder.encode("rawPassword");
        return fixtureMonkey.giveMeBuilder(Member.class)
                .set("passwordHash", encodedPassword)
                .sample();
    }

}
