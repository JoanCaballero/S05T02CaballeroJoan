package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getUserName(String token);
    String tokenGenerator(UserDetails userDetails);
    boolean validToken(String token, UserDetails userDetails);
}
