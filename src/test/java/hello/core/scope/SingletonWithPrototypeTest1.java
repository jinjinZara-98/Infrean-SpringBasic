package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        //타입으로 찾기
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        /**싱글톤이 아니여서 필드를 공유하지 않기 때문에 값 증가시켜도 1임 */
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUserPrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        /**생성 시점에 주입된걸 다시 씀 */
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
    }

    //위에 인자로 넣어주기 때문에 자동으로 스프링빈으로 등록
    @Scope("singleton")
    static class ClientBean {

        //private final PrototypeBean prototypeBean;//생성 시점에 주입

        /**
         * ObjectProvider는 ObjectFactory 상속받고 있음, 스프링 외에 별도의 의존관계 추가가 필요 없어 편리
         * 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공하는 것
         */
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {

            /**
             * getObject() 스프링 컨테이너에서 프로토타입빈을 찾아서 반환 (DL),
             * 항상 새로운 프로토타입 빈 생성 찾아주는걸 도와주는 대신 조회해주는
             * 의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을
             * Dependency Lookup (DL) 의존관계 조회(탐색)
             * AnnotationConfigApplicationContext에서 찾는게 아니라 찾아주는 기능만 제공
             * 필요할때마다 스프링 컨테이너에 요청하는 기능 사용 가능, 대신 조회하는 대리자 정도
             *
             * Provider java.inject는 getObject() 아니라 get, 똑같이 새로운 프로토타입 빈 생성
             * 딱 필요한 정도의 Dl기능 제공,
             * 대신 별도의 라이브러리가 필요하고 자바 표준이어서 스프링이 아닌 다른 컨테이너에서 사용 가능
             * 프로토타입 뿐만 아니라 Dl이 필요한 경우 언제든지 사용 가능
             */
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();//이미 생성 시점에 주입된 prototypeBean
            int count = prototypeBean.getCount();//그래서 2 반환

            return count;
       }
    }

    //위에 인자로 넣어주기 때문에 자동으로 스프링빈으로 등록
    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }


        /**
         * 스프링 컨테이너에서 빈을 조회할 떄 생성되고 초기화 메서드도 실행
         * 스프링 컨테이너가 생성과 의존관계 주입, 초기화까지만 관여 더는 관리하지 않음
         * 따라서 스프링 컨테이너 종료될때 @PreDestroy같은 종료 메서드 실행되지 않음
         */
        @PostConstruct
        public void init() {
            //this 자기 자신 참조값
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destory");
        }
    }
}
