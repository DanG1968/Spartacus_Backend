package novare.com.hk.spartacus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
  private String displayName;
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private int id;
  private String name;

  private String[] authorities;

  public Account(String displayName, int id, String name, String... auths) {
    super();
    this.displayName = displayName;
    this.id = id;
    this.name = name;
    this.authorities = auths;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getAuthorities() {
    return authorities;
  }

  public void setAuthorities(String[] authorities) {
    this.authorities = authorities;
  }

}
