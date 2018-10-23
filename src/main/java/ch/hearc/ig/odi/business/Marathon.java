/*
 * Company : HEG-ARC
 * Lesson: ODI SA17
 * Project: Marathon
 * Autor: Myriam Schaffter
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
  private List listCategories;

  public Marathon() {
    this.listCategories = new ArrayList();
  }

  public Marathon(Long id, String name, String city) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.listCategories = new ArrayList();

  }

  public Marathon(Marathon m, Category c) {
    this.id = m.getId();
    this.name = m.getName();
    this.city = m.getCity();
    this.listCategories = new ArrayList();
    this.listCategories.add(c);
  }

  @XmlElement
  public List getListCategories() {
    return listCategories;
  }

  public void setListCategories(List listCategory) {
    this.listCategories = listCategory;
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
    this.listCategories.add(category);
    category.setMarathon(this);
  }

  public Category getCategory(Long id) {
    for (int i = 0; i < listCategories.size(); i++) {
      Category c = (Category) listCategories.get(i);
      if (c.getId().longValue() == (id.longValue())) {
        return (Category) listCategories.get(i);
      }
    }
    return null;
  }

  public int getIndex(Long id) throws MarathonException {
    for (int i = 0; i < listCategories.size(); i++) {
      Category c = (Category) listCategories.get(i);
      if (c.getId().longValue() == (id.longValue())) {
        return i;
      }
    }
    throw new MarathonException("Index not found");
  }

  public void deleteCategory(Long id) throws MarathonException {
    this.listCategories.remove(getIndex(id));
  }
}
