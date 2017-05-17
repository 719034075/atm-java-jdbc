package main.entity;

/**
 * Created by 71903 on 2017/5/14.
 */
public class Areas {
   private Integer id;
   private String name;
   private Integer parentid;

    @Override
    public String toString() {
        return "Areas{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentid=" + parentid +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

}
