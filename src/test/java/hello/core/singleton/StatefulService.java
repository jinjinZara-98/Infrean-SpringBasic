package hello.core.singleton;

//싱긅톤은 무상태로 설계 해야함
public class StatefulService {

    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);
        this.price = price;
    }

    //공유되는 필드가 변경되지 않게 가격을 반환, 필드 주석처리하고
    public int order2(String name, int price) {
        System.out.println("name = " + name + "price = " + price);

        return price;
    }

    public int getPrice() {
        return price;
    }
}
