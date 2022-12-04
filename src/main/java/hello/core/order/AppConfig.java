package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//수동 빈 등록 설정 클래스

//구성영역, 현재 AppConfig가 구성영역 구현체변경은 여기 코드만
//사용영역(기존의 클라이언트 코드) 코드 손대지않고 구성영역 코드만 변경해 확장, 개방폐쇄?
//어플리케이션 전체를 구성하고 설정, 실제 동작에 필요한 구현객체 생성하고 연결, 역할 분리
//기획자 역할, 출연자들인 구현 객체들 모두 알아야함, 변경할게 생기면 이 구성클래스 안의 코드만 변경하면됨,
//즉 주입하는 객체만 팩토리 메서드로 등록하는 방법
//@Configuration없이도 스프링 빈으로 등록되지만 싱글톤은 보장하지 않음
@Configuration
public class AppConfig {

    //ConfigurationSingletonTest 결과
    //예상
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.memberRepository
    //orderService
    //call AppConfig.memberRepository

    //결과
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //orderService

    //@Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고,
    //스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
    //덕분에 싱글톤이 보장되는 것이다
    //@Configuration 안붙이면 예상했던 결과 나옴, 매번 new로 만들어 주입
    //스프링 컨테이너가 관리하는 빈이 아니게 되는

    //MemberService타입의 memberService메서드,
    //이 메서드 호출하면 MemoryMemberRepository 객체를 파라미터로 넣은 MemberServiceImpl 객체 반환
    //생성자 주입, 주사기로 넣듯이
    //외부에서 메서드를 호출해 생성되는 방식
    @Bean
    public MemberService memberService(){

        //초기화 될 떄, 빈 등록될 때 호출되면서 출력되는
        return new MemberServiceImpl( memberRepository() );
    }

    //공통로직 메서드로, 중복이 제거되었으니 구현체를 바꿀 때 이 로직만 변경
    //위에 MemberServiceImpl안 파라미터애서 ctrl alt m으로 생성
    @Bean
    public MemberRepository memberRepository() {

        return new MemoryMemberRepository();
    }

    //OrderService타입의 orderService메서드
    //호출하면 MemoryMemberRepository 객체와 FixDiscountPolicy 객체를 파라미터로 가진 OrderServiceImpl객체 반환환
    @Bean
    public OrderService orderService(){

        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    //메서드로 빼주기, 메서드명으로 역할을 다 드러나게
   @Bean
    public DiscountPolicy discountPolicy(){

        return new RateDiscountPolicy();
    }

    //구체적인 역할을 분리해놓음, 전체 구성 빠르게 파악
    //memberService memberRepository orderService 각각 한번씩 호출
    //@Configuration 지우면 memberRepository 3번 호출됨
    //매번 호출할때마다 새로운 객체 생성해, 싱글톤이 아닌
}
