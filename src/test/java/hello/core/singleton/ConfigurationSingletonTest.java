package hello.core.singleton;

import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.AppConfig;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    //appconfig에 @configuration을 존재하지 않으면 memberService와 orderService가 memberRepository 참조하는 값이 달라짐
    //존재하면 값이 다 똑같음 같은 객체를 공유하므로
    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        //둘 다 MemberRepository구현체 주입받고 있으니 가져와보기
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        //appconfig에 @configuration 없으면 memberRepository 서로 값이 다 다르게 나옴
        System.out.println("memberService ->memberRepository = " + memberRepository1);
        System.out.println("orderService ->memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //AnnotationConfigApplicationContext(AppConfig.class)로 넘기면 AppConfig도 스프링빈으로 등록됨
        AppConfig bean = ac.getBean(AppConfig.class);

        //순수한 클래스를 원래 실행하면 hello.core.order.AppConfig 이렇게 나옴
        //스프링은 Appconfig가 아닌 조작한 클래스의 자식 클래스 AppConfig@CGLIB를 만들어 스프링 빈으로 등록
        //스프링 컨테이너에 이름은 Appconfig인데 인스턴스는 AppConfig@CGLIB로 되어있는
        //@configuration을 지우고 실행하면 Appconfig나옴,
        //내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서
        //AppConfig클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것
        //그 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해준다.

        //@Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고,
        //스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
        //덕분에 싱글톤이 보장되는 것이다

        //bean = class hello.core.order.AppConfig$$EnhancerBySpringCGLIB$$24a8d040
        //bean = class hello.core.order.AppConfig
        System.out.println("bean = " + bean.getClass());
    }
}
