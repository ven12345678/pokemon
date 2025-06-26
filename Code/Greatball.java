public class Greatball extends Pokeball {
    public Greatball() {
        super("silver");
    }

    @Override
    public boolean catchPokemon(Pokemon pokemon) {
        switch (pokemon.getGrade().toLowerCase()) {
            case "bronze":
                return true;
            case "silver":
                return true;
            case "gold":
                return false;
            case "platinum":
                return false;
            default:
                throw new IllegalArgumentException("Invalid Pokémon grade");
        }
    }

	@Override
	public String toString() {
		return "Greatball [toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
	
}
