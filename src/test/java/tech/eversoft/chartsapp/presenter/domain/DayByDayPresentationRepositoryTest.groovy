package tech.eversoft.chartsapp.presenter.domain

import spock.lang.Specification
import tech.eversoft.chartsapp.availability.domain.Availability
import tech.eversoft.chartsapp.availability.domain.AvailabilityIndex
import tech.eversoft.chartsapp.availability.domain.AvailabilityRepository
import tech.eversoft.chartsapp.availability.domain.DataDate
import tech.eversoft.chartsapp.availability.domain.DataDateId

import java.time.LocalDateTime
import java.time.Month

class DayByDayPresentationRepositoryTest extends Specification {
    def "Create"() {
        given:
        def availabilityRepository = Mock(AvailabilityRepository)
        def cut = new DayByDayPresentationRepository(availabilityRepository: availabilityRepository)

        and:
        def companyName = 'CompanyName'

        and:
        def now = LocalDateTime.of(2020, Month.SEPTEMBER, 15, 10, 14)
        def threeDaysAgo = LocalDateTime.of(2020, Month.SEPTEMBER, 12, 10, 14)
        def midnightThreeDaysAgo = LocalDateTime.of(2020, Month.SEPTEMBER, 12, 0, 0)

        and:
        def availabilities = [
                Mock(Availability) {
                    getDate() >> new DataDate(new DataDateId(1), midnightThreeDaysAgo.plusHours(5))
                    getCurrentIndex() >> new AvailabilityIndex(10)
                },
                Mock(Availability) {
                    getDate() >> new DataDate(new DataDateId(2), midnightThreeDaysAgo.plusHours(25))
                    getCurrentIndex() >> new AvailabilityIndex(15)
                },
                Mock(Availability) {
                    getDate() >> new DataDate(new DataDateId(3), midnightThreeDaysAgo.plusHours(26))
                    getCurrentIndex() >> new AvailabilityIndex(20)
                }
        ]

        when:
        def result = cut.findByCompanyNameEqualAndDateBetween(companyName, threeDaysAgo, now)

        then:
        1 * availabilityRepository.findByCompanyNameEqualAndDataDateBetween(companyName, midnightThreeDaysAgo, now) >> availabilities

        and:
        result.dayByDayAvailabilities == [
                new AvailabilityIndex(10),
                new AvailabilityIndex(average(15, 20)),
                null,
                null
        ]
    }

    int average(int... vals) {
        vals.sum() / vals.size()
    }
}
