/*
 * Company : HEG-ARC
 * Lesson: ODI SA17
 * Project: Marathon
 * Autor: Myriam Schaffter
 * Date: 23.11.17 10:45
 * Module: sa17-projet1
 */

package ch.hearc.ig.odi.service;

import ch.hearc.ig.odi.business.Category;
import ch.hearc.ig.odi.business.Marathon;
import ch.hearc.ig.odi.business.Person;
import ch.hearc.ig.odi.exception.MarathonException;
import ch.hearc.ig.odi.exception.PersonException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock persistence service class. Used as singleton to simulate persistence
 */
public class RestService {

  private final DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
  private Map<Long, Marathon> mapMarathon;
  private Map<Long, Person> mapPeople;

  /**
   * Empty constructor. All initialization should be done here.
   */
  public RestService() throws ParseException {

    populateMockPersistenceData();

  }


  /**
   * Allows to check that the same RestService is used for all requests.
   *
   * @return this object's HashCode
   */
  public int getHashCode() {
    return hashCode();
  }

  public Date getDate(String date) throws ParseException {
    return format.parse(date);
  }

  public List<Marathon> getMarathons() {
    List<Marathon> marathons = new ArrayList<>(mapMarathon.values());
    return marathons;
  }

  public Marathon getMarathon(Long id) {
    return mapMarathon.get(id);
  }

  public Marathon updateMarathon(Long id, String name, String city) {
    Marathon m = mapMarathon.get(id);
    m.setName(name);
    m.setCity(city);
    return mapMarathon.get(id);
  }


  public Marathon createMarathon(Long id, String name, String city) throws MarathonException {
    if (id != null) {
      mapMarathon.put(id, new Marathon(id, name, city));
      return mapMarathon.get(id);
    } else {
      throw new MarathonException("id Marathon can't be null");
    }
  }

  public void deleteMarathon(Long id) throws MarathonException {
    Marathon m = mapMarathon.remove(id);
    if (m == null) {
      throw new MarathonException("Marathon doesn't exist");
    }
  }

  public Marathon createCategory(Long idMarathon, Long idCategory, String nameCategory,
      Date dateOfRunCategory, Integer maxPerson, Double registrationFees, int maxAge, int minAge) {
    Marathon m = mapMarathon.get(idMarathon);
    m.addCategory(
        new Category(idCategory, nameCategory, dateOfRunCategory, maxPerson, registrationFees,
            maxAge, minAge));
    return m;
  }

  public Marathon updateNameCategory(Long idMarathon, Long idCategory, String nameCategory) {
    Marathon marathon = mapMarathon.get(idMarathon);
    if (!nameCategory.matches("^\\s*$")) {
      Category c = marathon.getCategory(idCategory);
      c.setName(nameCategory);
    }
    return marathon;
  }

  public void deleteCategory(Long idMarathon, Long idCategory) throws MarathonException {
    Marathon m = mapMarathon.get(idMarathon);
    m.deleteCategory(idCategory);
  }

  public List<Person> getPersons() {
    List<Person> list = new ArrayList<>(mapPeople.values());
    return list;
  }

  public Person getPerson(Long idPerson) {
    return mapPeople.get(idPerson);
  }

  public Person createPerson(Long id, String firstName, String lastName, Date birthdate) {
    mapPeople.put(id, new Person(id, firstName, lastName, birthdate));
    return mapPeople.get(id);
  }

  public Person updatePerson(Long idPerson, String firstName, String lastName)
      throws PersonException {
    Person p = mapPeople.get(idPerson);
    if (p != null) {
      p.setFirstName(firstName);
      p.setLastName(lastName);
      return p;
    } else {
      throw new PersonException("unknown Person: " + idPerson);
    }
  }

  public void deletePerson(Long idPerson) throws PersonException {
    Person person = mapPeople.remove(idPerson);
    if (person == null) {
      throw new PersonException("unknown Person: " + idPerson);
    }
  }

  public Category addPersonCategory(Long idMarathon, Long idCategory, Long idPerson)
      throws MarathonException {
    Marathon marathon = mapMarathon.get(idMarathon);
    Category category = marathon.getCategory(idCategory);
    Person person = mapPeople.get(idPerson);
    category.addPerson(person);
    return category;
  }


  public Category removePersonFromCategory(Long idMarathon, Long idCategory, Long idPerson)
      throws PersonException, MarathonException {
    Marathon m = mapMarathon.get(idMarathon);
    Category c = m.getCategory(idCategory);
    c.unregisterPerson(idPerson);
    return c;
  }

  public List<Category> runsMarathon(Long personId) {
    Person person = mapPeople.get(personId);
    return (List<Category>) person.getCategories();
  }

  private void populateMockPersistenceData() throws ParseException {
    mapMarathon = new HashMap<>();
    mapPeople = new HashMap<>();

    //create marathon
    Marathon m0 = new Marathon(Long.parseLong("1000"), "Swiss Alpine Marathon", "Davos");
    Marathon m1 = new Marathon(Long.parseLong("1001"), "Lausanne Marathon", "Lausanne");
    Marathon m2 = new Marathon(Long.parseLong("1002"), "Bieler Lauftage", "Bienne");
    Marathon m3 = new Marathon(Long.parseLong("1003"), "Geneva Marathon", "Genève");

    //create and add Category to marathon
    m1.addCategory(
        new Category(Long.parseLong("2001"), "Marathon Juniors Garçons", format.parse("21.10.2018"),
            -1, Double.parseDouble("90"), 1999, 2000));
    m1.addCategory(
        new Category(Long.parseLong("2002"), "Marathon Juniors Filles", format.parse("21.10.2018"),
            -1, Double.parseDouble("90"), 1999, 2000));
    m1.addCategory(
        new Category(Long.parseLong("2003"), "10 km Hommes H20", format.parse("21.10.2018"), 6000,
            Double.parseDouble("52"), 1989, 1998));
    m1.addCategory(
        new Category(Long.parseLong("2004"), "10 km Dame D20", format.parse("21.10.2018"), 6000,
            Double.parseDouble("52"), 1989, 1998));
    m1.addCategory(new Category(Long.parseLong("2005"), "Pink Challenge marche 15 min",
        format.parse("21.10.2018"), 500, Double.parseDouble("30"), 1917, 2018));

    m2.addCategory(
        new Category(Long.parseLong("2006"), "Course-Loisir 13.5 km", format.parse("08.06.2018"),
            -1, Double.parseDouble("68"), 1900, 2004));
    m2.addCategory(new Category(Long.parseLong("2007"), "Semi-marathon nocturne 21.1 km",
        format.parse("08.06.2018"), -1, Double.parseDouble("76"), 1900, 2006));
    m2.addCategory(new Category(Long.parseLong("2008"), "Ultra marathon nocturne 56 km",
        format.parse("08.06.2018"), -1, Double.parseDouble("120"), 1900, 2006));

    m3.addCategory(
        new Category(Long.parseLong("2009"), "10 km Course", format.parse("05.05.2018"), -1,
            Double.parseDouble("45"), 1900, 2003));
    m3.addCategory(new Category(Long.parseLong("2010"), "Marathon", format.parse("06.05.2018"), -1,
        Double.parseDouble("45"), 1900, 2000));
    m3.addCategory(
        new Category(Long.parseLong("2011"), "Course Junior 5km", format.parse("05.05.2018"), -1,
            Double.parseDouble("17"), 2003, 2004));

    //create and add person to list of people
    mapPeople.put(Long.parseLong("3001"),
        new Person(Long.parseLong("3001"), "Myriam", "Schaffter", format.parse("17.09.1989")));
    mapPeople.put(Long.parseLong("3002"),
        new Person(Long.parseLong("3002"), "Ethan", "Bos", format.parse("11.02.2000")));
    mapPeople.put(Long.parseLong("3003"),
        new Person(Long.parseLong("3003"), "Emily", "Smith", format.parse("21.06.1996")));
    mapPeople.put(Long.parseLong("3004"),
        new Person(Long.parseLong("3004"), "Jean", "Smith", format.parse("01.11.1997")));
    mapPeople.put(Long.parseLong("3005"),
        new Person(Long.parseLong("3005"), "Paul", "Rossman", format.parse("15.03.1977")));
    mapPeople.put(Long.parseLong("3006"),
        new Person(Long.parseLong("3006"), "Anna", "Barth", format.parse("05.12.1960")));
    mapPeople.put(Long.parseLong("3007"),
        new Person(Long.parseLong("3007"), "Kylie", "Lawrence", format.parse("12.08.1658")));

    //add Participant to one category
    try {
      m1.getCategory(Long.parseLong("2004")).addPerson(mapPeople.get(Long.parseLong("3001")));
      m1.getCategory(Long.parseLong("2001")).addPerson(mapPeople.get(Long.parseLong("3002")));
      m2.getCategory(Long.parseLong("2006")).addPerson(mapPeople.get(Long.parseLong("3003")));
      m2.getCategory(Long.parseLong("2006")).addPerson(mapPeople.get(Long.parseLong("3004")));
      m3.getCategory(Long.parseLong("2010")).addPerson(mapPeople.get(Long.parseLong("3005")));
      m3.getCategory(Long.parseLong("2009")).addPerson(mapPeople.get(Long.parseLong("3006")));
    } catch (MarathonException e) {
      e.printStackTrace();
    }

    //add marathon to list of marathons
    mapMarathon.put(m0.getId(), m0);
    mapMarathon.put(m1.getId(), m1);
    mapMarathon.put(m2.getId(), m2);
    mapMarathon.put(m3.getId(), m3);
  }

}
