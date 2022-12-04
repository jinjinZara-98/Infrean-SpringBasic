package hello.core.beanfind;

import hello.core.order.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        //getBeanDefinitionNames 스프링에 등록된 모든 빈 이름 조회
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        //키
        for (String beanDefinitionName : beanDefinitionNames) {
            //값
            Object bean = ac.getBean(beanDefinitionName);
            //키와 값
            System.out.println("name = " + beanDefinitionName + "object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {

            //getBeanDefinition은 빈 하나하나에 대한 정보들
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            //ROLE_APPLICATION 직접 등록한 빈
            //ROLE_INFRASTRUCTURE 스프링이 내부에서 사용하는 빈
            //beanDefinition객체의 역할이 어플리케이션을 위주로 개발한 빈들을 등록한 것이면
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionName);
                //키와 값
                System.out.println("name = " + beanDefinitionName + "  object = " + bean);
            }
        }
    }
}
