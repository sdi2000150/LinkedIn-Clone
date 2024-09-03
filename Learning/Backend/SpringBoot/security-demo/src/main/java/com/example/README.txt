APPLICATION LOGIC - FILES USAGE

UserController.java:
    This class defines the REST API endpoints for user management and authentication. Here's a breakdown of each method and the files involved:

        GET /welcome: This public endpoint doesn't require any security checks. No other files are involved besides UserController.

        POST /addNewUser: This endpoint allows adding a new user. It interacts with the following files:
            UserInfoService: Handles adding the user to the database using UserInfoRepository.
            UserInfo: Represents the user data structure.
            PasswordEncoder (from SecurityConfig): Encodes the user's password before saving it.

        GET /user/userProfile: This endpoint requires the user to be logged in with the "ROLE_USER" authority. It uses the following files:
            SecurityConfig: Defines security configurations like URL authorization rules.
            JwtAuthFilter (called by SecurityConfig): Validates JWT tokens in requests.
            UserInfoDetails (potentially involved during token validation): Converts UserInfo to a Spring Security UserDetails object.

        GET /admin/adminProfile: Similar to GET /user/userProfile but requires "ROLE_ADMIN" authority. Same files are involved.

        POST /generateToken: This endpoint allows users to authenticate and obtain a JWT token. It interacts with the following files:
            AuthenticationManager (from SecurityConfig): Authenticates the user based on username and password.
            JwtService: Generates and validates JWT tokens.
            UserInfoService: Loads user details by username (potentially involves UserInfoDetails).
            AuthRequest: Represents the user's login credentials (username and password).

Other Files:
    SecurityDemoApplication: The main application class that bootstraps the Spring application.
    application.properties: Defines application configuration details like database connection and JPA settings.