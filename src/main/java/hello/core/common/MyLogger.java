package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//이 빈은 HTPP 요청당 하나씩 생성, 요청 끝나는 시점에 소멸
//ScopedProxyMode.TARGET_CLASS CGLIB라는 바이트코드를 조작하는 라이브러리를 사용해
//MyLogger를 상속받은 가짜 프록시 객체를 생성
//HTTPREQUEST와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.
//provider쓰는거처럼 동작
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)//적용 대상 클래스
public class MyLogger {

    private String uuid;
    private String requestURl;

    //requestURl은 이 빈이 생성되는 시점에 알 수 없으므로 외부에서 세터로 입력받음
    public void setRequestURl(String requestURl) {
        this.requestURl = requestURl;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURl + "]" + message);
    }

    //초기화, 고객 요청이 들어올때 호출
    @PostConstruct
    public void init() {
        //글로벌하게 유니크한 아이디 생성, 겹치지 않음
        //빈이 생성되는 시점에 자동으로 @PostConstruct 초기화 메서드 사용해 uuid 생성해 저장
        //HTTP요청 하나씩 생성되므로 uuid 저장해두면 다른 HTTP요청과 구분이 가능
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] requst scope bean create" + this);
    }

    //종료, 프로토타입과 달리 request는 종료 메서드 호출됨
    //고객 요청이 서버에서 빠져나가면 호출
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] requst scope bean close" + this);
    }
}
