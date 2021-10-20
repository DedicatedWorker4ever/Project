package com.kea.cinemaxx.services;

<<<<<<< Updated upstream
public class TicketService {
=======
import com.kea.cinemaxx.dtos.MovieDTO;
import com.kea.cinemaxx.dtos.SeatDTO;
import com.kea.cinemaxx.dtos.TicketDTO;
import com.kea.cinemaxx.dtos.UserDTO;
import com.kea.cinemaxx.entities.Seat;
import com.kea.cinemaxx.entities.Ticket;
import com.kea.cinemaxx.repositiories.SeatRepository;
import com.kea.cinemaxx.repositiories.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    final ResponseStatusException UNAUTHORIZED_USER = new ResponseStatusException(
            HttpStatus.UNAUTHORIZED, "User does not have permissions to perform this action."
    );

    public List<TicketDTO> getTickets() {
        //and other conditions maybe? the admin should be able to see these?
        return TicketDTO.TicketDTOSfromTicket(ticketRepository.findAll());
    }

    public TicketDTO getTicket(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        return new TicketDTO(ticket);
    }

    // create booking (Sam)
    public TicketDTO addTicket(TicketDTO newTicket){
        Ticket ticketToMake = TicketDTO.ticketFromTicketDTO(newTicket);
        return new TicketDTO(ticketRepository.save(ticketToMake));
    }

    public boolean isTicketFree(int screeningId, int seatId){
        Ticket t = ticketRepository.findTicketByScreening_ScreeningIdAndSeat_SeatId(screeningId, seatId);
        return t==null ? true : false;
    }

    // edit booking (Chia)
    public TicketDTO editTicket(TicketDTO ticketToEdit, UserDTO ticketOwnerOrAdmin, int ticketId) {

        Ticket oldTicket = ticketRepository.findById(ticketId).orElseThrow();

        if (ticketOwnerOrAdmin.isAdmin() || ticketOwnerOrAdmin.getUserId() == oldTicket.getUser().getUserId()) {

            Ticket newTicket = ticketRepository.findBySeat(ticketToEdit.getSeat());

            ticketToEdit.setPurchased(oldTicket.isPurchased());
            ticketToEdit.setUser(oldTicket.getUser());
            ticketToEdit.setScreening(oldTicket.getScreening());

            oldTicket.resetTicket(oldTicket.getSeat());

            //ticket 1 paid | seat 1
            //ticket 2 not paid | seat 2
            //ticket 1 paid | seat 1 -> 2 => ticket 1.reset(), ticket 2 paid=true, user=this, seat=2(stays the same)

            return new TicketDTO(ticketRepository.save(oldTicket));
        }
        else throw UNAUTHORIZED_USER; //exception declared above
    }

    // cancel booking (Chia)
    public void deleteTicket(UserDTO ticketOwnerOrAdmin, int ticketId) {

        Ticket ticketOrg = ticketRepository.findById(ticketId).orElseThrow();
        if (ticketOwnerOrAdmin.isAdmin() || ticketOwnerOrAdmin.getUserId() == ticketOrg.getUser().getUserId()) {

            ticketOrg.setPurchased(false);
            ticketOrg.setUser(null);
            TicketDTO newTicket = new TicketDTO(ticketRepository.save(ticketOrg));
//            ticketRepository.deleteById(ticketId); // can't actually be deleted like that!

        }

        else throw UNAUTHORIZED_USER; //exception declared above
    }

    public List<TicketDTO> getSeatsByBooking(boolean purchased, int screeningId) {
//        Ticket t = ticketRepository.findByPurchased(purchased);
//        List<TicketDTO> out = null;
//        for (TicketDTO ticketDTO : t){
//            if (ticketDTO.getScreening().equals(screeningId)){
//                out.add(ticketDTO);
//            }
//        }
//        return out;
          return TicketDTO.TicketDTOSfromTicket(ticketRepository.findTicketByPurchasedAndScreening_ScreeningId(purchased, screeningId));
    }
>>>>>>> Stashed changes
}
