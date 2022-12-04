package hello.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {
    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        //alt enter해서 static import
        assertThat(beanA).isNotNull();

        //MyexcludeComponent로 해서 컴포넌트 대상에서 빠져버림
        //BeanB beanB = ac.getBean("beanB", BeanB.class);
        //NoSuchBeanDefinitionException은 위 문장 컴파일 오류 예외 클래스
        //jupiter.api.Assertions
        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));
    }

    //FilterType은 5가지 옵션이 있다.

    //ANNOTATION: 기본값, 애노테이션을 인식해서 동작한다.
    //ex) org.example.SomeAnnotation

    //ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다.
    //ex) org.example.SomeClass

    //ASPECTJ: AspectJ 패턴 사용
    //ex) org.example..*Service+

    //REGEX: 정규 표현식
    //ex) org\.example\.Default.*

    //CUSTOM: TypeFilter 이라는 인터페이스를 구현해서 처리
    //ex) org.example.MyTypeFilter

    @Configuration
    @ComponentScan(
            //스캔 범위 지정안해서 package hello.core.scan.filter 안에 다 스캔하나?

            //포함하는 필터
            //@ComponentScan에서 alt enter해서 static import
            //@MyIncludeComponent 붙어있는 클래스인 BeanA 스캔대상에 포함
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),

            //제외하는 필터
            //@MyExcludeComponent 붙어있는 클래스인 BeanB 스캔대상에 제외
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {

    }
}
