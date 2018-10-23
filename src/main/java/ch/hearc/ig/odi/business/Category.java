/*
 * Company : HEG-ARC
 * Lesson: ODI SA17
 * Project: Marathon
 * Autor: Myriam Schaffter
 * Date: 17.11.17 12:03
 * Module: sa17-projet1
 */

package ch.hearc.ig.odi.business;

import ch.hearc.ig.odi.exception.PersonException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Category")
public class Category implements Serializable {


  private Long id;
  private String name;
  private Date dateOfRun;
  private Integer maxPerson;
  private Double registrationFees;
  private int[] ageRange;
  private Marathon marathon;
  private List participantList;

  public Category() {
    this.participantList = new ArrayList();
  }

  public Category(Long id, String name, Date dateOfRun, Integer maxPerson, Double registrationFees,
      int AgeMax, int AgeMin) {
    this.id = id;
    this.name = name;
    this.dateOfRun = dateOfRun;
    this.maxPerson = maxPerson;
    this.registrationFees = registrationFees;
    this.ageRange = new int[]{AgeMax, AgeMin};
    this.participantList = new ArrayList();
  }

  @XmlElement
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @XmlElement
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @XmlElement
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
  public Date getDateOfRun() {
    return dateOfRun;
  }

  public void setDateOfRun(Date dateOfRun) {
    this.dateOfRun = dateOfRun;
  }

  @XmlElement
  public Integer getMaxPerson() {
    return maxPerson;
  }

  public void setMaxPerson(Integer maxPerson) {
    this.maxPerson = maxPerson;
  }

  @XmlElement
  public Double getRegistrationFees() {
    return registrationFees;
  }

  public void setRegistrationFees(Double registrationFees) {
    this.registrationFees = registrationFees;
  }

  @XmlElement
  public int[] getAgeRange() {
    return ageRange;
  }

  public void setAgeRange(int[] ageRange) {
    this.ageRange = ageRange;
  }

  @XmlElement
  public List getParticipantList() {
    return participantList;
  }

  public void setParticipantList(List participantList) {
    this.participantList = participantList;
  }

  public void addPerson(Person person) {
    this.participantList.add(person);
    person.addMarathon(this);
  }


  public int getIndex(Long id) throws PersonException {
    for (int i = 0; i < participantList.size(); i++) {
      Person p = (Person) participantList.get(i);
      if (p.getId().longValue() == (id.longValue())) {
        return i;
      }
    }
    throw new PersonException("Index not found");
  }

  public void deletePerson(Long id) throws PersonException {
    this.participantList.remove(getIndex(id));
  }

  @XmlTransient
  @JsonIgnore
  public Marathon getMarathon() {
    return marathon;
  }

  @XmlTransient
  public void setMarathon(Marathon marathon) {
    this.marathon = marathon;
  }
}
