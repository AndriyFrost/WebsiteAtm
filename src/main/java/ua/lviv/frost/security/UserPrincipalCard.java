package ua.lviv.frost.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.lviv.frost.entity.UserCard;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class UserPrincipalCard implements UserDetails {

    private Integer id;

    private String username;

    private String cardCode;

    @JsonIgnore
    private String pinCode;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipalCard(Integer id, String username, String cardCode,
                             String pinCode, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.cardCode = cardCode;
        this.pinCode = pinCode;
        this.authorities = authorities;
    }

    public static UserPrincipalCard create(UserCard userCard) {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userCard.getRole().toString());

        return new UserPrincipalCard(
                userCard.getId(),
                userCard.getFirstName(),
                userCard.getCardCode(),
                userCard.getPinCode(),
                Collections.singletonList(authority)
        );
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return pinCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipalCard that = (UserPrincipalCard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
