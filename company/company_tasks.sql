USE company;

-- #1 Departments names and numbers located in Dallas.
SELECT dept_no, dept_name 
FROM department
WHERE location="Dallas";

-- #2 Full data about all departments
SELECT * 
FROM department
	INNER JOIN (
		-- select amount of employees in every department
		SELECT dept_no, COUNT(*) AS empl_num
        FROM employee
        GROUP BY dept_no) dept_stuff
    ON department.dept_no = dept_stuff.dept_no;

/*
#3 Get employee numbers, project numbers and job name for workers, who spend NOT most time on one of these projects.

Напишіть запит для отримання номерів працівників, номерів проектів і назв робіт для працівників, 
які витратили НЕ найбільше часу для роботи над одним з цих проектів
*/
SELECT employee.emp_no, project_no, job 
FROM employee 
	INNER JOIN works_on
    ON employee.emp_no = works_on.emp_no
WHERE employee.emp_no <> (
	-- select who spend most time on one of these projects
	SELECT emp_no FROM (
		SELECT emp_no, MIN(enter_date)
		FROM works_on
	) longest_at_project
);

-- #4 Select names, surnames and employee numbers for those who have second letter of name "a"
SELECT emp_fname, emp_lname, emp_no
FROM employee
WHERE emp_fname LIKE "_a%";

-- #5 Select project numbers and jobs that in this project. Don't select unknown jobs.
SELECT project.project_no, job
FROM project 
	JOIN works_on
	ON project.project_no = works_on.project_no
WHERE NOT job IS NULL;

-- #6 Count different job types at each project.
SELECT project.project_no, project_name, job_num 
FROM project
	INNER JOIN (
		SELECT project_no, job, COUNT(job) AS job_num
        FROM works_on
        GROUP BY project_no
    ) job_types
    ON project.project_no = job_types.project_no;
    
    
-- #7 All data about departments whose location starts with a character in the range from C to F 
SELECT *
FROM department
WHERE location >= "C" AND location < "G";


-- #8 Select full data about employees whose number is not equal 10102 and 9031
SELECT * 
FROM employee
WHERE emp_no <> 10102 AND emp_no <> 9031;


-- #9 Напишіть запит для обчислення суми всіх бюджетів всіх проектів
SELECT SUM(budget) AS total
FROM project;

-- #10	Напишіть запит для отримання номерів працівників, які працюють над проектом p1 і/або проектом p2
SELECT DISTINCT emp_no
FROM works_on
WHERE project_no = "p1" OR project_no = "p2";

-- #11	Напишіть запит для отримання повних даних про працівників, чиї номери рівні 29346, 28559 або 25348
SELECT * 
FROM employee
WHERE emp_no IN (29346, 28559, 25348);


-- #12	Напишіть запит для виведення (по одному разу) тих робіт з таблиці works_on, які починаються з букви M.
SELECT DISTINCT job
FROM works_on
WHERE job LIKE "m%";


-- #13	Напишіть запит для отримання списку всіх різних робіт по кожному проекту
SELECT DISTINCT project.project_no, project_name, job
FROM project 
	JOIN works_on
    ON project.project_no = works_on.project_no
WHERE job IS NOT NULL;


-- #14	Напишіть запит для обчислення кількості робіт у всіх проектах (наприклад, скільки у всіх проектах є менеджерів, клерків і т.д.)
SELECT job, COUNT(job)
FROM works_on
WHERE job IS NOT NULL
GROUP BY job;


-- #15	Напишіть запит для отримання номерів і прізвищ всіх працівників, що не працюють у відділі d2
SELECT emp_no, emp_fname, emp_lname
FROM employee
WHERE dept_no <> "d2";


-- #16	Напишіть запит для отримання назв всіх проектів з бюджетом, меншим ніж $100 000 і більшим, ніж $150 000
SELECT project_name
FROM project
WHERE budget NOT BETWEEN 100000 AND 150000;

-- #17	Напишіть запит для отримання імен і прізвищ працівників, що працюють у відділі Research
SELECT emp_fname, emp_lname 
FROM employee 
	JOIN department
    ON employee.dept_no = department.dept_no
WHERE dept_name = "research";


-- #18	Напишіть запит для отримання всіх даних про працівника на посаді менеджера, який останнім отримав цю посаду
SELECT * 
FROM employee 
	JOIN (
		SELECT emp_no, MAX(enter_date) AS become_manager
		FROM works_on
		WHERE job = "manager") last_manager
	ON employee.emp_no = last_manager.emp_no;


-- #19	Напишіть запит для отримання назв і бюджетів всіх проектів з бюджетом в діапазоні від $95 000 до $120 000 включно
SELECT project_name, budget
FROM project
WHERE budget BETWEEN 95000 AND 120000;


-- #20	Напишіть запит для отримання переліку всіх різних робіт для всіх працівників
SELECT employee.emp_no, emp_fname, emp_lname, job
FROM employee
	JOIN works_on
    ON employee.emp_no = works_on.emp_no
WHERE job IS NOT NULL;

/*
-- #21	Напишіть запит для отримання списку працівників, що працюють над проектом p1 і відповідних робіт, які вони виконують. 
    Якщо робота для певного з працівників невідома, то у відповідній колонці вивести запис «Job unknown»*/
SELECT employee.*, 
	CASE
		WHEN job IS NULL THEN "Job unknown"
        ELSE job
	END AS job
FROM employee
	INNER JOIN works_on
    ON employee.emp_no = works_on.emp_no
WHERE project_no = "p1";

/*
#22	Напишіть запит для отримання назв всіх проектів з бюджетом, більшим ніж 60 000 фунтів стерлінгів 
    (при поточному курсі за 1 долар 0,51 фунт стерлінгів) */
SELECT * 
FROM project
WHERE budget * 0.51 > 60000;


-- #23	Напишіть запит для отримання номерів працівників і відповідних номерів проектів для невідомих робіт, пов’язаних з проектом p2
SELECT emp_no, project_no
FROM works_on
WHERE job IS NULL AND emp_no IN (
	SELECT emp_no 
    FROM works_on
    WHERE project_no = "p2"
    );
    
    
-- #24	Напишіть запит для обчислення середнього значення всіх бюджетів, більших за $100 000
SELECT AVG(budget) 
FROM project
WHERE budget > 100000;


-- #25	Напишіть запит для отримання номера та прізвища працівника з найменшим номером
SELECT emp_no, emp_lname
FROM employee
WHERE emp_no = (
	SELECT MIN(emp_no) 
    FROM employee 
    );


-- #26	Напишіть запит для отримання прізвищ всіх працівників, що працюють над проектом Apollo
SELECT emp_lname 
FROM employee
	JOIN works_on
    ON employee.emp_no = works_on.emp_no
		JOIN project
		ON works_on.project_no = project.project_no
WHERE project_name = "Apollo";
    
/*    
-- #27	Напишіть запит для отримання номерів відділів, імен та прізвищ тих працівників, у яких номери менші, 
	ніж 20 000, впорядкувавши результат за зростанням спершу по прізвищах, потім по іменах. */
SELECT dept_no, emp_fname, emp_lname
FROM employee
WHERE emp_no < 20000
ORDER BY emp_fname, emp_lname ASC;

    
    
-- #28	Напишіть запит для отримання прізвищ та імен всіх працівників, з номером працівника, більшим або рівним 15 000
SELECT emp_fname, emp_lname
FROM employee
WHERE emp_no >= 15000;

/*    
-- #29	Напишіть запит для отримання номерів всіх проектів та кількості працівників, які працюють над цими проектами, 
    у порядку спадання кількості працівників. */
SELECT project.project_no, project_name, budget, COUNT(emp_no) AS emp_amount
FROM project
	JOIN works_on
    ON project.project_no = works_on.project_no
GROUP BY works_on.project_no;


-- #30	Напишіть запит для отримання повних даних про всіх працівників, чиє відділення знаходиться у Далласі
SELECT employee.*, dept_name, location
FROM employee
	JOIN department
    ON department.dept_no = employee.dept_no
WHERE location = "Dallas";

/*
-- #31	Напишіть запит для отримання номерів, імен та прізвищ всіх працівників, чиї прізвища 
    не починаються з букв J, K, L, M, N або О, і чиї імена не починаються з букв E або Z. */
SELECT emp_no, emp_fname, emp_lname
FROM employee
WHERE NOT (emp_lname >= "J" AND emp_lname < "P")
	AND NOT (emp_fname > "E" AND emp_fname < "F" OR emp_fname > "Z") ;
    
SELECT emp_no, emp_fname, emp_lname
FROM employee
WHERE emp_lname REGEXP "^[^J-O].*"
	AND emp_fname REGEXP "^[^EZ].*";


-- #32	Напишіть запит для отримання повних відомостей про працівників, чиї імена не закінчуються символом «n»
SELECT *
FROM employee
WHERE emp_fname REGEXP ".*[^n]$";


-- #33	Напишіть запит для отримання номерів проектів, над якими працює менше ніж чотири працівники
SELECT project.project_no, project_name, amount
FROM project
	JOIN (
		SELECT project_no, COUNT(emp_no) AS amount
		FROM works_on
		GROUP BY project_no
        HAVING amount < 4
	) project_emp_num
    ON project.project_no = project_emp_num.project_no;
    
    
-- #34	Напишіть запит для отримання всіх даних про роботи, які почалися у 2007 році
SELECT *
FROM works_on
WHERE enter_date LIKE "2007-%";