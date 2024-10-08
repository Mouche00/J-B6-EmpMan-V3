package models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
@Table(name = "admins")
public class Admin extends Employee{
}
