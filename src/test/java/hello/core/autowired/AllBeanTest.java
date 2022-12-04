package hello.core.autowired;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.AutoAppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {
    @Test
    void findAllBean(){
        //AutoAppConfig가 스프링 설정 클래스이고 componentscan이 있어 component가 붙어있는 클래스 빈으로 등록
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountPolicy.class);
        DiscountService discountService = ac.getBean(DiscountService.class);

        //인자를 넣은 멤버 객체 생성
        Member member = new Member(1L, "userA", Grade.VIP);

        //구현체 이름을 파라미터로
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    //DiscountService는 Map으로 모든 DiscountPolicy를 주입받는다
    //@Autowired로 자동의존주입, fixDiscountPolicy rateDiscountPolicy 받음
    static class DiscountService{

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;//할인정책들이 들어있는 배열

        //@Autowired 생성자 하나로 생략 가능,
        //AutoAppConfig의 componentscan으로 component가 붙어있는 DiscountService의 자식 fixDiscountPolicy rateDiscountPolicy
        //map, list에 생성자 통해 들어옴, DiscountPolicy에 ctrl alt b 누르면 들어간거 보임
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountcode) {
            //할인코드 들어오면 그 코드에 맞는 DiscountPolicy 객체 리턴
            DiscountPolicy discountPolicy = policyMap.get(discountcode);

            //DiscountPolicy객체의 discount메서드 인자에 member, price 넣어 결과값 반환
            return discountPolicy.discount(member, price);
        }
    }
}
