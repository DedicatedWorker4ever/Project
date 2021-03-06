package com.kea.cinemaxx.repositiories;

import com.kea.cinemaxx.entities.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Integer> {

    List<Seat> findAll();
//    //Again, they probably need to be entities but I'm not sure what the best practice is
//    List<Seat> findSeatByHall(int hallId);
//    List<Seat> findSeatByHallandReserved(int hallId, boolean reserved);
//    List<Seat> findSeatByRowandHall(int hallId, int row);
//    List<Seat> findSeatByColumnandHall(int hallId, char column);

}