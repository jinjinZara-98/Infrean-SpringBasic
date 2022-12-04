package hello.core.member;

import hello.core.order.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//테스트코드 오류캐치 빨리 가능
public class MemberServiceTest {

    MemberService memberService;

    //각 테스트 실행하기 전 무조건 실행됨
    @BeforeEach
    public void beforeEach() {
        AppConfig appconfig = new AppConfig();
        //MemoryMemberRepository를 주입받은 MemberServiceImpl를 반환해주는
        memberService = appconfig.memberService();
    }
    @Test
    void join(){
        //given 주어졌을때
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when 했을때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then 된다
        //검증, 똑같냐
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
