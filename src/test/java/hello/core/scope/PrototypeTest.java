package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 빈 스코프란?
 *
 * 지금까지 우리는 스프링 빈이 스프링 컨테이너의 시작과 함께 생성되어서 스프링 컨테이너가 종료될 때 까지 유지된다고 학습했다.
 * 이것은 스프링 빈이 기본적으로 싱글톤 스코프로 생성되기 때문이다.
 * 스코프는 번역 그대로 빈이 존재할 수 있는 범위를 뜻한다.
 * 스프링은 다음과 같은 다양한 스코프를 지원한다.
 *
 * 싱글톤: 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프이다.
 * 프로토타입: 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 매우 짧은 범위의 스코프이다.
 *
 * 웹 관련 스코프
 * request: 웹 요청이 들어오고 나갈때 까지 유지되는 스코프이다.
 * session: 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프이다.
 * application: 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프이다.
 * 빈 스코프는 다음과 같이 지정할 수 있다.*/
public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        //@Component @Bean 안붙여줘도 PrototypeBean를 파라미터에 넣어주면 스캔 대상이 됨
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();
    }


    /**
     * 스프링 컨테이너에서 빈을 조회할 떄 생성되고 초기화 메서드도 실행
     * 스프링 컨테이너가 생성과 의존관계 주입, 초기화까지만 관여, 더는 관리하지 않음
     * 따라서 스프링 컨테이너 종료될때 @PreDestroy같은 종료 메서드 실행되지 않음
     */
    @Scope("prototype")
    static class PrototypeBean {

        //빈 생성, 의존관계 주입 끝난 후 실행
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        //빈 소멸 전 실행, 호출 안됨
        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destory");
        }
    }
}
