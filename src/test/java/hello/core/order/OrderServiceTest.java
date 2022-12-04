package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//단위테스트, 스프링이나 컨테이너 도움없이 순수 자바코드로
public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appconfig = new AppConfig();
        memberService = appconfig.memberService();
        orderService = appconfig.orderService();
    }

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    @Test
    void fieldInjectionTest() {
//        OrderServiceImpl orderService = new OrderServiceImpl();
        
//        orderService.setMemberRepository(new MemoryMemberRepository());
//        orderService.setDiscountPolicy(new FixDiscountPolicy());

        orderService.createOrder(1L, "itemA", 10000);
    }
}
