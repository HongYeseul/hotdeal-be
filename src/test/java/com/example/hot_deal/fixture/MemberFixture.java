package com.example.hot_deal.fixture;

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

    /**
     * FixtureMonkey 사용
     * @return 랜덤으로 생성된 Member 객체
     */
    public static Member createMember() {
        return fixtureMonkey.giveMeOne(Member.class);
    }

    /**
     * FixtureMonkey 사용
     * @return 암호화된 비밀번호를 가진 랜덤으로 생성된 Member 객체
     */
    public static Member memberFixture() {
        String encodedPassword = encoder.encode(getFixtureRawPassword());
        return fixtureMonkey.giveMeBuilder(Member.class)
                .set("passwordHash", encodedPassword)
                .sample();
    }

    /**
     * @return FixtureMonkey를 사용하지 않은 평문 비밀번호를 가진 Member 객체
     */
    public static Member plainMemberFixture() {
        return new Member("YS", "test@gmail.com", getFixtureRawPassword());
    }

    public static String getFixtureRawPassword() {
        return "rawPassword";
    }

}
