CREATE TABLE departments (
	id UUID PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

CREATE TABLE employees (
	id UUID PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	email VARCHAR(40) NOT NULL,
	phone VARCHAR(15) NOT NULL,
	post VARCHAR(20) NOT NULL,
	department_id UUID NOT NULL,
    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-----------------------------------------------------------

WITH dept_ids AS (
    INSERT INTO departments (id, name)
    VALUES
        (gen_random_uuid(), 'hr'),
        (gen_random_uuid(), 'finance'),
        (gen_random_uuid(), 'marketing'),
        (gen_random_uuid(), 'operations'),
        (gen_random_uuid(), 'it')
        RETURNING id
)

INSERT INTO employees
VALUES
    (gen_random_uuid(),'Alice Johnson','alice.johnson@example.com','555-0101','Developer', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Bob Smith','bob.smith@example.com','555-0102','Manager', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Carol Williams','carol.williams@example.com','555-0103','Designer', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'David Brown','david.brown@example.com','555-0104','Analyst', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Eva Green','eva.green@example.com','555-0105','Tester', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Frank White','frank.white@example.com','555-0106','HR Specialist', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Grace Black','grace.black@example.com','555-0107','Sales Representative', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Hank Red','hank.red@example.com','555-0108','Marketing Executive', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Ivy Blue','ivy.blue@example.com','555-0109','Support Staff', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1)),
    (gen_random_uuid(),'Jack Gray','jack.gray@example.com','555-0110','Admin Assistant', (SELECT id FROM dept_ids ORDER BY random() LIMIT 1));