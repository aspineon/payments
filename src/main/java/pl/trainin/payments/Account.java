package pl.trainin.payments;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "accounts")
@Entity
@Data
public class Account {

    @GeneratedValue
    @Id
    private Long id;
    private long balance;

    public void increaseBalance(long funds) {
        balance += funds;
    }

    public void decreaseBalance(long funds) {
        balance -= funds;
    }

}
