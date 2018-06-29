#Team GLoW
#PM3
USE EventSmart;

#1. What are five most popular element in each category?
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

#3. Which 3 planners have the most reservation in May, 2018?
/*
SELECT Reservations.PlannerName, COUNT(*) AS CNT
FROM M201805
LEFT OUTER JOIN
(SELECT Reservations.PlannerName AS Planner, Date(EventDate)
FROM Reservations) AS RESERVS
ON M201805.day = DATE (Reservations.EventDate)
GROUP BY Reservations.PlannerName;
*/

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

#5. 


#6. What event theme is most used?
SELECT DIYEvents.Theme, COUNT(*) AS CNT
FROM DIYEvents
GROUP BY  Theme
ORDER BY CNT DESC;

#7. What is the restaurant most used by business events?
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
