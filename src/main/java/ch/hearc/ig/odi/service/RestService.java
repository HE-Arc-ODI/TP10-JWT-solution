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

  private Map<Long, Marathon> mapMarathon;
  private Map<Long, Person> mapPeople;

  private final DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

  /**
   * Empty constructor. All initialization should be done here.
   */
  public RestService() throws ParseException {

    this.mapMarathon = new HashMap<>();
    this.mapPeople = new HashMap<>();

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
    this.mapPeople.put(Long.parseLong("3001"),
        new Person(Long.parseLong("3001"), "Myriam", "Schaffter", format.parse("17.09.1989")));
    this.mapPeople.put(Long.parseLong("3002"),
        new Person(Long.parseLong("3002"), "Ethan", "Bos", format.parse("11.02.2000")));
    this.mapPeople.put(Long.parseLong("3003"),
        new Person(Long.parseLong("3003"), "Emily", "Smith", format.parse("21.06.1996")));
    this.mapPeople.put(Long.parseLong("3004"),
        new Person(Long.parseLong("3004"), "Jean", "Smith", format.parse("01.11.1997")));
    this.mapPeople.put(Long.parseLong("3005"),
        new Person(Long.parseLong("3005"), "Paul", "Rossman", format.parse("15.03.1977")));
    this.mapPeople.put(Long.parseLong("3006"),
        new Person(Long.parseLong("3006"), "Anna", "Barth", format.parse("05.12.1960")));
    this.mapPeople.put(Long.parseLong("3007"),
        new Person(Long.parseLong("3007"), "Kylie", "Lawrence", format.parse("12.08.1658")));

    //add Participant to one category
    m1.getCategory(Long.parseLong("2004")).addPerson(this.mapPeople.get(Long.parseLong("3001")));
    m1.getCategory(Long.parseLong("2001")).addPerson(this.mapPeople.get(Long.parseLong("3002")));
    m2.getCategory(Long.parseLong("2006")).addPerson(this.mapPeople.get(Long.parseLong("3003")));
    m2.getCategory(Long.parseLong("2006")).addPerson(this.mapPeople.get(Long.parseLong("3004")));
    m3.getCategory(Long.parseLong("2010")).addPerson(this.mapPeople.get(Long.parseLong("3005")));
    m3.getCategory(Long.parseLong("2009")).addPerson(this.mapPeople.get(Long.parseLong("3006")));
    m3.getCategory(Long.parseLong("2009")).addPerson(this.mapPeople.get(Long.parseLong("3007")));

    //add marathon to list of marathons
    mapMarathon.put(m0.getId(), m0);
    mapMarathon.put(m1.getId(), m1);
    mapMarathon.put(m2.getId(), m2);
    mapMarathon.put(m3.getId(), m3);

  }

  /**
   * Allows to check that the same RestService is used for all requests.
   *
   * @return this object's HashCode
   */
  public int getHashCode() {
    return this.hashCode();
  }

  public Date getDate(String date) throws ParseException {
    return format.parse(date);
  }

  public List<Marathon> getMarathons() {
    List l = new ArrayList<Marathon>();
    for (Object o : mapMarathon.keySet()) {
      Marathon marathon = mapMarathon.get(o);
      l.add(marathon);
    }
    return l;

  }

  public Marathon getMarathon(Long id) throws MarathonException {
    if (id != null) {
      Marathon m = this.mapMarathon.get(id);
      if (m != null) {
        return m;
      } else {
        throw new MarathonException("Marathon doesn't exist");
      }
    } else {
      throw new MarathonException("id Marathon can't be null");
    }
  }

  public Marathon updateMarathon(Long id, String name, String city) throws MarathonException {
    if (id != null) {
      Marathon m = this.mapMarathon.get(id);
      if (m != null) {
        m.setName(name);
        m.setCity(city);
        return this.mapMarathon.get(id);
      } else {
        throw new MarathonException("Marathon doesn't exist");
      }
    } else {
      throw new MarathonException("id Marathon can't be null");
    }
  }


  public Marathon createMarathon(Long id, String name, String city) throws MarathonException {
    if (id != null) {
      this.mapMarathon.put(id, new Marathon(id, name, city));
      return this.mapMarathon.get(id);
    } else {
      throw new MarathonException("id Marathon can't be null");
    }
  }

  public void deleteMarathon(Long id) throws MarathonException {
    Marathon m = this.mapMarathon.remove(id);
    if (m == null) {
      throw new MarathonException("Marathon doesn't exist");
    }

  }

  public Marathon createCategory(Long idMarathon, Long idCategory, String nameCategory,
      Date dateOfRunCategory, Integer maxPerson, Double registrationFees, int maxAge, int minAge)
      throws MarathonException {
    Marathon m = this.mapMarathon.get(idMarathon);
    if (m != null) {
      if (idCategory != null && !nameCategory.matches("^\\s*$") && dateOfRunCategory != null
          && maxPerson != null && registrationFees != null && maxAge != 0 && minAge != 0) {
        m.addCategory(
            new Category(idCategory, nameCategory, dateOfRunCategory, maxPerson, registrationFees,
                maxAge, minAge));
        return m;
      } else {
        throw new MarathonException("no Category data can be null or empty");
      }
    } else {
      throw new MarathonException("Marathon doesn't exsit");
    }
  }

  public Marathon updateNameCategory(Long idMarathon, Long idCategory, String nameCategory)
      throws MarathonException {
    Marathon m = this.mapMarathon.get(idMarathon);
    if (m != null) {
      if (idCategory != null && !nameCategory.matches("^\\s*$")) {
        Category c = m.getCategory(idCategory);
        c.setName(nameCategory);
        return m;
      } else {
        throw new MarathonException("Category name can be null or empty");
      }
    } else {
      throw new MarathonException("Marathon doesn't exsit");
    }
  }

  public void deleteCategory(Long idMarathon, Long idCategory) throws MarathonException {
    Marathon m = this.mapMarathon.get(idMarathon);
    if (m != null) {
      if (idCategory != null) {
        m.deleteCategory(idCategory);
      } else {
        throw new MarathonException("Category id can't be null");
      }
    } else {
      throw new MarathonException("Marathon doesn't exsit");
    }
  }

  public List<Person> getPersons() {
    List l = new ArrayList<Person>();
    for (Object o : mapPeople.keySet()) {
      Person person = mapPeople.get(o);
      l.add(person);
    }
    return l;
  }

  public Person getPerson(Long id) throws PersonException {
    if (id != null) {
      Person p = this.mapPeople.get(id);
      if (p != null) {
        return p;
      } else {
        throw new PersonException("Person doesn't exist");
      }
    } else {
      throw new PersonException("id Person can't be null");
    }
  }

  public Person createPerson(Long id, String firstName, String lastName, Date birthdate)
      throws PersonException {
    if (id != null) {
      this.mapPeople.put(id, new Person(id, firstName, lastName, birthdate));
      return this.mapPeople.get(id);
    } else {
      throw new PersonException("id Person can't be null");
    }
  }

  public Person updatePerson(Long id, String firstName, String lastName) throws PersonException {
    if (id != null) {
      Person p = this.mapPeople.get(id);
      if (p != null) {
        p.setFirstName(firstName);
        p.setLastName(lastName);
        return p;
      } else {
        throw new PersonException("Person doesn't exist");
      }
    } else {
      throw new PersonException("id Person can't be null");
    }
  }

  public void deletePerson(Long id) throws PersonException {
    Person p = this.mapPeople.remove(id);
    if (p == null) {
      throw new PersonException("Marathon doesn't exist");
    }
  }

  public Category addPersonnCategory(Long idMarathon, Long idCategory, Long idPerson)
      throws MarathonException, PersonException {
    Marathon m = this.mapMarathon.get(idMarathon);
    if (m != null) {
      if (idCategory != null) {
        Category c = m.getCategory(idCategory);
        if (c != null) {
          if (idPerson != null) {
            Person p = this.mapPeople.get(idPerson);
            if (p != null) {
              checkAge(c, p);
              c.addPerson(p);
              return c;
            } else {
              throw new PersonException("Person doesn't exist");
            }

          } else {
            throw new PersonException("id person can't be null");
          }
        } else {
          throw new MarathonException("Category doesn't exsist");
        }
      } else {
        throw new MarathonException("id category can't be null");
      }
    } else {
      throw new MarathonException("Marathon doesn't exsit");
    }
  }

  private void checkAge(Category c, Person p) throws MarathonException {
    DateFormat df = new SimpleDateFormat("yyyy");
    int year = Integer.parseInt(df.format(p.getDateBirth()));

    int maxAge = c.getAgeRange()[0];
    int minAge = c.getAgeRange()[1];

    if (year < maxAge && year < minAge || year > maxAge && year > minAge) {
      throw new MarathonException("Person can't be run in this category");
    }

  }

  public Category deletePersonnCategory(Long idMarathon, Long idCategory, Long idPerson)
      throws MarathonException, PersonException {
    Marathon m = this.mapMarathon.get(idMarathon);
    if (m != null) {
      if (idCategory != null) {
        Category c = m.getCategory(idCategory);
        if (c != null) {
          if (idPerson != null) {
            Person p = (Person)c.getParticipantList().get(idPerson.intValue());
            p.deleteCategory(idCategory);
            c.deletePerson(idPerson);
            return c;
          } else {
            throw new PersonException("id person can't be null");
          }
        } else {
          throw new MarathonException("Category doesn't exsist");
        }
      } else {
        throw new MarathonException("id category can't be null");
      }
    } else {
      throw new MarathonException("Marathon doesn't exsit");
    }
  }

  public List<Marathon> runsMarathon(Long id) throws PersonException {
    List l = new ArrayList<Marathon>();
    Person p = this.mapPeople.get(id);
    if (p != null) {
      List lc = p.getCategories();
      int j = 0;
      while (lc.size() > j) {
        l.add(new Marathon(((Category) lc.get(j)).getMarathon(), ((Category) lc.get(j)))); // FIXME: pourquoi créer un nouvel objet marathon?
        j++;
      }
      return l;
    } else {
      throw new PersonException("Person doesn't exist");
    }
  }

}
