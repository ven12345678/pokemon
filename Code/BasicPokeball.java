public class BasicPokeball extends Pokeball {
    public BasicPokeball() {
        super("bronze");
    }

    @Override
    public boolean catchPokemon(Pokemon pokemon) {
        switch (pokemon.getGrade().toLowerCase()) {
            case "bronze":
                return true;
            case "silver":
                return false;
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
		return "BasicPokeball [toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
   
}
