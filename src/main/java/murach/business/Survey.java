package murach.business;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "surveys")
public class Survey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String heardFrom;
    private String wantsUpdates;
    private String emailAnnouncements;
    private String contactVia;

    public Survey() {
        this.user = new User();
        this.heardFrom = "";
        this.wantsUpdates = "No";
        this.emailAnnouncements = "No";
        this.contactVia = "";
    }

    public Survey(User user, String heardFrom, String wantsUpdates, 
                  String emailAnnouncements, String contactVia) {
        this.user = user;
        this.heardFrom = heardFrom;
        this.wantsUpdates = wantsUpdates;
        this.emailAnnouncements = emailAnnouncements;
        this.contactVia = contactVia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHeardFrom() {
        return heardFrom;
    }

    public void setHeardFrom(String heardFrom) {
        this.heardFrom = heardFrom;
    }

    public String getWantsUpdates() {
        return wantsUpdates;
    }

    public void setWantsUpdates(String wantsUpdates) {
        this.wantsUpdates = wantsUpdates;
    }

    public String getEmailAnnouncements() {
        return emailAnnouncements;
    }

    public void setEmailAnnouncements(String emailAnnouncements) {
        this.emailAnnouncements = emailAnnouncements;
    }

    public String getContactVia() {
        return contactVia;
    }

    public void setContactVia(String contactVia) {
        this.contactVia = contactVia;
    }
}
