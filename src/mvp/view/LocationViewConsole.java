package mvp.view;

import mvp.presenter.AdressePresenter;
import mvp.presenter.LocationPresenter;
import two_three.Location;

import java.util.List;
import java.util.Scanner;

public class LocationViewConsole implements LocationViewInterface{
    private LocationPresenter presenter;
    private List<Location> locations;
    private Scanner sc = new Scanner(System.in);
    @Override
    public void setPresenter(LocationPresenter presenter) { this.presenter = presenter; }

    @Override
    public void setListDatas(List<Location> locations) {
        this.locations = locations;
        menu();
    }

    @Override
    public void affMsg(String msg) { System.out.println("Information : " +  msg); }

    public void menu(){
        //TODO menu view Location
    }

}
