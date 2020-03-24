package pl.polsl.task.manager.rest.api.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.NotAuthorizedException;
import pl.polsl.task.manager.rest.api.exceptions.UsernameAlreadyUsedException;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.repositories.UserRepository;
import pl.polsl.task.manager.rest.api.views.AuthenticationPost;
import pl.polsl.task.manager.rest.api.views.AuthenticationView;
import pl.polsl.task.manager.rest.api.views.UserPost;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5h
    private static final String secret = "halorozwoj2";

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        String parsedToken;
        if (token != null && token.startsWith("Bearer "))
            parsedToken = token.substring(7);
        else
            parsedToken = token;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(parsedToken).getBody();
        return claimsResolver.apply(claims);
    }

    private String generateToken(UserDetails userDetails) {
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !getExpirationDateFromToken(token).before(new Date());
    }

    @Override
    public User getUserFromHeader(String token) {
        try {
            String userName = getClaimFromToken(token, Claims::getSubject);
            return userRepository.findByUsername(userName).orElseThrow(() -> new NotAuthorizedException("Token does not match any user"));
        }catch (Exception e){
            throw new NotAuthorizedException(e.getMessage());
        }
    }

    @Override
    public User registerUser(UserPost userPost) {
        User user = new User();
        user.setUsername(userPost.getUsername());
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyUsedException(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userPost.getPassword()));
        user.setEmail(userPost.getEmail());
        user.setSurname(userPost.getSurname());
        user.setName(userPost.getName());
        return userRepository.save(user);
    }

    @Override
    public AuthenticationView loginUser(AuthenticationPost authenticationPost) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationPost.getUsername(), authenticationPost.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationPost.getUsername());
            String token = generateToken(userDetails);
            AuthenticationView authenticationView = new AuthenticationView();
            authenticationView.setToken(token);
            authenticationView.setExpirationDate(getExpirationDateFromToken(token));
            return authenticationView;
        }  catch (Exception e){
            throw new NotAuthorizedException("Wrong credentials");
        }
    }

}
