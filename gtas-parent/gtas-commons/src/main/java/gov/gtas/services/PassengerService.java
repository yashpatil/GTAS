/*
 * All GTAS code is Copyright 2016, The Department of Homeland Security (DHS), U.S. Customs and Border Protection (CBP).
 * 
 * Please see LICENSE.txt for details.
 */
package gov.gtas.services;

import static gov.gtas.constant.GtasSecurityConstants.PRIVILEGES_ADMIN_AND_VIEW_PASSENGER;
import static gov.gtas.constant.GtasSecurityConstants.PRIVILEGES_ADMIN_AND_MANAGE_CASES;
import static gov.gtas.constant.GtasSecurityConstants.PRIVILEGES_ADMIN_AND_VIEW_PASSENGER_AND_MANAGE_QUERIES;

import gov.gtas.model.*;
import gov.gtas.model.lookup.DispositionStatus;
import gov.gtas.services.dto.PassengersPageDto;
import gov.gtas.services.dto.PassengersRequestDto;
import gov.gtas.vo.passenger.CaseVo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;

public interface PassengerService {

	Passenger create(Passenger passenger);

	Passenger update(Passenger passenger);

	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	Passenger findById(Long id);

	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	Passenger findByIdWithFlightPaxAndDocuments(Long paxId);

	/*
	 * 
	 * List<Passenger> getPassengersByLastName(String lastName);
	 */

	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	List<Disposition> getPassengerDispositionHistory(Long passengerId, Long flightId);

	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	void createDisposition(DispositionData disposition, User user);

	void createDisposition(HitsSummary hit);

	void createDisposition(List<HitsSummary> hit);

	@PreAuthorize(PRIVILEGES_ADMIN_AND_MANAGE_CASES)
	List<DispositionStatus> getDispositionStatuses();

	@PreAuthorize(PRIVILEGES_ADMIN_AND_MANAGE_CASES)
	List<CaseVo> getAllDispositions();

	void createOrEditDispositionStatus(DispositionStatus ds);

	void deleteDispositionStatus(DispositionStatus ds);

	/**
	 * Gets the passengers by criteria.
	 *
	 * @param flightId
	 *            optional
	 * @param request
	 *            the request
	 * @return the passengers by criteria
	 */
	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER_AND_MANAGE_QUERIES)
	PassengersPageDto getPassengersByCriteria(Long flightId, PassengersRequestDto request);

	/**
	 * Gets the travel history.
	 *
	 * @param pId
	 *            the id
	 * @param docNum
	 *            the doc num
	 * @param docIssuCountry
	 *            the doc issu country
	 * @param docExpirationDate
	 *            the doc expiration date
	 * @return the travel history
	 */
	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	List<Flight> getTravelHistory(Long pId, String docNum, String docIssuCountry, Date docExpDate);

	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	List<Flight> getTravelHistoryByItinerary(Long pnrId, String pnrRef);

	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	List<Flight> getTravelHistoryNotByItinerary(Long pId, Long pnrId, String pnrRef);

	@PreAuthorize(PRIVILEGES_ADMIN_AND_VIEW_PASSENGER)
	List<Passenger> getBookingDetailHistoryByPaxID(Long pId);

	Set<Flight> getAllFlights(Long id);

	Set<FlightPax> findFlightPaxFromPassengerIds(List<Long> passengerIdList);

	List<Passenger> getPaxByPaxIdList(List<Long> passengerIdList);

	void setAllFlights(Set<Flight> flights, Long id);

	void SetSingleFlight(Flight f, Long id);

	List<Flight> getFlightsByIdList(List<Long> flightIdList);

	Set<Passenger> getPassengersForFuzzyMatching(List<MessageStatus> messageIds);

	Map<Long, List<HitsSummary>> getHitsSummaryMappedToPassengerIds(Set<Long> passengerIds);

	Map<Long, Set<Document>> getDocumentMappedToPassengerIds(Set<Long> passengerIds);
}
