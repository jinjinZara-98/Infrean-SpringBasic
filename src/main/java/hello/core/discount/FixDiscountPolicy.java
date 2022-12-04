package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//할인정책 인터페이스 구현한 클래스
@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{
   private int discountFIxAmount = 1000;

    @Override
    public int discount(Member member, int price) {

        //파라미터로 들어온 회원 객체에서 등급을 꺼내 vip면 1000원 할인
        if(member.getGrade() == Grade.VIP){
            return discountFIxAmount;
        } else {
            return 0;
        }
    }
}
