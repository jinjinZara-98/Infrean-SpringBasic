package hello.core.scan;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.AutoAppConfig;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberServiceImpl memberService = ac.getBean(MemberServiceImpl.class);

        MemberRepository memberRepository = memberService.getMemberRepository();

        MemberRepository memberRepository2 = ac.getBean(MemberRepository.class);

        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memberRepository2 = " + memberRepository2);
    }
}
