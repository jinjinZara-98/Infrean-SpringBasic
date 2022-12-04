package hello.core.beandefinition;

import hello.core.order.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

//이 클래스 실행해서 나온 결과로 싱글톤인지 다른 설정이 적용되었는지 확인 가능
public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    //bean에 대한 정보가 명확하게 담겨잇음
    //GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xnl");

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        //키 값들 다 받아오는
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        //키
        for (String beanDefinitionName : beanDefinitionNames) {
            //ac.getBeanDefinition(beanDefinitionName) ctrl alt v
            //값
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                //soutv
                System.out.println("beanDefinitionName = " + beanDefinition +
                                "beanDefinition = " + beanDefinition);
            }
        }
    }
}
