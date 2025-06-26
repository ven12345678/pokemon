
public class Water extends Pokemon{
	
	//empty constructor
	public Water() {
		super();
	}
	
	//complete constructor
	public Water(String name, String grade, int hp) {
		super(name, grade, hp, "Water");
	} 
	
	@Override
	// super_effective: Fire
	// normal: Electric
	// not_effective: Water
	public int calEffectiveness(Pokemon opponent) {
        if (opponent instanceof Fire) {
        	effectiveness = 15;
        }
        else if (opponent instanceof Electric) {
        	effectiveness = 10;
        }
        else { 
        	effectiveness =  5;
        }
        return effectiveness;
    }
	
	
	@Override
	public String toString() {
		return String.format("Water %s]", super.toString());
	}
}
