package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        //ConfigurableApplicationContext는 ApplicationContext와 AnnotationConfigApplicationContext사이에 있는
        //close()를 제공해주는 클래스를 사용하기 위해
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifecycleConfig.class);
        //스프링 설정 클래스 객체에서 NetworkClient 타입의 빈 갖고옴
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    //스프링 설정 클래스
    @Configuration
    static class LifecycleConfig {

        //스프링 빈은 객체를 생성하고 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료

        //설정 정보에 초기화 메서드, 종료 메서드 지정
        //initMethod, destroyMethod 초기화, 종료 동시에 하는 빈
        //인터페이스와 달리 메서드 이름 자유롭게 줄 수 있고, 스프링 빈이 스프링 코드에 의존하지 않는다
        //코드가 아닌 설정 정보를사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드 적용 가능
        //@Bean(initMethod = "init", destroyMethod = "close")

        //종료 메서드 추론
        //@Bean의 destroyMethod 속성에는 아주 특별한 기능이 있다.
        //라이브러리는 대부분 close , shutdown 이라는 이름의 종료 메서드를 사용한다.
        //@Bean의 destroyMethod 는 기본값이 (inferred) (추론)으로 등록되어 있다.
        //이 추론 기능은 close , shutdown 라는 이름의 메서드를 자동으로 호출해준다. 이름 그대로 종료
        //메서드를 추론해서 호출해준다.
        //따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작한다.
        //추론 기능을 사용하기 싫으면 destroyMethod="" 처럼 빈 공백을 지정하면 된다.

        @Bean
        //NetworkClient타입의 메서드 NetworkClient 객체 생성해 url 세팅하고 객체 반환
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring_dey");
            return networkClient;
        }
    }
}
