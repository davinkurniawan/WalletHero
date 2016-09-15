CREATE TABLE "User" (
	"UserID" serial NOT NULL,
	"Username" varchar NOT NULL UNIQUE,
	"Email" varchar NOT NULL UNIQUE,
	"Password" varchar NOT NULL,
	"SaltHash" varchar NOT NULL,
	"FirstName" varchar NOT NULL,
	"LastName" varchar NOT NULL,
	"Phone" varchar NOT NULL,
	"Token" varchar NOT NULL,
	CONSTRAINT User_pk PRIMARY KEY ("UserID")
) WITH (
  OIDS=FALSE
);

-- THIS IS NOT FINAL -- 
-- THIS IS AN EXAMPLE FROM DBDESIGN.NET --