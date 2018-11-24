CREATE DATABASE company;

USE company;

CREATE TABLE department (
	dept_no VARCHAR(4) NOT NULL,
    dept_name VARCHAR(50),
    location VARCHAR(50),
    PRIMARY KEY (dept_no)
);

CREATE TABLE employee (
	emp_no INT NOT NULL,
    emp_fname VARCHAR(50),
    emp_lname VARCHAR(50),
    dept_no VARCHAR(4),
    PRIMARY KEY (emp_no),
    FOREIGN KEY (dept_no) REFERENCES department(dept_no)
);

CREATE TABLE project (
	project_no VARCHAR(5) NOT NULL,
    project_name VARCHAR (50), 
    budget INT,
    PRIMARY KEY (project_no)
);

CREATE TABLE works_on (
	emp_no INT,
    project_no  VARCHAR(5) NOT NULL,
    job VARCHAR(50),
    enter_date DATE,
    FOREIGN KEY (emp_no) REFERENCES employee(emp_no),
    FOREIGN KEY (project_no) REFERENCES project(project_no)
);
