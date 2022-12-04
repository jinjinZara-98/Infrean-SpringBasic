package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor final이 붙은 필드를 대상으로 생성자를 만들어줌, navigate로 확인 가능
public class OrderServiceImpl implements OrderService{

    //저장소 객체, 할인 정책 객체, 할인 정책 객체는 추상화만 해놓고 구체적인 주입 객체는 모름 그건 Appconfig가
    //final은 무조건 값이 있어야 하는, 생성된게 정해지면 안바뀜. 생성자로 값을 주입해주지 않으면 에러 발생
    //생성자에서만 값을 세팅 할 수 있음. 아니면 초기에 값을 대입하거나
    //생성자 이외에 나머지 주입 방식은 final 사용불가
    //생성자로 값이 안들어온걸 에러로 알려주므로 좋음

    //이렇게 new 구현체 코드가 없는것으로 DIP원칙(의존 역전) 지킴
    //구현체가 아닌 인터페이스에 의존
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //생성자 주입
    //성자 호출시점에 딱 1번만 호출되는 것이 보장된다.
    //불변, 필수 의존관계에 사용

    //OrderServiceImpl가 스프링 빈으로 등록될 때, 등록이 되면 생성자를 호출하므로
    //@Autowired가 있으면 스프링 컨테이너에 빈을 꺼내서 주입
    //@Autowired가 있는 메서드 파라미터에 자동으로 주입

    //이때 하위 타입으로 지정할 수 도 있지만, 하위 타입으로 지정하는 것은 DIP를 위배하고 유연성이 떨어진다.
    //그리고 이름만 다르고, 완전히 똑같은 타입의 스프링 빈이 2개 있을 때 해결이 안된다

    //@Autowired 는 타입 매칭을 시도하고, 이때 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭

    //씨발중요
    //타입이 여러개 조회되면 파라미터 이름을 봄, rateDiscountPolicy
    //@Qualifier("mainDiscountPolicy")와 같은 이름을 가진 것과 매칭, 없다면 스프링 빈 이름을 매칭
    //@Qualifier("mainDiscountPolicy") 대신 @MainDiscountPolicy라는 어노테이션 직접 만들어 값 틀려도 컴파일 오류가 생기지 않은걸 방지하는
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy rateDiscountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = rateDiscountPolicy;
    }

    //주문 생성
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        //id넣어서 member객체 가져오고
        Member member = memberRepository.findById(memberId);

        //가져온 member객체와 물건가격 파라미터로 넘겨 할인가격 알기
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
