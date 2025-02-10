package org.prd.securityexample.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int max_roles;

    private LocalDateTime last_login;

    @Column(nullable = false)
    private boolean account_expired;

    @Column(nullable = false)
    private boolean account_locked;

    @Column(nullable = false)
    private boolean credentials_expired;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRole> user_roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserPermission> permissions = new ArrayList<>();

    public ArrayList<String> getAllRolesArray(){
        if(user_roles.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<String> roles = new ArrayList<>();
        user_roles.forEach(
                userRole -> {
                    roles.add(userRole.getRole().getName());
                }
        );
        return roles;
    }

    public ArrayList<String> getAllPermissionsArray(){
        if(user_roles.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<String> allPermissions = new ArrayList<>();
        user_roles.forEach(
                userRole -> {
                    allPermissions.addAll(userRole.getRole().getPermissionsArray());
                }
        );
        return allPermissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user_roles.isEmpty()){
            return new ArrayList<>();
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        user_roles.forEach(
                userRole -> {
                    authorities.addAll(userRole.getRole().getAuthorities());
                }
        );

        if(permissions.isEmpty()){
            return authorities;
        }
        permissions.forEach(
                userPermission -> {
                    authorities.add(userPermission.getPermission());
                }
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !account_expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account_locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentials_expired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


//    @Override
//    public final boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null) return false;
//        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
//        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
//        if (thisEffectiveClass != oEffectiveClass) return false;
//        UserEntity that = (UserEntity) o;
//        return getId() != null && Objects.equals(getId(), that.getId());
//    }
//
//    @Override
//    public final int hashCode() {
//        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
//    }
}