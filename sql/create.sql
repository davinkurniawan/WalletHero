CREATE TABLE "User" (
	"UserID" serial NOT NULL,
	"Username" varchar(255) NOT NULL UNIQUE,
	"Email" varchar(255) NOT NULL UNIQUE,
	"Password" varchar(255) NOT NULL,
	"SaltHash" varchar(255) NOT NULL,
	"FirstName" varchar(255) NOT NULL,
	"MiddleName" varchar(255),
	"LastName" varchar(255) NOT NULL,
	"Phone" varchar(255) NOT NULL,
	"Token" varchar(255) NOT NULL,
	"Age" bigint,
	"Gender" char,
	"Type" varchar(255) NOT NULL,
	CONSTRAINT User_pk PRIMARY KEY ("UserID")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Transactions" (
	"TransactionID" serial NOT NULL,
	"UserID" serial NOT NULL,
	"Date" DATE NOT NULL,
	"IsRecurring" BOOLEAN NOT NULL,
	"RecurrenceFreq" bigint,
	"ShortDescription" varchar NOT NULL,
	"LongDescription" varchar,
	"Price" DECIMAL NOT NULL,
	"CategoryID" bigint,
	CONSTRAINT Transactions_pk PRIMARY KEY ("TransactionID")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Session" (
	"SessionID" serial(32) NOT NULL,
	"UserID" bigint NOT NULL,
	"LastAccessed" DATE NOT NULL,
	CONSTRAINT Session_pk PRIMARY KEY ("SessionID")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "Transactions" ADD CONSTRAINT "Transactions_fk0" FOREIGN KEY ("UserID") REFERENCES "User"("UserID");

ALTER TABLE "Session" ADD CONSTRAINT "Session_fk0" FOREIGN KEY ("UserID") REFERENCES "User"("UserID");
