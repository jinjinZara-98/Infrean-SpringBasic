package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderServiceImplTest {

    @Test
    void createOrder(){
        MemberRepository memberRepository = new MemoryMemberRepository();

        //생성한 회원 객체를 save메서드로 memberRepository에 저장
        memberRepository.save( new Member(1L,"name", Grade.VIP) );

        //이렇게 생성자로 객체를 만들면 인자가 뭐가 필요한지 인지 가능
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());

        //주문을 한 메서드의 결과를 order 객체에 대입
        Order order = orderService.createOrder(1L,"itemA",10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
