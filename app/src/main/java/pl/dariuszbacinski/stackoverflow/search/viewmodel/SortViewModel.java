package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import java.util.List;

import pl.dariuszbacinski.stackoverflow.R;
import pl.dariuszbacinski.stackoverflow.search.model.Sort;
import rx.Observable;
import rx.functions.Func1;

public class SortViewModel implements FilterDisplay{

    Sort sort;
    List<String> values;

    public SortViewModel(Sort sort) {
        this.sort = sort;
        this.values = Observable.from(Sort.values()).map(new Func1<Sort, String>() {
            @Override
            public String call(Sort order) {
                return order.toString();
            }
        }).toList().toBlocking().single();
    }

    @Override
    public int filterNameRes() {
        return R.string.sort;
    }

    @Override
    public String filterSelected() {
        return sort.toString();
    }

    @Override
    public List<String> filterValues() {
        return values;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }
}
