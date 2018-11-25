USE company;

#1 Departments names and numbers located in Dallas.
SELECT dept_no, dept_name 
FROM department
WHERE location="Dallas";

#2 Full data about all departments
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

#4 Select names, surnames and employee numbers for those who have second letter of name "a"
SELECT emp_fname, emp_lname, emp_no
FROM employee
WHERE emp_fname LIKE "_a%";

#5 Select project numbers and jobs that in this project. Don't select unknown jobs.
SELECT project.project_no, job
FROM project 
	JOIN works_on
	ON project.project_no = works_on.project_no
WHERE NOT job IS NULL;

#6 Count different job types at each project.
SELECT project.project_no, project_name, job_num 
FROM project
	INNER JOIN (
		SELECT project_no, job, COUNT(job) AS job_num
        FROM works_on
        GROUP BY project_no
    ) job_types
    ON project.project_no = job_types.project_no;
    
    
#7 All data about departments whose location starts with a character in the range from C to F 
SELECT *
FROM department
WHERE location >= "C" AND location < "G";


#8 Select full data about employees whose number is not equal 10102 and 9031
SELECT * 
FROM employee
WHERE emp_no <> 10102 AND emp_no <> 9031;


#9 Напишіть запит для обчислення суми всіх бюджетів всіх проектів
SELECT SUM(budget) AS total
FROM project;

#10	Напишіть запит для отримання номерів працівників, які працюють над проектом p1 і/або проектом p2
SELECT DISTINCT emp_no
FROM works_on
WHERE project_no = "p1" OR project_no = "p2";

#11	Напишіть запит для отримання повних даних про працівників, чиї номери рівні 29346, 28559 або 25348
SELECT * 
FROM employee
WHERE emp_no IN (29346, 28559, 25348);


#12	Напишіть запит для виведення (по одному разу) тих робіт з таблиці works_on, які починаються з букви M.
SELECT DISTINCT job
FROM works_on
WHERE job LIKE "m%";


#13	Напишіть запит для отримання списку всіх різних робіт по кожному проекту
SELECT DISTINCT project.project_no, project_name, job
FROM project 
	JOIN works_on
    ON project.project_no = works_on.project_no
WHERE job IS NOT NULL;


#14	Напишіть запит для обчислення кількості робіт у всіх проектах (наприклад, скільки у всіх проектах є менеджерів, клерків і т.д.)
SELECT job, COUNT(job)
FROM works_on
WHERE job IS NOT NULL
GROUP BY job;


#15	Напишіть запит для отримання номерів і прізвищ всіх працівників, що не працюють у відділі d2
SELECT emp_no, emp_fname, emp_lname
FROM employee
WHERE dept_no <> "d2";


#16	Напишіть запит для отримання назв всіх проектів з бюджетом, меншим ніж $100 000 і більшим, ніж $150 000
SELECT project_name
FROM project
WHERE budget NOT BETWEEN 100000 AND 150000;

#17	Напишіть запит для отримання імен і прізвищ працівників, що працюють у відділі Research
SELECT emp_fname, emp_lname 
FROM employee 
	JOIN department
    ON employee.dept_no = department.dept_no
WHERE dept_name = "research";


#18	Напишіть запит для отримання всіх даних про працівника на посаді менеджера, який останнім отримав цю посаду
SELECT * 
FROM employee 
	JOIN (
		SELECT emp_no, MAX(enter_date) AS become_manager
		FROM works_on
		WHERE job = "manager") last_manager
	ON employee.emp_no = last_manager.emp_no;


#19	Напишіть запит для отримання назв і бюджетів всіх проектів з бюджетом в діапазоні від $95 000 до $120 000 включно
SELECT project_name, budget
FROM project
WHERE budget BETWEEN 95000 AND 120000;


#20	Напишіть запит для отримання переліку всіх різних робіт для всіх працівників
SELECT employee.emp_no, emp_fname, emp_lname, job
FROM employee
	JOIN works_on
    ON employee.emp_no = works_on.emp_no
WHERE job IS NOT NULL;


