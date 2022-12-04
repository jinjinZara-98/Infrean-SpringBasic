package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    void singletonBeanFind() {
        //SingletonBean.class는 컴포넌트 클래스, 인자로 넣어주면 자동으로 컴포넌트스캔이 읽음
        //그러므로 바로 스프링 빈으로 등록해버림
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        //타입 조회
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);

        //밑에 출력 전 빈 생성, 의존관계 주입이 끝난 직후 바로 실행되는 init()이 더 먼저 실행
        //싱글톤으로 만든 빈 클래스에서 객체 생성을 해 같은 객체인지 비교
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);
        assertThat(singletonBean1).isSameAs(singletonBean2);

        //스프링 컨테이너 닫기? 빈 소멸?
        ac.close();
        System.out.println("여기서 부터는 스프링 컨테이너 닫힌 후 실행한거니 destroy() 출력되고 출력됨");
    }


    /**
     * 싱글톤으로 만들때, 스프링 컨테이너 생성 시점에 초기화 메서드 실행
     * 스프링 컨테이너가 관리하기 때문에 스프링 컨테이너가 종료될때 빈의 종료 메서드가 실행
     */
    @Scope("singleton")
    static class SingletonBean {

        //빈 생성, 의존관계 주입 후 실행
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        //빈 소멸 전 실행
        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destory");
        }
    }
}
