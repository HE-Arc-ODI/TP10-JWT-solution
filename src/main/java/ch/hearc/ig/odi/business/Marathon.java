/*
 * 2018. Cours outils de développement intégré. ulysse.rosselet@he-arc.ch
 * Company : HEG-ARC
 * Lesson: ODI SA17
 * Project: Marathon
 * Author: Myriam Schaffter
 * Date: 17.11.17 12:02
 * Module: sa17-projet1
 */

package ch.hearc.ig.odi.business;

import ch.hearc.ig.odi.exception.MarathonException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Marathon")
public class Marathon implements Serializable {

  private Long id;
  private String name;
  private String city;
  private List<Category> listCategories;

  public Marathon() {
    listCategories = new ArrayList<>();
  }

  public Marathon(Long id, String name, String city) {
    this.id = id;
    this.name = name;
    this.city = city;
    listCategories = new ArrayList<>();

  }

  public Marathon(Marathon m, Category c) {
    id = m.getId();
    name = m.getName();
    city = m.getCity();
    listCategories = new ArrayList<>();
    listCategories.add(c);
  }

  @XmlElement
  public List getListCategories() {
    return listCategories;
  }

  public void setListCategories(List<Category> listCategory) {
    listCategories = listCategory;
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
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void addCategory(Category category) {
    listCategories.add(category);
    category.setMarathon(this);
  }

  public Category getCategory(Long id) {
    for (Category category : listCategories) {
      if (category.getId() == (id.longValue())) {
        return category;
      }
    }
    return null;
  }

  public int getIndex(Long id) throws MarathonException {
    for (int i = 0; i < listCategories.size(); i++) {
      Category c = listCategories.get(i);
      if (c.getId() == (id.longValue())) {
        return i;
      }
    }
    throw new MarathonException("Index not found");
  }

  public void deleteCategory(Long id) throws MarathonException {
    this.listCategories.remove(getIndex(id));
  }
}
