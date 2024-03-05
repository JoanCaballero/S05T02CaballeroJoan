package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces;

public interface JwtService {
    public String getUserName(String token);
    String tokenGenerator(UserDetails userDetails);
    public boolean tokenValidation(String token, UserDetails userDetails);
}
