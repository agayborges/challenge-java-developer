package br.com.neurotech.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private NeurotechClient neurotechClient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleModel vehicleModel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeeType feeType;


    @Column(precision = 3, scale = 2, nullable = false)
    private BigDecimal taxValue;

    public Credit() {
    }

    public Credit(UUID id, NeurotechClient neurotechClient, VehicleModel vehicleModel, FeeType feeType, BigDecimal taxValue) {
        this.id = id;
        this.neurotechClient = neurotechClient;
        this.vehicleModel = vehicleModel;
        this.feeType = feeType;
        this.taxValue = taxValue;
    }

    public Credit(NeurotechClient neurotechClient, VehicleModel vehicleModel, FeeType feeType) {
        this.neurotechClient = neurotechClient;
        this.vehicleModel = vehicleModel;
        this.feeType = feeType;
    }

    public Credit(NeurotechClient neurotechClient, VehicleModel vehicleModel, FeeType feeType, BigDecimal taxValue) {
        this.neurotechClient = neurotechClient;
        this.vehicleModel = vehicleModel;
        this.feeType = feeType;
        this.taxValue = taxValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public NeurotechClient getNeurotechClient() {
        return neurotechClient;
    }

    public void setNeurotechClient(NeurotechClient neurotechClient) {
        this.neurotechClient = neurotechClient;
    }

    public VehicleModel getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }
}
