package mvp.presenter;

import mvp.model.DAO;
import mvp.view.ViewInterface;
import two_three.Client;

import java.util.Comparator;
import java.util.List;

public abstract class Presenter<T> {
    protected DAO<T> model;
    protected ViewInterface<T> view;
    protected Comparator<T> cmp;

    public Presenter(DAO<T> model, ViewInterface<T> view,Comparator<T> cmp) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
        this.cmp=cmp;
    }

    public void start() {
        view.setListDatas(getAll(),cmp);
    }

    public List<T> getAll(){
        List<T> l = model.getAll();
        l.sort(cmp);
        return l;
    }

    public void add(T elt) {
        T nelt = model.add(elt);
        if(nelt!=null) view.affMsg("création de :"+nelt);
        else view.affMsg("erreur de création");
    }

    public void remove(T elt) {
        boolean ok = model.remove(elt);
        if(ok) view.affMsg("élément effacé");
        else view.affMsg("élément non effacé");
    }

    public void update(T elt) {
        T nelt  =model.update(elt);
        if(nelt==null) view.affMsg("mise à jour infrucueuse");
        else view.affMsg("mise à jour effectuée : "+nelt);
    }

    public T read(T rech) {
        T elt= model.read(rech);
        if(elt==null) view.affMsg("recherche infructueuse");
        else view.affMsg(elt.toString());

        return elt;
    }

    public T select(){
        return  view.select(model.getAll());
    }




}
