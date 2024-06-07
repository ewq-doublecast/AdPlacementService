package com.example.adplacementservice.model;

import com.example.adplacementservice.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliveries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "sender_address")
    private String senderAddress;

    @Column(name = "sender_index")
    private String senderIndex;

    @Column(name = "recipient_address")
    private String recipientAddress;

    @Column(name = "recipient_index")
    private String recipientIndex;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    private Ad ad;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

}
