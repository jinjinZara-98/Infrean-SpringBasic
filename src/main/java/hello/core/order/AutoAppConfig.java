package hello.core.order;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//자동 빈 등록 설정 클래스

@Configuration
//@Component가 붙은 클래스를 스캔해서 스프링빈에 등록
//클래스 이름의 앞글자를 소문자로 바꿔 빈 이름으로 등록
//이름을 직접 지정하고 싶으면 @Component("이름")
@ComponentScan(
        //스캔 대상 패키지 설정, 설정한 패키지 포함해 하위 패키지 모두 탐색
        //웬만하면 스캔 대상 지정해줘야 안그럼 모든 패키지 라이브러리 다 탐색해 시간 오래 걸림
        //배열로 여러개 지정 가능, 지정하지 않으면 hello.core.order와 그 하위패키지 뒤짐
        //때문에 보편적으로 설정 정보 클래스의 위치를 프로젝트 최상단에 둠
        //이러면 하위 패키지와 클래스들은 컴포넌트 스캔의 대상이 됨
        basePackages = "hello.core.member",
        //basePackageClasses = AutoAppConfig.class 클래스 직접 지정가능

        //제외 대상 설정, @Configuration은 수동으로 등록하기 때문에
        //AopConfig 제외하는, @Configuration 붙어있으니
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
