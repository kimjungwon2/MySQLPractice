package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다")
    @Test
    public void testChangeNickName() {
        var member = MemberFixtureFactory.create();
        var expected = "cat";

        member.changeNickName(expected);

        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원 닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNickNameMaxLength() {
        var member = MemberFixtureFactory.create();
        var overMaxLengthName = "catcatcatcatcat";


        Assertions.assertThrows(IllegalArgumentException.class,
                ()->member.changeNickName(overMaxLengthName));

    }

}
