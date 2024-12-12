package services;

import entities.CarRental;
import entities.Invoice;

import java.time.Duration;

public class RentalService {

    private Double pricePerHour;
    private Double pricePerDay;

    private  TaxService taxService;

    public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public void processInvoice (CarRental carRental) {

        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes / 60.0;

        double basicaPay;
        if (hours <= 12.0) {
            basicaPay = pricePerHour * Math.ceil( hours);
        } else {
            basicaPay = pricePerDay * Math.ceil(hours / 24);
        }

        double tax = taxService.tax(basicaPay);

        carRental.setInvoice(new Invoice(basicaPay, tax));
    }

}
