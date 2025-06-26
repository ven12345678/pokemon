public class Pokeball {
    private String grade;

    public Pokeball() {
    	
    }
    
    public Pokeball(String grade) {
        setGrade(grade);
    }

    public boolean catchPokemon(Pokemon pokemon) {
    	return false;
    }
    
    public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		if (grade.equalsIgnoreCase("bronze") || 
		        grade.equalsIgnoreCase("silver") || 
		        grade.equalsIgnoreCase("gold") || 
		        grade.equalsIgnoreCase("platinum")) {
		        this.grade = grade;
		} else {
		    	throw new IllegalArgumentException("Invalid grade. Must be one of: bronze, silver, gold, platinum.");
		}
	}

	@Override
    public String toString() {
        return String.format("Pokeball [grade=%s]", grade);
    }
}
