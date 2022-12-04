package hello.core.order;

//주문 결과를 반환하는 인터페이스
public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
