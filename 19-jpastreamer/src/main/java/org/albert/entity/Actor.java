package org.albert.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "actor", schema = "sakila", catalog = "")
public class Actor
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "actor_id")
    private short actorId;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "last_update")
    private Timestamp lastUpdate;
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private Set<Film> films;

    public Set<Film> getFilms()
    {
        return films;
    }

    public void setFilms(Set<Film> films)
    {
        this.films = films;
    }

    public short getActorId()
    {
        return actorId;
    }

    public void setActorId(short actorId)
    {
        this.actorId = actorId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor that = (Actor) o;

        if (actorId != that.actorId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) actorId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }
}
