package se.dat255.bulletinferno.model;


import org.junit.Test;
import se.dat255.bulletinferno.model.mock.SimpleMockProjectile;

import static org.junit.Assert.assertTrue;

public class GameImplTest {

    @Test
    public void retrieveProjectile(){
        // Tests that retrieving a projectile returns the correct
        // type of projectile and that the projectile is added
        // to the current projectiles.


        GameImpl game = new GameImpl();
        assertTrue("The list of projectiles of a new game should be empty",
                game.getProjectiles().isEmpty());

        Projectile projectile = game.retrieveProjectile(SimpleMockProjectile.class);
        assertTrue("retrieveProjectile should return a Projectile of the wanted class-type",
                projectile instanceof SimpleMockProjectile);

        assertTrue("The projectile should be added to the list of projectiles",
                game.getProjectiles().contains(projectile));

    }

}
