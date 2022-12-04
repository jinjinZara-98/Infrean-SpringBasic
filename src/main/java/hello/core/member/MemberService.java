package hello.core.member;

//회원가입 인터페이스
public interface MemberService {

    void join(Member member);//회원가입 메서드

    Member findMember(Long memberId);
}
