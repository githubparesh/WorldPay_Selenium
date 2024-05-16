Feature: Parking Cost Calculation

  @Test
  Scenario: Calculate Valet Parking Cost for One Day
    Given user is on parking cost calculator homepage and select the Parking lot as "Valet Parking"
    When user enters an entryDate as "6/16/2024" and entryTime as "12:00" "AM"
    Then user enters an exitDate as "6/17/2024" and exitTime as "12:00" "AM"
    And the parking cost is calculated as "$ 18.00"

  @Test
  Scenario Outline: Calculate Parking Cost for <ParkingLot>
    Given user is on parking cost calculator homepage and select the Parking lot as "<ParkingLot>"
    When user enters an entryDate as "<EntryDate>" and entryTime as "<EntryTime>" "<EntryAMPM>"
    Then user enters an exitDate as "<LeavingDate>" and exitTime as "<LeavingTime>" "<LeavingAMPM>"
    And the parking cost is calculated as "<ExpectedCost>"

    Examples:
      | ParkingLot                | EntryDate  | EntryTime | EntryAMPM | LeavingDate | LeavingTime | LeavingAMPM | ExpectedCost |
      | Valet Parking             | 06/17/2024 | 12:00     | AM        | 06/17/2024  | 12:00       | AM          | $ 12.00      |
      | Short-Term Parking        | 06/17/2024 | 12:00     | PM        | 06/18/2024  | 12:00       | PM          | $ 24.00      |
      | Long-Term Garage Parking  | 06/17/2024 | 12:00     | PM        | 06/18/2024  | 12:00       | PM          | $ 12.00      |
      | Long-Term Surface Parking | 06/17/2024 | 12:00     | PM        | 06/18/2024  | 12:00       | PM          | $ 10.00      |
      | Economy Parking           | 06/17/2024 | 12:00     | PM        | 06/18/2024  | 12:00       | PM          | $ 9.00       |
