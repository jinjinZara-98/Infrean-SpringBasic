package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

//직접 스프링 빈을 등록하는
//AppConfig가 팩토리 메서드를 통해 등록하는
//AnnotationContext를 구현한것중 AnnotationConfigApplicationContext 아닌 GenericXmlApplicationContext를
public class XmlAppContext {

    @Test
    void xmlAppContext() {
        //설정정보가 클래스파일이 아닌 xml파일로 바뀐
        //xml기반의 appConfig.xml 스프링 설정정보와 자바 크드로 된 Appcinfig.java 설정 정보를 비교해보면 거의 비슷
        //xml 현재 잘 사용 안함
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        //Assertions에 alt enter import
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
