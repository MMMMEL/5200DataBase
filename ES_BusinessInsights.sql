#Team GLoW
#PM3
USE EventSmart;

#1. What are three most popular element in each category?
SELECT *
FROM (
SELECT "Music" AS Category_Type, ElementLists.MusicID AS ElementID, COUNT(*) AS CNT
FROM ElementLists
GROUP BY ElementLists.MusicID
ORDER BY CNT DESC
LIMIT 3
) AS MUSIC

UNION ALL

SELECT *
FROM (
SELECT "Movie" AS Category_Type, ElementLists.MovieID AS ElementID, COUNT(*) AS CNT
FROM ElementLists
GROUP BY ElementLists.MovieID
ORDER BY CNT DESC
LIMIT 3
) AS MOVIE

UNION ALL 

SELECT *
FROM (
SELECT "Wine" AS Category_Type, ElementLists.WineID AS ElementID, COUNT(*) AS CNT
FROM ElementLists
GROUP BY ElementLists.WineID
ORDER BY CNT DESC
LIMIT 3
)AS WINE

UNION ALL

SELECT *
FROM (
SELECT "Restaurant" AS Category_Type, ElementLists.RestaurantID AS ElementID, COUNT(*) AS CNT
FROM ElementLists
GROUP BY ElementLists.RestaurantID
ORDER BY CNT DESC
LIMIT 3
) AS RESTAURANT

UNION ALL

SELECT *
FROM (
SELECT "Gift" AS Category_Type, ElementLists.GiftID AS ElementID, COUNT(*) AS CNT
FROM ElementLists
GROUP BY ElementLists.GiftID
ORDER BY CNT DESC
LIMIT 3
) AS GIFT;

#2. Who is the professional planner with best average review rating?
SELECT EventProducts.PlannerName AS Planner, AVG (Rating) AS AVG_REVIEW
FROM Reviews INNER JOIN EventProducts
	ON Reviews.ProductID = EventProducts.ProductID
GROUP BY EventProducts.PlannerName
ORDER BY AVG_REVIEW DESC
LIMIT 1;


#3. TOP 3 planners have the most reservation in May, 2018?
SELECT PlannerName, COUNT(*) AS MAY_RESERVATION_CNT
FROM Reservations
WHERE EventDate BETWEEN '2018-05-01 00:00:00' AND '2018-06-01 00:00:00'
GROUP BY PlannerName
ORDER BY MAY_RESERVATION_CNT DESC
LIMIT 3;

#4. Which 3 planner’s event products are the most expensive averagely?
SELECT EventProducts.PlannerName AS Planner,
AVG(
CASE
	WHEN EventProducts.PriceRange = '$' THEN 1
    WHEN EventProducts.PriceRange = '$$' THEN 2
    WHEN EventProducts.PriceRange = '$$$' THEN 3
    WHEN EventProducts.PriceRange = '$$$$' THEN 4
END ) AS AVG_PriceRange
FROM EventProducts
GROUP BY EventProducts.PlannerName
ORDER BY AVG_PriceRange DESC
LIMIT 3;

#5. What event theme is most used?
SELECT DIYEvents.Theme, COUNT(*) AS CNT
FROM DIYEvents
GROUP BY  Theme
ORDER BY CNT DESC;

#6. What is the restaurant most used by business events?
SELECT Restaurants.RestaurantID, Restaurants.RestaurantName
FROM Restaurants INNER JOIN
(
SELECT ElementLists.RestaurantID AS RESTAURANT_ID, COUNT(*) AS CNT
FROM ElementLists INNER JOIN
(
SELECT DIYEvents.ListID AS LISTID
FROM DIYEvents
WHERE DIYEvents.Theme = 'Business'
) AS BUSINESS_LIST
ON ElementLists.ListID = BUSINESS_LIST.LISTID
GROUP BY ElementLists.RestaurantID
ORDER BY CNT DESC
LIMIT 1
) AS POP_RESTAURANT
ON Restaurants.RestaurantID = POP_RESTAURANT.RESTAURANT_ID;

#7. Percent of DIYers who actually created DIY events
SELECT
100.0 * (SELECT COUNT(DISTINCT UserName) FROM DIYEvents) / 
(SELECT COUNT(1) FROM DIYers) AS DIY_Percent;

#8. Flag administrators who active participate (logged in within 1 week) in system management
SELECT UserName AS AdminName,
CASE
	WHEN DATEDIFF(NOW(), LastLogin) <= 7
	THEN 'ACTIVE'
	ELSE 'INACTIVE'
END AS Flag
FROM Administrators
ORDER BY AdminName;

#9. Assuming each DIYer do reviews their planners on their products when DIYers commit such plan, rank Planners by their gross income
SELECT ep.PlannerName AS Planner,
SUM(
	CASE
		WHEN ep.PriceRange = '$' THEN 1
	    WHEN ep.PriceRange = '$$' THEN 2
	    WHEN ep.PriceRange = '$$$' THEN 3
	    WHEN ep.PriceRange = '$$$$' THEN 4
	END
) AS GrossIncome
FROM Reviews re
INNER JOIN EventProducts ep
ON re.ProductID = ep.ProductID
GROUP BY ep.PlannerName
ORDER BY GrossIncome DESC;

#10. List top 10 of most used wines with price less than 100 dollars?
SELECT ElementLists.WineID, COUNT(*) AS CNT, Wines.Price
FROM ElementLists INNER JOIN Wines
ON ElementLists.WineID = Wines.WineID 
WHERE Wines.Price < 100
GROUP BY ElementLists.WineID
ORDER BY CNT DESC
LIMIT 10;
