
public class Fire extends Pokemon{
	
	//empty constructor
	public Fire() {
		super();
	}
	
	// complete constructor
	public Fire(String name, String grade, int hp) {
		super(name, grade, hp, "Fire");
	} 

	@Override
	// normal: Electric
	// not_effective: Fire, Water
    public int calEffectiveness(Pokemon opponent) {
        if (opponent instanceof Electric) {
        	effectiveness = 10;
        }
        else {
        	effectiveness = 5;
        }
        return effectiveness;
    }
	
	
	@Override
	public String toString() {
		return String.format("Fire %s", super.toString());
	}
}
