package hello.core.scan.filter;

//annotation으로 생성

import java.lang.annotation.*;

//@component에서 가져옴
//Type은 클래스레벨에 붙는
//컴포넌트스캔에 탐지되는
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {

}
