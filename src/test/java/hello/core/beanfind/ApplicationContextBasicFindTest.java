package hello.core.beanfind;

import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        //빈으로 등록한 메서드의 반환값이 MemberServiceImpl이니
        //memberService가 MemberServiceImpl의 인스턴스면 성공
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    //인터페이스로 조회하면 구현체가 대상이 됨
    @Test
    @DisplayName("인터페이스 타입으로 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        //memberService가 MemberServiceImpl의 인스턴스면 성공
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    //타입을 구현체로 적어줘도됨
    //역할에 의존하는게 맞는 것이므로 좋은 코드는 아님, 구현체에 의존하기 때문에
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        //memberService가 MemberServiceImpl의 인스턴스면 성공
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByNameX(){
        //ac.getBean("xxxxx",MemberService.class) 이 코드를 실행할때
        // NoSuchBeanDefinitionException 이 예외가 터져야 테스트 성공
        assertThrows(NoSuchBeanDefinitionException.class, () ->
                ac.getBean("xxxxx",MemberService.class));
    }
}
