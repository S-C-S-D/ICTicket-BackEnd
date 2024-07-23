package com.sparta.icticket.admin.venue;

import com.sparta.icticket.venue.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminVenueRepository extends JpaRepository<Venue, Long> {
}
