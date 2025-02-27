/*
 * All GTAS code is Copyright 2016, The Department of Homeland Security (DHS), U.S. Customs and Border Protection (CBP).
 * 
 * Please see LICENSE.txt for details.
 */
package gov.gtas.repository;

import gov.gtas.model.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ApisMessageRepository extends MessageRepository<ApisMessage> {
	@Query("SELECT distinct apis " + "FROM ApisMessage apis " + "left join fetch apis.phones "
			+ "left join apis.passengers pax " + "left join apis.flights f " + "where f.id = :flightId "
			+ "and :passengerId in (select p.id from apis.passengers p) "
			+ "order by apis.edifactMessage.transmissionDate " + "desc")
	List<ApisMessage> findByFlightIdAndPassengerId(@Param("flightId") Long flightId,
			@Param("passengerId") Long passengerId);

	@Query("SELECT fp.reservationReferenceNumber FROM ApisMessage apis join apis.flightPaxList fp where fp.passenger.id = :passengerId and fp.flight.id = :flightId")
	List<String> findApisRefByFlightIdandPassengerId(@Param("flightId") Long flightId,
			@Param("passengerId") Long passengerId);

	@Query("SELECT fp FROM ApisMessage apis join apis.flightPaxList fp where fp.reservationReferenceNumber = :refNumber")
	Set<FlightPax> findFlightPaxByApisRef(@Param("refNumber") String refNumber);

	@Query("SELECT fp FROM ApisMessage apis join apis.flightPaxList fp where fp.passenger.id = :passengerId and fp.flight.id = :flightId")
	List<FlightPax> findFlightPaxByFlightIdandPassengerId(@Param("flightId") Long flightId,
			@Param("passengerId") Long passengerId);

	@Query("SELECT apis FROM ApisMessage apis WHERE apis.createDate >= current_date() - 1")
	public List<Message> getAPIsByDates();

	default ApisMessage findOne(Long id) {
		return findById(id).orElse(null);
	}

	@Query("SELECT passenger from ApisMessage apismessage join apismessage.passengers passenger left join fetch passenger.flight where apismessage.id in :apisIds")
	Set<Passenger> getPassengerWithFlightInfo(@Param("apisIds") Set<Long> apisIds);
}
