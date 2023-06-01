package designpattern.composite;

public class gestComposite {
    public static void main(String[] args) {
        Taxi tOne = new Taxi(1,"TX-ABC-001",2,"essence",1.2);
        Taxi tTwo = new Taxi(2,"TX-ABC-002",4,"essence",1.2);
        Taxi tThree = new Taxi(3,"TX-ABC-003",6,"essence",1.2);
        Taxi tFour = new Taxi(4,"TX-ABC-004",8,"essence",1.2);
        Taxi tFive = new Taxi(5,"TX-ABC-005",10,"essence",1.2);
        Taxi tSix = new Taxi(6,"TX-ABC-006",12,"essence",1.2);
        Taxi tSeven = new Taxi(7,"TX-ABC-007",4,"essence",1.2);

        Categorie cOne = new Categorie(1,"Camionnette");
        Categorie cTwo = new Categorie(2,"SUV");
        Categorie cThree = new Categorie(3,"Auto");
        Categorie cFour = new Categorie(4,"Citadine");
        Categorie cFive = new Categorie(5,"Motorhome");
        Categorie cSix = new Categorie(6,"Limousine");

        cOne.getElements().add(tOne);
        cOne.getElements().add(tTwo);
        cFive.getElements().add(tThree);
        cSix.getElements().add(tFour);
        cThree.getElements().add(tFive);
        cFour.getElements().add(tSix);
        cTwo.getElements().add(tSeven);
        //Ajout à la catégorie SUV , la catégorie Limousine
        cTwo.getElements().add(cSix);

        //TODO pick up here
        System.out.println("Test Cat 1 :\n");
        System.out.println(cOne);
        System.out.println("\nTest Cat 2 :\n");
        System.out.println(cTwo);
        //cTwo -> SUV -> Ajouté limousine
        System.out.println("\nTest Cat 3 :\n");
        System.out.println(cThree);
        System.out.println("\nTest Cat 4 :\n");
        System.out.println(cFour);
        System.out.println("\nTest Cat 5 :\n");
        System.out.println(cFive);
        System.out.println("\nTest Cat 6 :\n");
        System.out.println(cSix);

    }
}
