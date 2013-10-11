package se.dat255.bulletinferno.util;

public class GameActionEventImpl implements GameActionEvent {
	private final ResourceIdentifier source;
	
	public GameActionEventImpl(ResourceIdentifier source) {
		this.source = source;
	}
	
	@Override
	public ResourceIdentifier getSource() {
		return source;
	}

	@Override
	public void getAction() {
		// TODO Auto-generated method stub
		
	}
	
}
