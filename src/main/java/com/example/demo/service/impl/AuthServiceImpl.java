@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtResponse register(AuthRequest request) {

        // ❗ prevent duplicate email crash
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());

        User savedUser = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(savedUser);

        String rolesCsv = String.join(",", savedUser.getRoles());

        return new JwtResponse(
                token,
                savedUser.getId(),
                savedUser.getEmail(),
                rolesCsv
        );
    }
}
