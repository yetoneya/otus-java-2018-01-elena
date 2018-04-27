
package ru.otus.elena.dbenchance.dataset;

import ru.otus.elena.dbenchance.interfaces.DataSet;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="phone")
public class Phone extends DataSet implements Serializable {
    
    @Column(name = "phone_phone")
    public int phone;

    public Phone(int phone, long id) {
        super(id);
        this.phone = phone;
    }

    public Phone(int phone) {
        super(0);
        this.phone = phone;
    }

    public Phone() {
    }
    
    public void setPhone(int phone){
        this.phone=phone;
    }
    public int getPhone(){
        return phone;
    }
    public String toString(){
        return "[phone="+phone+"]";
    }
}
