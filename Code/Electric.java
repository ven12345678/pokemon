
public class Electric extends Pokemon{
		
	//empty constructor
	public Electric() {
		super();
	}
	
	// complete constructor
	public Electric(String name, String grade, int hp) {
		super(name, grade, hp, "Electric");
	} 

	
	@Override
	// super_effective: Water
	// normal: Fire
	// not_effective: Electric
	public int calEffectiveness(Pokemon opponent) {
        if (opponent instanceof Water) {
        	effectiveness = 15;
        }
        else if (opponent instanceof Fire) {
        	effectiveness = 10;
        }
        else { 
        	effectiveness =  5;
        }
        return effectiveness;
    }
	
	
	@Override
	public String toString() {
		return String.format("Electric %s]", super.toString());
	}
	
}
