--Student ID: 29825660
--Student Fullname: ARVIND YOGESH ATTUR RAMESH
--Tutor Name: CHANDRESH VIRADIA

/*  --- COMMENTS TO YOUR MARKER --------

	

	In branch_manager_collection table, a constraint bl_collection_type_check is added to check the book collection type.
	Constraint checks FI= FICTION, NF=NON-FICTION, F=FULL collection
	
	In book_copy table, bc_status_chk constraint is added to check the book condition.
	Constraint checks G=Good, L=Lost, D=Damaged
	
	Before dropping all the tables, In 4.3, branch_manager_collection table need to be dropped first.as it is created in the live database.
	

    

*/

--Q1
/*
1.1
Add to your solutions script, the CREATE TABLE and CONSTRAINT definitions which are missing from the 
FIT9132_2018S2_A2_Schema_Start.sql script. You MUST use the relation and attribute names shown in the data model above 
to name tables and attributes which you add.
*/


CREATE TABLE book_copy(
	branch_code NUMERIC(2) NOT NULL,
	bc_id NUMERIC(6) NOT NULL,
	bc_purchase_price NUMERIC(7,2) NOT NULL,
	bc_reserve_flag CHAR(1) NOT NULL,
	book_call_no VARCHAR(20) NOT NULL
);


COMMENT ON COLUMN book_copy.branch_code IS
    'Branch Number';

COMMENT ON COLUMN book_copy.bc_id IS
	'Unique Book Copy Number';

COMMENT ON COLUMN book_copy.bc_purchase_price IS
	'Purchase Price of the Book Copy';
	
COMMENT ON COLUMN book_copy.bc_reserve_flag IS
	'Flag value of the reserved book copy';

COMMENT ON COLUMN book_copy.book_call_no IS
	'Book Call Number';
	
ALTER TABLE book_copy ADD CONSTRAINT book_copy_pk PRIMARY KEY( branch_code, bc_id ); 
																

																	
CREATE TABLE loan(
	branch_code NUMERIC(2) NOT NULL,
	bc_id NUMERIC(6) NOT NULL,
	loan_date_time Date NOT NULL,
	loan_due_date Date NOT NULL,
	loan_actual_return_date Date,
	bor_no NUMERIC(6) NOT NULL
);

COMMENT ON COLUMN loan.branch_code IS
	'Branch Number';

COMMENT ON COLUMN loan.bc_id IS
	'Book Copy Identifier';
	
COMMENT ON COLUMN loan.loan_date_time IS
	'Loan Date and Time of the Book';

COMMENT ON COLUMN loan.loan_due_date IS
	'Loan Due Date of the Book';

COMMENT ON COLUMN loan.loan_actual_return_date IS
	'Loan actual return date of the Bookj';

COMMENT ON COLUMN loan.bor_no IS
	'Borrower Number';

ALTER TABLE loan ADD CONSTRAINT loan_pk PRIMARY KEY ( branch_code, 
													  bc_id,
													  loan_date_time);
													  
CREATE TABLE reserve(
	branch_code NUMERIC(2) NOT NULL,
	bc_id NUMERIC(6) NOT NULL,
	reserve_date_time_placed Date NOT NULL,
	bor_no Numeric(6) NOT NULL
);

COMMENT ON COLUMN reserve.branch_code IS
	'Branch Number';

COMMENT ON COLUMN reserve.bc_id IS
	'Book Copy Identifier';

COMMENT ON COLUMN reserve.reserve_date_time_placed IS
	'Reserved Date and Time of the book copy';

COMMENT ON COLUMN reserve.bor_no IS
	'Borrower Number';
	
ALTER TABLE reserve ADD CONSTRAINT reserve_pk PRIMARY KEY ( branch_code, 
															 bc_id,
															 reserve_date_time_placed);

ALTER TABLE book_copy
	ADD CONSTRAINT call_no_fk FOREIGN KEY ( book_call_no )
	REFERENCES book_detail( book_call_no );

ALTER TABLE book_copy
	ADD CONSTRAINT bc_branch_code_fk FOREIGN KEY( branch_code )
	REFERENCES branch( branch_code );
	
ALTER TABLE reserve
	ADD CONSTRAINT bcid_fk FOREIGN KEY( branch_code, bc_id )
	REFERENCES book_copy ( branch_code, bc_id );

ALTER TABLE reserve
	ADD CONSTRAINT re_bor_no FOREIGN KEY ( bor_no )
	REFERENCES borrower ( bor_no );

ALTER TABLE loan
	ADD CONSTRAINT lo_bcid_fk FOREIGN KEY ( branch_code, bc_id )
	REFERENCES book_copy ( branch_code, bc_id );


ALTER TABLE loan
	ADD CONSTRAINT lo_bor_no_fk FOREIGN KEY ( bor_no )
	REFERENCES borrower ( bor_no );


     
/*
1.2
Add the full set of DROP TABLE statements to your solutions script. In completing this section you must not use the CASCADE 
CONSTRAINTS clause as part of your DROP TABLE statement (you should include the PURGE clause).
 
*/

   DROP TABLE reserve PURGE;

	DROP TABLE loan PURGE;

	DROP TABLE borrower PURGE;

	DROP TABLE bd_author PURGE;

	DROP TABLE bd_subject PURGE;

    DROP TABLE book_copy PURGE;

	DROP TABLE author PURGE;

	DROP TABLE subject PURGE;
	
	DROP TABLE branch PURGE;
	
	DROP TABLE manager  PURGE;
   
    DROP TABLE book_detail PURGE;
	
	DROP TABLE publisher PURGE;







--Q2
/*
2.1
MonLib has just purchased its first 3 copies of a recently released edition of a book. Readers of this book will learn about 
the subjects "Database Design" and "Database Management". 

Some of  the details of the new book are:

	      	Call Number: 005.74 C822D 2018
Title: Database Systems: Design, Implementation, and Management
	      	Publication Year: 2018
	      	Edition: 13
	      	Publisher: Cengage
	Authors: Carlos CORONEL (author_id = 1 ) and 
   Steven MORRIS  (author_id = 2)  	      	
Price: $120
	
You may make up any other reasonable data values you need to be able to add this book.

Each of the 3 MonLib branches listed below will get a single copy of this book, the book will be available for borrowing 
(ie not on counter reserve) at each branch:

		Caulfield (Ph: 8888888881)
		Glen Huntly (Ph: 8888888882)
        Carnegie (Ph: 8888888883)

Your are required to treat this add of the book details and the three copies as a single transaction.
*/


INSERT INTO book_detail VALUES('005.74 C822D 2018', 'Database Systems: Design, Implementation, and Management', 'R', 800, TO_DATE('2018','YYYY'), '13', 
                                       (SELECT pub_id FROM publisher WHERE pub_name='Cengage') );

INSERT INTO bd_author VALUES('005.74 C822D 2018', 1 );  

INSERT INTO bd_author VALUES('005.74 C822D 2018', 2 );

INSERT INTO book_copy VALUES((SELECT branch_code FROM branch WHERE branch_contact_no=8888888881), 
                                    1, 120, 'N',
                                    '005.74 C822D 2018');

INSERT INTO book_copy VALUES( (SELECT branch_code FROM branch WHERE branch_contact_no=8888888882), 
                                2, 120, 'N',
                                '005.74 C822D 2018');
								
INSERT INTO book_copy VALUES((SELECT branch_code FROM branch WHERE branch_contact_no=8888888883),
                                        3, 120, 'N',
                                        '005.74 C822D 2018');
										
INSERT INTO bd_subject VALUES((SELECT subject_code FROM subject WHERE subject_details='Database Design'),
                                        '005.74 C822D 2018');
										

INSERT INTO bd_subject VALUES((SELECT subject_code FROM subject WHERE subject_details='Database Management'),
                                '005.74 C822D 2018');

COMMIT;








/*
2.2
An Oracle sequence is to be implemented in the database for the subsequent insertion of records into the database for  
BORROWER table. 

Provide the CREATE 	SEQUENCE statement to create a sequence which could be used to provide primary key values for the BORROWER 
table. The sequence should start at 10 and increment by 1.
*/

CREATE SEQUENCE borrower_bor_no_seq START WITH 10 INCREMENT BY 1 NOCACHE ORDER;








/*
2.3
Provide the DROP SEQUENCE statement for the sequence object you have created in question 2.2 above. 
*/

DROP SEQUENCE borrower_bor_no_seq ;










--Q3
/*
--3.1
Today is 20th September, 2018, add a new borrower in the database. Some of the details of the new borrower are:

		Name: Ada LOVELACE
		Home Branch: Caulfield (Ph: 8888888881)

You may make up any other reasonable data values you need to be able to add this borrower.

*/


INSERT INTO borrower VALUES(
    borrower_bor_no_seq.NEXTVAL, 
    'Ada', 'LOVELACE','54 Bales street','Waverley',2000,10	);


COMMIT;





/*
--3.2
Immediately after becoming a member, at 4PM, Ada places a reservation on a book at the Carnegie branch (Ph: 8888888883). Some 
of the details of the book that Ada  has placed a reservation on are:

		Call Number: 005.74 C822D 2018
        Title: Database Systems: Design, Implementation, and Management
		Publication Year: 2018
		Edition: 13

You may assume:
MonLib has not purchased any further copies of this book, beyond those which you inserted in Task 2.1
that nobody has become a member of the library between Ada becoming a member and this reservation.

*/


INSERT INTO reserve VALUES((SELECT branch_code FROM branch WHERE branch_contact_no=8888888883),
                               (SELECT bc_id FROM book_copy WHERE branch_code=(SELECT branch_code FROM branch WHERE branch_contact_no=8888888883)  
												AND book_call_no='005.74 C822D 2018'),
                                  TO_DATE('20-SEP-2018 16:00:00','DD-MON-YYYY HH24:MI:SS'),
                           (SELECT bor_no FROM borrower WHERE bor_fname='Ada' AND bor_lname='LOVELACE'));

UPDATE book_copy
		SET bc_reserve_flag='Y'
		WHERE branch_code=(SELECT branch_code FROM branch WHERE branch_contact_no=8888888883);
		
COMMIT;





/*
3.3
After 7 days from reserving the book, Ada receives a notification from the Carnegie library that the book she had placed
reservation on is available. Ada is very excited about the book being available as she wants to do very well in FIT9132 unit 
that she is currently studying at Monash University. Ada goes to the library and borrows the book at 2 PM on the same day of 
receiving the notification.

You may assume that there is no other borrower named Ada Lovelace.
*/


INSERT INTO loan VALUES((SELECT branch_code FROM branch WHERE branch_contact_no=8888888883),
                              (SELECT bc_id FROM book_copy WHERE branch_code=(SELECT branch_code FROM branch WHERE branch_contact_no=8888888883)),
                              ((TO_DATE('20-SEP-2018 14:00:00', 'DD/MON/YY HH24:MI:SS')) +7),
                              ((TO_DATE('20-SEP-2018 14:00:00', 'DD/MON/YY HH24:MI:SS')) +35),
                              '',(SELECT bor_no FROM borrower WHERE bor_fname='Ada' AND bor_lname='LOVELACE'));
							  
COMMIT;






  
/*
3.4
At 2 PM on the day the book is due, Ada goes to the library and renews the book as her exam for FIT9132 is in 2 weeks.
		
You may assume that there is no other borrower named Ada Lovelace.
*/

	INSERT INTO loan VALUES((SELECT branch_code FROM branch WHERE branch_contact_no=8888888883),
                              (SELECT bc_id FROM book_copy WHERE branch_code=(SELECT branch_code FROM branch WHERE branch_contact_no=8888888883)),
                              ((TO_DATE('20-SEP-2018 14:00:00', 'DD/MON/YY HH24:MI:SS')) +35),
                              ((TO_DATE('20-SEP-2018 14:00:00', 'DD/MON/YY HH24:MI:SS')) +63),
                              '',(SELECT bor_no FROM borrower WHERE bor_fname='Ada' AND bor_lname='LOVELACE'));
							  
	COMMIT;







--Q4
/*
4.1
Record whether a book is damaged (D) or lost (L). If the book is not damaged or lost,then it  is good (G) which means, 
it can be loaned. The value cannot be left empty  for this. Change the "live" database and add this required information 
for all the  books currently in the database. You may assume that condition of all existing books will be recorded as being 
good. The information can be updated later, if need be. 
*/

ALTER TABLE book_copy 
 ADD book_status CHAR(1);
 
ALTER TABLE book_copy
    modify book_status not null novalidate;
 

ALTER TABLE book_copy
 ADD CONSTRAINT bc_status_chk CHECK(book_status IN (
			'G',
			'D',
			'L'
		));

 
UPDATE book_copy
  SET book_status='G';
 
 COMMIT;


/*
4.2
Allow borrowers to be able to return the books they have loaned to any library branch as MonLib is getting a number of requests 
regarding this from borrowers. As part of this process MonLib wishes to record which branch a particular loan is returned to. 
Change the "live" database and add this required information for all the loans  currently in the database. For all completed 
loans, to this time, books were returned at the same branch from where those were loaned.
*/

ALTER TABLE loan
		ADD book_return_branch_code NUMBER(2);
	
ALTER TABLE loan	
		ADD CONSTRAINT lo_branch_code_fk FOREIGN KEY(book_return_branch_code)
		REFERENCES branch(branch_code);
        
        
	UPDATE loan
		set book_return_branch_code= branch_code
		where loan_actual_return_date is not null;
		
	COMMIT;









/*
4.3
Some of the MonLib branches have become very large and it is difficult for a single manager to look after all aspects of the 
branch. For this reason MonLib are intending to appoint two managers for the larger branches starting in the new year - one 
manager for the Fiction collection and another for the Non-Fiction collection. The branches which continue to have one manager 
will ask this manager to manage the branches Full collection. The number of branches which will require two managers is quite 
small (around 10% of the total MonLib branches). Change the "live" database to allow monLib the option of appointing two 
managers to a branch and track and also record, for all managers, which collection/s they are managing. 

In the new year, since the Carnegie branch (Ph: 8888888883) has a huge collection of books in comparison to the Caulfield and 
Glen Huntly branches, Robert (Manager id: 1) who is currently managing the Caulfield branch (Ph: 8888888881) has been asked to 
manage the Fiction collection of the Carnegie branch, as well as the full collection at the Caulfield branch. Thabie 
(Manager id: 2) who is currently managing the Glen Huntly branch (Ph: 8888888882) has been asked to manage the Non-Fiction 
collection of Carnegie branch, as well as the full collection at the Glen Huntly branch. Write the code to implement these 
changes.
*/
ALTER TABLE branch DROP COLUMN man_id;
DROP TABLE branch_manager_collection purge;

CREATE TABLE branch_manager_collection(
					branch_code      NUMBER(2) NOT NULL, 			
					man_id           NUMBER(2) NOT NULL,
					collection_type  CHAR(2) NOT NULL
	);
	
	ALTER TABLE branch_manager_collection
					ADD CONSTRAINT bmc_branch_code_pk PRIMARY KEY(branch_code,man_id,collection_type);
	
	
	ALTER TABLE branch_manager_collection
					ADD CONSTRAINT bmc_branch_code_fk FOREIGN KEY(branch_code)
					REFERENCES branch(branch_code);
	
	ALTER TABLE branch_manager_collection
					ADD CONSTRAINT bmc_man_id_fk FOREIGN KEY(man_id)
					REFERENCES manager(man_id);

	ALTER TABLE branch_manager_collection
			ADD CONSTRAINT bl_collection_type_check CHECK( collection_type IN (
			'FI',
			'NF',
			'FU'
			));                 
	
	
	
		INSERT INTO branch_manager_collection VALUES(
												(SELECT branch_code from branch where branch_contact_no=8888888883),1,'FI');
	
	
		INSERT INTO branch_manager_collection VALUES(
											(SELECT branch_code from branch where branch_contact_no=8888888881),1 ,'FU');
	
	
		INSERT INTO branch_manager_collection VALUES(
											(SELECT branch_code from branch where branch_contact_no=8888888883),2 ,'NF');
		
	
		INSERT INTO branch_manager_collection VALUES(
									(SELECT branch_code from branch where branch_contact_no=8888888882),2 ,'FU');
									
	COMMIT;
		


















