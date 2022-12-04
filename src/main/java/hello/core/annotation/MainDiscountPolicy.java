package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

//필드 메서드 클래스에 다 붙일수 있는
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
//그냥 @Qualifier 쓰면 안에 값을 잘못 써도 컴파일 오류 안나니 이게 더 유용
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}
