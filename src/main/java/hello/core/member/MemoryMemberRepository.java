package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
//빈 이름 기본 전략: MemberServiceImpl 클래스 memberServiceImpl
//빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면
//@Component("memberService2") 이런식으로 이름을 부여하면 된다
@Component//memoryMemberRepository
//회원저장소 구현한 클래스
public class MemoryMemberRepository implements MemberRepository{
    //저장소인 맵 객체
    private static Map<Long, Member> store = new HashMap<>();

    //파라미터로 member객체 들어오면 그 아이디를 키로 객체는 값으로 저장
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    //아이디값 키로 객체 가져오는
    @Override
    public Member findById(long memberId) {
        return store.get(memberId);
    }
}
