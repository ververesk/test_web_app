insert into category (name)
values
    ('food'),
	('clothes'),
	('utilities payments'),
	('entertainment'),
	('communal payments'),
	('education')
	;

insert into category (name)
values
	('travel')
	;
	

select * from category;

select * from expense;

insert into expense (name, created_at, category, amount)
values
    ('pizza', date '2021-02-23', 1, 190),
	('jacket', date '2021-02-15', 2, 590),
	('dress', date '2021-03-15', 2, 450),
	('credit', date '2021-03-15', 3, 350),
	('pasta', date '2021-04-15', 1, 50),
	('gaz', date '2021-05-15', 5, 140),
	('udemy', date '2021-06-15', 6, 240),
	('cafe', date '2021-06-15', 4, 150),
	('car repairs', date '2021-06-23', null, 550)
	;

insert into expense (name, created_at, category, amount)
values
	('udemy', date '2021-06-15', 6, 240);

select expense.name, category.name from expense join category on (expense.category=category.id) where category.name='clothes'
and 
expense.created_at between '2021-01-01' and CURRENT_DATE;

--Посчитать и вывести общую сумму расходов 
select sum(e.amount) from expense e ;

--Посчитать и вывести категории с наибольшей суммой расходов 
select c.name, max(e.amount) from expense e join category c on (e.category=c.id)
GROUP BY c.name
ORDER BY max(e.amount) desc 
limit 1;


select e.created_at, max(e.amount) from expense e
GROUP BY e.created_at
ORDER by e.created_at;