package ru.otus.crm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_id_seq")
    @SequenceGenerator(name = "phone_id_seq", sequenceName = "phone_id_seq", allocationSize = 1)
    private Long id;
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }
}
