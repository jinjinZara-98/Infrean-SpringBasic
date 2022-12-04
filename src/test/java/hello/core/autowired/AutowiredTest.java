package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {
        //@Autowired(required = false) 자동주입할 대상이 없으면 메서드 자체가 호출 안됨
        //Member클래스는 스프링이 관리하는 클래스가 아님 빈으로 등록되어 있지 않음
        //밑에 2개는 출력되도 이건 출력 안됨
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired
        //@Nullable은 자동주입할 대상이 없으면 null 입력됨
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        }

        //Optional<>자동 주입할 대상 없으면 Optional.empty 입력됨
        @Autowired(required = false)
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
//@Nullable, Optional은 스프링 전반에 걸쳐서 지원된다.
//예를 들어서 생성자 자동 주입에서 특정 필드에만 사용해도 된다
