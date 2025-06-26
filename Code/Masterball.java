public class Masterball extends Pokeball {
    public Masterball() {
        super("platinum");
    }

    @Override
    public boolean catchPokemon(Pokemon pokemon) {
        switch (pokemon.getGrade().toLowerCase()) {
            case "bronze":
                return true;
            case "silver":
                return true;
            case "gold":
                return true;
            case "platinum":
                return true;
            default:
                throw new IllegalArgumentException("Invalid Pokémon grade");
        }
    }

	@Override
	public String toString() {
		return "Masterball [toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
    
}
