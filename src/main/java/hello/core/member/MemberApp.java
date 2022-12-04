package hello.core.member;

import hello.core.order.AppConfig;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//        //MemoryMemberRepository 객체를 파라미터로 넣은 MemberServiceImpl 객체 반환해 대입
//        MemberService memberService = appConfig.memberService();

        //ApplicationContext가 스프링컨테이너
        //저장소 객체 생성. 빈으로 등록한 것들 다 관리
        //AnnotationConfigApplicationContext는 ApplicationContext인터페이스를 적용한 클래스
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //빈이름은 메서드 이름,
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        //new 객체 생성후 ctrl alt v하면 타입 객체 생성됨
        //인자를 넣어준 객체 생성후
        Member member = new Member(1L, "memberA", Grade.VIP);

        //회원가입객체 메서드로 객체 파라미터로 넣어 가입
        memberService.join(member);

        //방급 넣어준 아이디로 조회한 객체 갖고옴
        Member findMember = memberService.findMember(1L);
        System.out.println("new member =" + findMember);
        System.out.println("findMember =" + findMember);
    }
}
