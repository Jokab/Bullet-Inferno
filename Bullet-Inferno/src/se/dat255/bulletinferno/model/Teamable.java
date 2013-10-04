package se.dat255.bulletinferno.model;

/**
 * An interface to enable an entity to belong to a team.
 */
public interface Teamable {

	/**
	 * Returns whether the specified team member belongs to my team
	 * 
	 * @param teamMember The (other) Teamable object to compare against.
	 * @return is team member
	 */
	public boolean isInMyTeam(Teamable teamMember);
}
