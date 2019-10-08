package tech.eversoft.chartsapp.availability.application

import spock.lang.Specification
import tech.eversoft.chartsapp.availability.domain.AvailabilityId
import tech.eversoft.chartsapp.availability.domain.AvailabilityIndex
import tech.eversoft.chartsapp.availability.domain.DataDate
import tech.eversoft.chartsapp.availability.domain.DataDateId
import tech.eversoft.chartsapp.availability.domain.Location
import tech.eversoft.chartsapp.availability.domain.LocationId
import tech.eversoft.chartsapp.availability.domain.RoomStatistics

import java.time.LocalDateTime
import java.time.Month

class StringTableAvailabilityRepositoryTest extends Specification {
    def "FindByDataDateBetween"() {
        given:
        def searchedCompanyName = 'COMPANY_X'

        and:
        def stringTable = new StringTable([
                new StringRow([AVAILABILITY_ID              : '1',
                               DATA_DATE_ID                 : '10',
                               DATA_DATE                    : '5/1/19 0:00',
                               COMPANY_ID                   : '1',
                               COMPANY_NAME                 : searchedCompanyName,
                               LOCATION_ID                  : '1',
                               LOCATION_NAME                : 'L1',
                               AVAILABILITY_INDEX           : '76',
                               QTY_ROOMS                    : '50',
                               QTY_ROOM_ISSUES              : '10',
                               LAST_MONTH_AVAILABILITY_INDEX: '95']),
                new StringRow([AVAILABILITY_ID              : '2',
                               DATA_DATE_ID                 : '20',
                               DATA_DATE                    : '5/2/19 0:01',
                               COMPANY_ID                   : '2',
                               COMPANY_NAME                 : searchedCompanyName,
                               LOCATION_ID                  : '2',
                               LOCATION_NAME                : 'L2',
                               AVAILABILITY_INDEX           : '11',
                               QTY_ROOMS                    : '1',
                               QTY_ROOM_ISSUES              : '2',
                               LAST_MONTH_AVAILABILITY_INDEX: '3']),
                new StringRow([AVAILABILITY_ID              : '3',
                               DATA_DATE_ID                 : '20',
                               DATA_DATE                    : '5/2/19 0:01',
                               COMPANY_ID                   : '3',
                               COMPANY_NAME                 : 'SOME_OTHER_COMPANY_NAME',
                               LOCATION_ID                  : '2',
                               LOCATION_NAME                : 'L2',
                               AVAILABILITY_INDEX           : '11',
                               QTY_ROOMS                    : '12',
                               QTY_ROOM_ISSUES              : '72',
                               LAST_MONTH_AVAILABILITY_INDEX: '30']),
                new StringRow([AVAILABILITY_ID              : '4',
                               DATA_DATE_ID                 : '30',
                               DATA_DATE                    : '5/2/19 4:59',
                               COMPANY_ID                   : '3',
                               COMPANY_NAME                 : searchedCompanyName,
                               LOCATION_ID                  : '3',
                               LOCATION_NAME                : 'L3',
                               AVAILABILITY_INDEX           : '13',
                               QTY_ROOMS                    : '5',
                               QTY_ROOM_ISSUES              : '10',
                               LAST_MONTH_AVAILABILITY_INDEX: '35'])
        ])
        def cut = new StringTableAvailabilityRepository(stringTable)

        when:
        def result = cut.findByCompanyNameEqualAndDataDateBetween(
                'COMPANY_X',
                LocalDateTime.of(2019, Month.MAY, 2, 0, 0),
                LocalDateTime.of(2019, Month.MAY, 2, 5, 0))

        then:
        result.size() == 2
        verifyAll(result[0]) {
            id == new AvailabilityId(2)
            date == new DataDate(new DataDateId(20), LocalDateTime.of(2019, Month.MAY, 2, 0, 1))
            location == new Location(new LocationId(2), 'L2')
            currentIndex == new AvailabilityIndex(11)
            lastMonthIndex == new AvailabilityIndex(3)
            roomStatistics == new RoomStatistics(1, 2)
        }
        verifyAll(result[1]) {
            id == new AvailabilityId(4)
            date == new DataDate(new DataDateId(30), LocalDateTime.of(2019, Month.MAY, 2, 4, 59))
            location == new Location(new LocationId(3), 'L3')
            currentIndex == new AvailabilityIndex(13)
            lastMonthIndex == new AvailabilityIndex(35)
            roomStatistics == new RoomStatistics(5, 10)
        }
    }
}
