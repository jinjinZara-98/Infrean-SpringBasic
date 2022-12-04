package hello.core.singleton;


public class SingletonService {

    //1. static 영역에 객체 instance를 미리 하나 생성해서 올려둔다.
    //2. 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다. 이 메서드를
    //호출하면 항상 같은 인스턴스를 반환한다.
    //3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 혹시라도 외부에서 new
    //키워드로 객체 인스턴스가 생성되는 것을 막는다

    //자기 자신을 내부에 pricate으로 가지고 있음. static을 붙여 하나만 만들어져 올라감
    //자바 jvm이 뜰때 static 영역에 new 되어있네 하고 내부적으로 실행해 자기 자신의 객체 생성하여 instatnce에 참조해 넣어줌
    private static final SingletonService instance = new SingletonService();

    //instance의 참조를 꺼낼 수 있는 유일한 방법, 객체 instance가 필요하면 오직 이 메서드를 통해 조회회
    //호출할때마다 객체를 계속 생성하는게 아닌 이미 만들어진 객체만 계속 반환
    public static SingletonService getInstance() {
       return instance;
    }

    //다른 클래스에서 이 클래스 객체 생성하지 못하게
    //결국 처음 프로그램 실행할 때 생성된 객체 instance 말고는 다른 객체 생성 못함
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
