package se.dat255.bulletinferno.util;

public class GameActionEventImpl implements GameActionEvent {
	private final ResourceIdentifier source;
	private final GameAction action;
	
	public GameActionEventImpl(ResourceIdentifier source, GameAction action) {
		this.source = source;
		this.action = action;
	}
	
	@Override
	public ResourceIdentifier getSource() {
		return source;
	}

	@Override
	public GameAction getAction() {
		return action;
	}
	
}
