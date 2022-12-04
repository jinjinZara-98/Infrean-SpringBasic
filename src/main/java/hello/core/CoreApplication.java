package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//스프링 테스트 할때 사용하는 메서드

//@SpringBootApplication 여기에 @Componentscan이 있음
//@Componentscan처럼 현재 적혀있는 패키지를 스캔하겠다
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
