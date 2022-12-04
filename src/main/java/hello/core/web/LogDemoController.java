package hello.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //@Autowired 있는거나 다름없음
    //MyLogger타입의 변수를 사용하는게 아닌 MyLogger타입의 Provider를 사용하면
    //MyLogger를 주입받는게 아니라 찾을 수 있는 depengency lookup 할 수 있는 애가 주입되는
    //주입 시점에 주입 받을 수 있음
    //private final ObjectProvider<MyLogger> myLoggerProvider;

    //실제 컨트롤러 고객 요청이 와서 http가 살아있는 상태에서 스코프를 사용 할 수 있는 상태가 됨
    //ObjectProvider 덕분에 ObjectProvider.getObject() 를 호출하는 시점까지 request scope 빈의 생성을 지연
    //정확한 표현으로는 스프링 컨테이너한테 요청하는걸 지연
    //그냥 MyLogger하면 http요청하기도 전에 바로 요청해버려서 에러 뜨지만 이걸 사용함으로써

    //진짜 MyLogger가 아님. 껍데기 가짜임. 실제 기능을 호출하는 시점에 진짜를 찾아 동작. provider처럼
    //프록시든 provider이든 진짜 객체 조회를 꼭 필요한 시점까지 지연처리 한다는게 핵심
    //가짜 프록시 객체는 원본 클래스를 상속 받아서 만들어졌기 때문에
    //이 객체를 사용하는 클라이언트 입장에서 원본인지 아닌지 모르게 동일하게 사용 가능, 다형성
    //애노테이션 설정 변경만으로 원본 객체를 프록시 객체로 대체 가능. 이것이 큰 강점
    //private final MyLogger myLogger;

    @RequestMapping("Log-demo")
    @ResponseBody
    //자바에서 제공하는 표준 서블릿 규악, HTTP Request정보를 받을 수 있음. 고객 요청 정보
    public String logDemo(HttpServletRequest request) throws InterruptedException {

        //HttpServletRequest을 통해 요청url받음, 고객이 어떤 url로 요청했는지 알기 위해
        //프록시는 기능을 사용할 떄 진짜 객체를 찾아 갖고옴
        String requestURl = request.getRequestURL().toString();

        //진짜 필요한 시점에 MyLogger 받을 수 있음
//        MyLogger myLogger = myLoggerProvider.getObject();
        //myLogger는 requestscope. http://localhost:8080/log-demo 남기기 위해
        //어디서든 로그를 찍어도 http://localhost:8080/log-demo 같이 찍힘
        //myLogger는 각각 구분되므로 HTTP요청 때문에 값이 섞이는 걱정은하지 않아도 된다

        //requestURl을 MyLogger에 저장하는 부분은 컨트롤러 보다는
        //공통 처리가 가능한 스프링 인터셉터나 서블릿필터 같은 곳을 활용하는 것이 좋다.
        //인터셉터는 HTTP Request요청을 컨트롤러 호출 직전에 공통화해서 처리 할 수 있는
//        System.out.println("myLogger = " + myLogger.getClass());
//        myLogger.setRequestURl(requestURl);

        //url담고 로그찍기
//        myLogger.log("controller test");
        Thread.sleep(100);
        logDemoService.logic("testId");

        return "OK";
    }
 }
