package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy")
//위 코드에서 값이 틀리면 컴파일 오류가 안 떠 에러 파악이 힘드니 아예 차제적으로 어노테이션을 만들어
//철자를 틀리지 않게 인지 할 수 있도록
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    //ctrl shift t 로 테스트 클래스 생성
    @Override
    public int discount(Member member, int price) {

        if(member.getGrade() == Grade.VIP) {
            return price * discountPercent /100;

        } else {
            return 0;
        }
    }
}
