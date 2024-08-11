package Banking.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class Client implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;

@Column(unique = true)
private String username;

private String password;

private LocalDate dateOfBirth;

@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private BankingAccount account;
@ElementCollection(fetch = FetchType.EAGER)
private List<String> phoneNumbers = new ArrayList<>();

@ElementCollection(fetch = FetchType.EAGER)
private List<String> emails = new ArrayList<>();

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	
	return null;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return UserDetails.super.isAccountNonExpired();
}

@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return UserDetails.super.isAccountNonLocked();
}

@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return UserDetails.super.isCredentialsNonExpired();
}

@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return UserDetails.super.isEnabled();
}

@Override
protected Object clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	return super.clone();
}

@Override
protected void finalize() throws Throwable {
	// TODO Auto-generated method stub
	super.finalize();
}


}
	
	

