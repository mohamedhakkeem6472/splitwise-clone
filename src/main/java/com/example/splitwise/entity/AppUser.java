@Entity
@Getter
@Setter
@Table(name = "users")
public class AppUser {
    @Id @GeneratedValue private Long id;
    @Column(unique = true, nullable = false) private String oauthId;
    @Column(unique = true, nullable = false) private String email;
    private String name;
    private String role; // "USER" or "ADMIN"
   
}
