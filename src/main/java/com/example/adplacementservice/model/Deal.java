package com.example.adplacementservice.model;

import com.example.adplacementservice.model.enums.PaymentMethod;
import com.example.adplacementservice.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "deals")
@NoArgsConstructor
@AllArgsConstructor
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deal_id")
    private Ad ad;

    @ManyToOne
    @JoinColumn
    private User seller;

    @ManyToOne
    @JoinColumn
    private User buyer;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public boolean inProcess() {
        return status == Status.IN_PROCESS;
    }

}
