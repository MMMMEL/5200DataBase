#3. Which 3 planners have the most reservation in May, 2018?
SELECT PlannerName, COUNT(*) AS MAY_RESERVATION_CNT
FROM Reservations
WHERE EventDate BETWEEN '2018-05-01 00:00:00' AND '2018-05-31 23:59:59'
GROUP BY PlannerName
HAVING MAY_RESERVATION_CNT DESC
LIMIT 3;

#8. Percent of DIYers who actually created DIY events
SELECT
100.0 * (SELECT COUNT(DISTINCT UserName) FROM DIYEvents) / 
(SELECT COUNT(1) FROM DIYers)

#9. Flag  administrators who active participate (logged in within 1 week) in system management
SELECT
UserName AS AdminName,
CASE
	WHEN DATEDIFF(NOW(), LastLogin) <= 7
	THEN 'ACTIVE'
	ELSE 'INACTIVE'
END AS Flag
FROM Administrators
ORDER BY AdminName

#10. Assuming each DIYer do reviews their planners on their products when DIYers commit such plan, rank Planners by their gross income
SELECT ep.PlannerName AS Planner
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
ORDER BY TotalIncome DESC