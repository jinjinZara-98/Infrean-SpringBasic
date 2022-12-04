package hello.core.scan.filter;

import java.lang.annotation.*;

//annotation으로 생성

//@component에서 가져옴
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
