package hello.core.discount;

import hello.core.member.Member;

//할인정책 인터페이스
public interface DiscountPolicy {

    //회원과 가격을 파라미터로 넣으면 할인금액 리턴
    int discount(Member member, int price);
}
