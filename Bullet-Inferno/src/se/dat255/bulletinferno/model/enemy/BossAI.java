package se.dat255.bulletinferno.model.enemy;
import se.dat255.bulletinferno.model.Enemy;

public interface BossAI extends Enemy {

public void	startBattle();

public void fullHP();

public void midHP();

public void lowHP();

public void die();


}

