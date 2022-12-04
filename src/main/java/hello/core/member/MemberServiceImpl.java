package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//회원가입 인터페이스 구현한 클래스, 기능을 실행만 하는 역할
//의존관계에 대한 고민은 외부에 맡긴다
public class MemberServiceImpl implements MemberService{

    //저장소 객체 생성, 추상화,
    //어떤 구현 객체가 적용될지 결정해주고 구체적인건 AppConfig
    //이전에는 = new 구현체로 구현체에도 의존을 했었음, 무엇이 들어올지 모름
    private final MemberRepository memberRepository;

    //자동 의존 관계 주입, @Component를 쓰면 @Autowired를 쓸 수 밖에 없는
    //수동으로 등록할 수 있는 장소가 없는
    //MemberRepository과 같은 타입의 빈을 찾아 주입

    //이전에 AppConfig에서는 @Bean 으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했다.
    //이제는 이런 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결
    //MemberRepository memberRepository = ac.bean(MemberRepository.class)처럼 비슷한 타입을 찾아 주입
    @Autowired
    //생성자, 파라미터로 memberRepository 주입받아 클래스 필드에 대입
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //오버라이딩한 가입 메서드로
    //저장소 저장메서드를 사용해 member객체 삽입
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
