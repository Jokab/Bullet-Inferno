package se.dat255.bulletinferno.util;

public enum GameActionImpl implements GameAction {
	DIED, SHOT;

	@Override
	public String getAction() {
		return name();
	}

}
