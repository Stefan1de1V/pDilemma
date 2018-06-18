package strategies;

/**
 * Class representing a move played by the opponent in a two-player IPD game as send to the player.
 * @author Stefan
 *
 */
public class InformationItem {

	//lost info is -1, coop is 1, defect is 2
	private int coopDefectLost;
	private int delay; //How much information is delayed
	private int secretDelay; //How much turns left before delayed information arrives
	
	/**
	 * Create an Information item
	 * @param coopDefectLost informs the player about whether the opponent cooperated, defected or
	 * whether that information is lost.
	 * @param delay if the opponent cooperated or defected, this variable is inspected to see if that
	 * information has arrived with any delay by the player.
	 * @param secretDelay this is not used by the player, but informs the game how many delay is left
	 * before it needs to turn lost information into delayed information from the player's perspective.
	 */
	public InformationItem(int coopDefectLost, int delay, int secretDelay) {
		this.coopDefectLost = coopDefectLost;
		this.delay = delay;
		this.secretDelay = secretDelay;
	}
	
	/**
	 * Decrease the amount of rounds left until delayed information arrives.
	 * This causes the Game object to create a new InformationItem (since Game actually has access
	 * to what should be its parameters).
	 */
	public void decreaseSecretDelay() {
		this.secretDelay--;
	}
	
	/**
	 * Checks whether this is equal to another Object.
	 */
	@Override
	public boolean equals(Object other) {
		if(other instanceof InformationItem) {
			InformationItem otherItem = (InformationItem) other;
			if(this.coopDefectLost == otherItem.getCoopDefectLost() &&
					this.delay == otherItem.getDelay() && this.secretDelay == otherItem.getSecretDelay()) {
				return true;
			}
		}
		return false;
	}
	
	//Getters
	
	/**
	 * 
	 * @return whether the opponent cooperated, defected or it is not known (possibly yet).
	 */
	public int getCoopDefectLost() {
		return coopDefectLost;
	}

	/**
	 * 
	 * @return how long the delay was for information about the opponent cooperating or defecting.
	 */
	public int getDelay() {
		return delay;
	}
	
	/**
	 * 
	 * @return how many rounds are left because lost information turns into delayed information as seen
	 * from the players perspective.
	 */
	public int getSecretDelay() {
		return secretDelay;
	}
}
