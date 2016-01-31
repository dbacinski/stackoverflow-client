package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import java.util.List;

import pl.dariuszbacinski.stackoverflow.R;
import pl.dariuszbacinski.stackoverflow.search.model.Order;
import rx.Observable;
import rx.functions.Func1;

public class OrderViewModel implements FilterDisplay{

    Order order;
    List<String> values;

    public OrderViewModel(Order order) {
        this.order = order;
        this.values = Observable.from(Order.values()).map(new Func1<Order, String>() {
            @Override
            public String call(Order order) {
                return order.toString();
            }
        }).toList().toBlocking().single();
    }

    @Override
    public int filterNameRes() {
        return R.string.order;
    }

    @Override
    public String filterSelected() {
        return order.toString();
    }

    @Override
    public List<String> filterValues() {
        return values;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
