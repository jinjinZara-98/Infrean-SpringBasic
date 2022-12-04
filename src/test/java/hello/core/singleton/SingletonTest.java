package hello.core.singleton;

import hello.core.member.MemberService;
import hello.core.order.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//싱글톤 패턴 문제점
//싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
//의존관계상 클라이언트가 구체 클래스에 의존한다. DIP를 위반한다.
//클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
//테스트하기 어렵다.
//내부 속성을 변경하거나 초기화 하기 어렵다.
//private 생성자로 자식 클래스를 만들기 어렵다.
//결론적으로 유연성이 떨어진다.
//안티패턴으로 불리기도 한다

//스프링은 이런 단점을 제거하면서 싱글톤 관리

//웹어플리케이션으로 주로 개발되는 스프링이 순수 DI컨테이너 Appconfig가 요청을 할때마다 객체가 새로 생성되는 비효율적임을 보여주는
//이 점을 해결하기 위해 해당 객체가 1개만 생성되 공유하도록 설계 이게 싱글톤
//스프링컨테이너는 기본적으로 싱글톤으로 만들어 관리
//@Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈 반환하고
// 스프링 빈 없으면 생성해 스프링빈으로 등록해 반환
public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        //조회할때마다 객체 생성
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른지 확인
        System.out.println("MemberService1 = " + memberService1);
        System.out.println("MemberService2 = " + memberService2);

        //isSameAs 는 ==
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        //SingletonService.getInstance(); ctrl alt v
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //같은 객체
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        //참조값이 다른지 확인
        System.out.println("MemberService1 = " + memberService1);
        System.out.println("MemberService2 = " + memberService2);

        //같은 객체인지
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
