USE company;

INSERT INTO department (dept_no, dept_name, location)
VALUES 
	("d1", "research", "Dallas"),
    ("d2", "accounting", "Seattle"),
    ("d3", "marketing", "Dallas");
    
    
INSERT INTO employee (emp_no, emp_fname, emp_lname, dept_no)
VALUES
	(25348, "Matthew", "Smith", "d3"),
    (10102, "Ann", "Jones", "d3"),
    (18316, "John", "Barrimore", "d1"),
    (29346, "James", "James", "d2"),
    (9031, "Elsa", "Bertoni", "d2"),
    (2581, "Elke", "Hansel", "d2"),
    (28559, "Sybill", "Moser", "d1");
    
    
INSERT INTO project (project_no, project_name, budget)
VALUES 
    ("p1", "Apollo", 120000),
    ("p2", "Gemini", 95000),
    ("p3", "Mercury", 186500);
    
    
INSERT INTO works_on (emp_no, project_no, job, enter_date)
VALUES
	(10102, "p1", "analyst", "2006-10-01"),
    (10102, "p3", "manager", "2008-01-01"),
    (25348, "p2", "clerk", "2007-02-15"),
    (18316, "p2", NULL, "2007-06-01"),
    (29346, "p2", NULL, "2006-12-15"),
    (2581, "p3", "analyst", "2007-10-15"),
    (9031, "p1", "manager", "2007-04-15"),
    (28559, "p1", NULL, "2007-08-01"),
    (28559, "p2", "clerk", "2008-02-01"),
    (9031, "p3", "clerk", "2006-11-15"),
    (29346, "p1", "clerk", "2007-01-04");
    
    