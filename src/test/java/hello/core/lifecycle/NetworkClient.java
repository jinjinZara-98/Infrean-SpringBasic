package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.DirectoryStream;

//빈 생명주기 콜백 시작
//데이터베이스 커넥션 풀이나, 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고,
//애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면, 객체의 초기화와 종료 작업이 필요하다.
//이번시간에는 스프링을 통해 이러한 초기화 작업과 종료 작업을 어떻게 진행하는지 예제로 알아보자.
//간단하게 외부 네트워크에 미리 연결하는 객체를 하나 생성한다고 가정해보자. 실제로 네트워크에 연결하는
//것은 아니고, 단순히 문자만 출력하도록 했다. 이 NetworkClient 는 애플리케이션 시작 시점에
//connect() 를 호출해서 연결을 맺어두어야 하고, 애플리케이션이 종료되면 disConnect() 를 호출해서
//연결을 끊어야 한다

//InitializingBean 초기화빈 DisposableBean 종료빈, 인터페이스 초기화 종료는 현재 거의 사용안함
//public class  NetworkClient implements InitializingBean, DisposableBean{
public class  NetworkClient {
    private String url;

    //생성자
    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    //url 외부에서 넣을 수 있게 세터
    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출, 세터로 들어온 url 출력
    public void connect(){
        System.out.println("connect: " + url);
    }

    //연결했을때 부르는, 연결할 서버에 메시지 전달
    public void call(String message) {
        //어떤 url에 call했고 메시지는 무엇인지
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출출
    public void disconnect(){
        System.out.println("close: " + url);
    }

    //의존관계주입이 끝나면 호출, 생성과 의존주입이 다 끝난 후 호출
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    //빈이 종료 될때 호출
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }

    //초기화, 소멸 인터페이스 단점
    //이 인터페이스는 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
    //초기화, 소멸 메서드의 이름을 변경할 수 없다.
    //내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다
    //거의 사용 안함

    //의존관계 주입이 끝나면 호출
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient afterPropertiesSet()");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    //이 빈이 종료될 떄 호출
//    //안전하게 호출이 되면 스프링이 내려가는
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient destroy");
//        disconnect();
//    }

    //코드를 고칠 수 없는 외부 라이브러리를 초기화, 종료해야 하면
    //@Bean 의 initMethod , destroyMethod 를 사용
}

