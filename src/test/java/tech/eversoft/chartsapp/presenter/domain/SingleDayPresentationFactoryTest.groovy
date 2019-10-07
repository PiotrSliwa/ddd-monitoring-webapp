package tech.eversoft.chartsapp.presenter.domain

import spock.lang.Specification
import tech.eversoft.chartsapp.availability.domain.*

import java.time.LocalDateTime
import java.time.Month

class SingleDayPresentationFactoryTest extends Specification {
    def "Create"() {
        given:
        def availabilityRepository = Mock(AvailabilityRepository)
        def cut = new SingleDayPresentationFactory(availabilityRepository: availabilityRepository)

        and:
        def now = LocalDateTime.of(2020, Month.SEPTEMBER, 15, 10, 14)
        def midnightToday = LocalDateTime.of(2020, Month.SEPTEMBER, 15, 0, 0)

        and:
        def poland = new Location(new LocationId(1), "Poland")
        def polishCurrentIndex = new AvailabilityIndex(20)
        def polishLastMonthIndex = new AvailabilityIndex(10)
        def polishRoomStatistics = new RoomStatistics(102, 550)
        def germany = new Location(new LocationId(2), "Germany")
        def germanCurrentIndexA = new AvailabilityIndex(50)
        def germanCurrentIndexB = new AvailabilityIndex(90)
        def germanLastMonthIndexA = new AvailabilityIndex(14)
        def germanLastMonthIndexB = new AvailabilityIndex(66)
        def germanRoomStatisticsA = new RoomStatistics(33, 192)
        def germanRoomStatisticsB = new RoomStatistics(153, 115)

        and:
        def availabilities = [
                Mock(Availability) {
                    getCurrentIndex() >> polishCurrentIndex
                    getLastMonthIndex() >> polishLastMonthIndex
                    getRoomStatistics() >> polishRoomStatistics
                    getLocation() >> poland
                },
                Mock(Availability) {
                    getCurrentIndex() >> germanCurrentIndexA
                    getLastMonthIndex() >> germanLastMonthIndexA
                    getRoomStatistics() >> germanRoomStatisticsA
                    getLocation() >> germany
                },
                Mock(Availability) {
                    getCurrentIndex() >> germanCurrentIndexB
                    getLastMonthIndex() >> germanLastMonthIndexB
                    getRoomStatistics() >> germanRoomStatisticsB
                    getLocation() >> germany
                }
        ]

        when:
        def result = cut.create(now)

        then:
        1 * availabilityRepository.findByDataDateBetween(midnightToday, now) >> availabilities

        and:
        verifyAll(result.total) {
            index.value == average(polishCurrentIndex, germanCurrentIndexA, germanCurrentIndexB)
            lastMonthDifference.difference.value == average(polishCurrentIndex, germanCurrentIndexA, germanCurrentIndexB) - average(polishLastMonthIndex, germanLastMonthIndexA, germanLastMonthIndexB)
        }
        result.locationAvailabilities.size() == 2
        verifyAll(result.locationAvailabilities[poland]) {
            index == polishCurrentIndex
            lastMonthDifference == new AvailabilityDifference(polishLastMonthIndex, polishCurrentIndex)
            roomStatistics == polishRoomStatistics
        }
        verifyAll(result.locationAvailabilities[germany]) {
            index.value == average(germanCurrentIndexA, germanCurrentIndexB)
            lastMonthDifference == availabilityDifferenceFromInt(average(germanLastMonthIndexA, germanLastMonthIndexB), average(germanCurrentIndexA, germanCurrentIndexB))
            roomStatistics.quantityOfRooms == germanRoomStatisticsA.quantityOfRooms + germanRoomStatisticsB.quantityOfRooms
            roomStatistics.quantityOfRoomIssues == germanRoomStatisticsA.quantityOfRoomIssues + germanRoomStatisticsB.quantityOfRoomIssues
        }
    }

    Integer average(AvailabilityIndex... indexes) {
        indexes.sum { it.value } / indexes.size()
    }

    Integer average(Integer... indexes) {
        indexes.sum() / indexes.size()
    }

    AvailabilityDifference availabilityDifferenceFromInt(Integer lastMonthIntegerIndex, Integer currentIntegerIndex) {
        new AvailabilityDifference(new AvailabilityIndex(lastMonthIntegerIndex), new AvailabilityIndex(currentIntegerIndex))
    }
}
