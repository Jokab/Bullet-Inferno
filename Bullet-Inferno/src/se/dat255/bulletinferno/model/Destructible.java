package se.dat255.bulletinferno.model;

public interface Destructible {
	public void takeDamage(int damage);

	public int getHealth();

	public int getInitialHealth();
}
