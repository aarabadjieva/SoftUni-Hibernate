import entities.ingredients.*;
import entities.labels.BasicLabel;
import entities.shampoos.BasicShampoo;
import entities.shampoos.FiftyShades;
import entities.shampoos.FreshNuke;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory managerFactory = Persistence
                .createEntityManagerFactory("shampoo_company");

        EntityManager em = managerFactory.createEntityManager();

        em.getTransaction().begin();
        BasicIngredient am = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();
        BasicIngredient strawberry = new Strawberry();

        BasicLabel label = new BasicLabel("Title", "subtitle");
        label.setTitle("Fresh Nuke");
        label.setSubtitle("Contains ammonium chloride and mint");
        BasicShampoo nuke = new FreshNuke(label);
        label.setTitle("Fifty Shades");
        label.setSubtitle("Contains nettle and mint");
        BasicShampoo shades = new FiftyShades(label);

        nuke.getIngredients().add(am);
        nuke.getIngredients().add(mint);

        shades.getIngredients().add(nettle);
        shades.getIngredients().add(mint);


        em.persist(nuke);
        em.persist(shades);
        em.getTransaction().commit();
        em.close();
    }}
