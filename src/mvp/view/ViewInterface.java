package mvp.view;

import mvp.presenter.Presenter;

import java.util.Comparator;
import java.util.List;

public interface ViewInterface<T> {
    public void setPresenter(Presenter<T> presenter);

    public void setListDatas(List<T> datas, Comparator<T> cmp);

    public void affMsg(String msg);


    public T select(List<T> l);
}
