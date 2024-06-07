package com.example.adplacementservice.model;

import com.example.adplacementservice.model.enums.DealStatus;
import com.example.adplacementservice.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Ad ad;

    @ManyToOne(fetch = FetchType.EAGER)
    private User seller;

    @ManyToOne(fetch = FetchType.EAGER)
    private User buyer;

    @Enumerated(EnumType.STRING)
    private DealStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


}
