package mvp.view;

import mvp.presenter.Presenter;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.affListe;
import static utilitaires.Utilitaire.choixElt;

public abstract class AbstractViewConsole<T> implements ViewInterface<T> {

    protected Presenter<T> presenter;
    protected List<T> ldatas;
    protected Scanner sc = new Scanner(System.in);


    @Override
    public void setPresenter(Presenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<T> ldatas, Comparator<T> cmp) {
        this.ldatas = ldatas;
        this.ldatas.sort(cmp);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:" + msg);
    }

    public void menu(){
        List<String> options = List.of("Ajouter", "Retirer", "Rechercher","Modifier","Special","Retour");
        do {
            affListe(options);
            int choix = choixElt(options);

            switch (choix) {
                case 1:
                    add();
                    break;
                case 2:
                    remove();
                    break;
                case 3:
                    seek();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    special();
                    break;
                case 6:
                    return;
            }
        } while (true);
    }

    protected abstract  void add();

    protected void remove() {
        int choix = choixElt(ldatas);
        T elt= ldatas.get(choix-1);
        presenter.remove(elt);
        ldatas=presenter.getAll();//rafraichissement
        affListe(ldatas);
    }

    protected abstract void seek();

    protected  abstract void update();
    protected abstract void special();

    @Override
    public T select(List<T> l) {
        affListe(l);
        int nl = choixElt(l);
        T elt = l.get(nl - 1);
        return elt;
    }


}
