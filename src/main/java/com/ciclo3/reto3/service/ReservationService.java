package com.ciclo3.reto3.service;

import com.ciclo3.reto3.entities.DTOs.CountClient;
import com.ciclo3.reto3.entities.DTOs.CountStatus;
import com.ciclo3.reto3.entities.Reservation;
import com.ciclo3.reto3.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    public ReservationRepository reservationRepository;

    public List<Reservation> getAll(){return reservationRepository.getAll();}

    public Optional<Reservation> getReservation(int id){return reservationRepository.getReservation(id);}


    public Reservation save(Reservation reservation){
        if(reservation.getIdReservation()==null){
            return reservationRepository.save(reservation);
        }else{
            Optional<Reservation> e= reservationRepository.getReservation(reservation.getIdReservation());
            if (e.isPresent()){
                return reservationRepository.save(reservation);
            }else {
                return reservation;
            }
        }
    }

    public Reservation update(Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> e = reservationRepository.getReservation(reservation.getIdReservation());
            if (!e.isPresent()){
                if(reservation.getStartDate()!=null){
                    e.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){
                    e.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    e.get().setStatus(reservation.getStatus());
                }
                reservationRepository.save(e.get());
                return e.get();
            }else{
                return reservation;
            }
        }else {
            return reservation;
        }
    }

    public boolean delete(int id){
        boolean flag=false;
        Optional<Reservation> e= reservationRepository.getReservation(id);
        if(e.isPresent()){
            reservationRepository.delete(e.get());
            flag=true;
        }
        return flag;
    }

    public List<CountClient> getTopClients(){
        return reservationRepository.getTopClients();
    }

    public List<Reservation> getReservationPeriod(String dateA, String dateB){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date();
        Date b = new Date();
        try{
            a = parser.parse(dateA);
            b = parser.parse(dateB);
        }catch (ParseException exception){
            exception.printStackTrace();
        }
        if (a.before(b)){
            return reservationRepository.getReservationPeriod(a, b);
        }else{
            return new ArrayList<>();
        }
    }

    public CountStatus getReservationsStatus(){
        List<Reservation> completed = reservationRepository.getReservationsByStatus("completed");

        List<Reservation> cancelled = reservationRepository.getReservationsByStatus("cancelled");

        return new CountStatus((long) completed.size(), (long) cancelled.size());
    }

}
