Introduction

Automate a website and fetch data to store in a JSON file format

Scenario

1.Go to this website and click on "Hockey Teams: Forms, Searching and Pagination"

1.01 Iterate through the table and collect the Team Name, Year and Win % for the teams with Win % less than 40% (0.40)

1.02 Iterate through 4 pages of this data and store it in a ArrayList

1.03 Convert the ArrayList object to a JSON file named hockey-team-data.json. Feel free to use Jackson library. (In the example, Maven is used, but you can resolve dependencies similarly by copying Gradle import from Maven Central). Each Hashmap object should contain:

  1.Epoch Time of Scrape

  2.Team Name

  3.Year

  4.Win %

  2.Go to this website and click on "Oscar Winning Films"

  2.01 Click on each year present on the screen and find the top 5 movies on the list - store in an ArrayList.

  2.02 Keep a Boolean variable "isWinner" which will be true only for the best picture winner of that year.

  2.03 Keep a variable to maintain the year from which the data is scraped

  2.04 Convert the ArrayList object to a JSON file named oscar-winner-data.json. Each HashMap object should contain:

  1.Epoch Time of Scrape

  2.Year

  3.Title

  4.Nomination

  5.Awards

  6.isWinner

  2.05 Store the file in the output folder in the root directory. Assert using TestNG that the file is present and not empty
