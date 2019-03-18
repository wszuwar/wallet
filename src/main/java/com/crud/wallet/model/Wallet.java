package com.crud.wallet.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "wallet")
@EntityListeners(AuditingEntityListener.class)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


   @DateTimeFormat(pattern = "YYYY-MM-dd")
    @Column(name = "date")
    private Date date;



    @Column(name = "name")
    private String name;


    @Column(name = "price")
    private Long price;

    @Column(name = "money_add")
    private Long moneyAdd;

    public Date getDate() {
        return date;
    }

    public void setDate(Date createdAt) {
        this.date = createdAt;
    }

}
