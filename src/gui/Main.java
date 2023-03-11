package gui;

import application.controller.Controller;
import application.model.Prisliste;
import application.model.Produkt;
import application.model.Produktkategori;
import javafx.application.Application;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Application.launch(MainApp.class);

//        System.out.println(Controller.getController().getStorage().getAllSalg().get(0).getSalgsLinjer().size());

//        for (Produkt p : Controller.getController().getStorage().getAllProduktkategorier().get(0).getProdukter()){
//            System.out.println(p);
//        }
//
//        System.out.println(Controller.getController().getStorage().getAllPrislister().get(0).getPrislisteMap().
//        size());
//
//        for (Map.Entry<String, Double> entry : Controller.getController().getStorage().getAllPrislister().
//        get(0).getPrislisteMap().entrySet()){
//            System.out.println(entry.getKey() + "   "+entry.getValue());
//        }


    }
}
