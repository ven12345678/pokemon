public class Ultraball extends Pokeball {
    public Ultraball() {
        super("gold");
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
                return false;
            default:
                throw new IllegalArgumentException("Invalid Pokémon grade");
        }
    }

	@Override
	public String toString() {
		return "Ultraball [toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
