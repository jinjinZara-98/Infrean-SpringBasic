package hello.core.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//lombok을 이용해 겟터 셋터 자동으로 만들어 주는
@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        //겟터 셋터 메서드 만들어주지 않고 바로 사용
        helloLombok.setName("허진");

        String name = helloLombok.getName();
        System.out.println("name = " + name);
        System.out.println("helloLombok = " + helloLombok);
    }
}
