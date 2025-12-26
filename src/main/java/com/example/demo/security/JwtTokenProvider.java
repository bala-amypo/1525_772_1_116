@Component
public class JwtTokenProvider {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("roles", String.join(",", user.getRoles()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }
}
